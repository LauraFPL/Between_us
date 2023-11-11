package salle.url.edu.presentation.controller;

import salle.url.edu.business.*;
import salle.url.edu.business.Character;
import salle.url.edu.presentation.view.LogsView;
import salle.url.edu.presentation.view.DefeatView;
import salle.url.edu.presentation.view.MasterView;
import salle.url.edu.presentation.view.VictoryView;
import salle.url.edu.presentation.view.gameView.GameView;
import salle.url.edu.business.CurrentGameManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * CurrentGameController class that implements ActionListener and KeyListener
 * Controls everything about the game that is being played at the moment
 */
public class CurrentGameController implements ActionListener, KeyListener, ListSelectionListener {
    private GameView gameView;
    private MasterView masterView;
    private CurrentGameManager currentGameManager;
    private VictoryView victoryView;
    private DefeatView defeatView;
    private LogsView logsView;

    /**
     * Constructor that initialise all what is needed for this controller
     * @param masterView The class that contains all the views to be able to change between them
     * @param currentGameManager The class that connects with the bussiness
     */
    public CurrentGameController (MasterView masterView,CurrentGameManager currentGameManager) {
        this.masterView = masterView;
        this.currentGameManager = currentGameManager;

        try {
            gameView = new GameView(this);
            gameView.registerController(this);
            masterView.showWindow(true);
            masterView.addView(gameView, MasterView.GAME_VIEW);
            masterView.changeView(MasterView.GAME_VIEW);

            logsView = new LogsView();
            logsView.registerController(this);
            masterView.addView(logsView,MasterView.LOGS_VIEW);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameView.showWindow();


        (new Thread(gameView)).start();

    }

    /**
     * Method to listen the buttons as an ActionListener
     * @param e ActionEvent with the string of what button has been selected
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        //GAME VIEW --------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(GameView.GAME_BACK_BUTTON_COMMAND)) {
            System.out.println("CurrentGameController > GameView, Back");
            gameView.stopSound();

            gameView.stopGame();
            currentGameManager.stopGame();
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_OBJECTIVE_UP_BUTTON_COMMAND)) {
            NPC npc = (NPC) currentGameManager.getCharacter(((JButton)e.getSource()).getParent().getParent().getName());
            npc.moveSuspitionRol(-1);
            System.out.println("CurrentGameController > GameView, NPC suspition changed: " + npc.getColorString() + " ->" + npc.getSuspicionRoleString());

            gameView.updateCharacters();
            gameView.updateUI();
            gameView.registerWestController(this);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_OBJECTIVE_DOWN_BUTTON_COMMAND)) {
            NPC npc = (NPC) currentGameManager.getCharacter(((JButton)e.getSource()).getParent().getParent().getName());
            npc.moveSuspitionRol(1);
            System.out.println("CurrentGameController > GameView, NPC suspition changed: " + npc.getColorString() + " ->" + npc.getSuspicionRoleString());

            gameView.updateCharacters();
            gameView.updateUI();
            gameView.registerWestController(this);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_TOGGLE_VISIBILITY_COMMAND)) {
            gameView.toggleGlobalVisibility();

            try {
                gameView.updateMap();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                gameView.updateMap();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_CONTROL_UP_ARROW_COMMAND)) {
            currentGameManager.movePlayer(CurrentGameManager.UP);
            System.out.println("CurrentGameController > Move Up [direction=" +CurrentGameManager.UP + "] Player pos = " + Arrays.toString(currentGameManager.getPositionFromPlayer()));
            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_CONTROL_DOWN_ARROW_COMMAND)) {
            currentGameManager.movePlayer(CurrentGameManager.DOWN);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_CONTROL_LEFT_ARROW_COMMAND)) {
            currentGameManager.movePlayer(CurrentGameManager.LEFT);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_CONTROL_RIGHT_ARROW_COMMAND)) {
            currentGameManager.movePlayer(CurrentGameManager.RIGHT);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_ACTION_LOGS_COMMAND)) {
            logsView.updateLogsView( currentGameManager.getCharacters(), currentGameManager.getGameLogs(), this);
            logsView.updateUI();
            masterView.changeView(MasterView.LOGS_VIEW);

            return;
        }

        if (e.getActionCommand().equals(GameView.GAME_ACTION_CLASSIFICATION_COMMAND)) {
            Boolean bool = currentGameManager.checkResultsFromPlayer();

            if (bool != null) {
                if (bool) {
                    gameView.stopSound();
                    //aturem threads i guardem partida
                    currentGameManager.finishGame(true);
                    gameView.stopGame();

                    //before going to the previus view, we make that game to don't be able to have the play button
                    GamesController.updateGameJustFinished(currentGameManager.getGameName());

                    //Anem a la pestanya de victòria
                    victoryView = new VictoryView();
                    victoryView.registerController(this);
                    masterView.addView(victoryView, MasterView.VICTORY_VIEW);
                    masterView.changeView(MasterView.VICTORY_VIEW);
                } else { //No ha encertat, es manté a la partida
                    gameView.showSusSuspectIncorrect();
                }
            }

            return;
        }

        //Victory view-----------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(VictoryView.VICTORY_BACK_COMMAND)) {
            victoryView.stopSound();
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
            currentGameManager = null;
            gameView = null;
            victoryView = null;
            defeatView = null;
            masterView = null;
        }

        //Defeat view-----------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(DefeatView.DEFEAT_BACK_BUTTON_COMMAND)) {
            defeatView.stopSound();
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
            currentGameManager = null;
            gameView = null;
            victoryView = null;
            defeatView = null;
            masterView = null;
        }

        //Logs view-----------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(LogsView.LOGS_EXIT_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.GAME_VIEW);
        }
    }

    /**
     * Method called when the player dies to finish the game, save it and go to the defeat view
     */
    public void finishGameDefeat () {
        gameView.stopSound();
        //aturem threads i guardem partida
        currentGameManager.finishGame(false);
        gameView.stopGame();

        //before going to the previus view, we make that game to don't be able to have the play button
        GamesController.updateGameJustFinished(currentGameManager.getGameName());

        //Anem a la pestanya de victòria
        defeatView = new DefeatView();
        defeatView.registerController(this);
        masterView.addView(defeatView, MasterView.DEFEAT_VIEW);
        masterView.changeView(MasterView.DEFEAT_VIEW);
    }

    /**
     * Getter of the current game
     * @return of type Game that contains the current game
     */
    public Game getGame () {
        return currentGameManager.getGame();
    }

    /**
     * Getter of the current map
     * @return of type Map that contains the current map
     */
    public Map getMap () {
        return currentGameManager.getGame().getMap();
    }

    /**
     * Method that looks if the user can open the logs view depending on the room that is right now
     * @param position contains the position of the player
     * @return of type boolean true (if logs table can be opened) or false (if logs table cannot be opened)
     */
    public boolean roomIsActionLogs (int[] position) {
       return currentGameManager.roomIsActionLogs(position);
    }

    /**
     * Method that looks if the user can check the suspicion guesses that made depending on the room
     * @param position contains the position of the player
     * @return of type boolean true (if guess can be checked) or false (if guess cannot be checked)
     */
    public boolean roomIsActionClassification (int[] position) {
        return currentGameManager.roomIsActionClassification(position);
    }

    /**
     * Method that check if the player of the game is still alive
     * @return boolean of true if it is alive
     */
    public boolean playerIsAlive () {
        return currentGameManager.playerIsAlive();
    }

    /**
     * Method that check if the player of the game is still alive
     * @param e variable that controls the keyboard
     */
    @Override
    public void keyTyped (KeyEvent e) {

    }

    /**
     * Method that allows the player move with the differents keyboard keys
     * @param e variable that controls the keyboard
     */
    @Override
    public void keyPressed (KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_LEFT){
            currentGameManager.movePlayer(CurrentGameManager.LEFT);
            return;
        }
        if (e.getKeyChar() == KeyEvent.VK_RIGHT){
            currentGameManager.movePlayer(CurrentGameManager.RIGHT);
            return;
        }
        if (e.getKeyChar() == KeyEvent.VK_UP){
            currentGameManager.movePlayer(CurrentGameManager.UP);
            return;
        }
        if (e.getKeyChar() == KeyEvent.VK_DOWN){
            currentGameManager.movePlayer(CurrentGameManager.DOWN);
        }
    }

    /**
     * Methods that waits
     * @param e the event to be processed
     */
    @Override
    public void keyReleased (KeyEvent e) {

    }

    /**
     * Method that waits for a ListSelectionEvent
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged (ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        String npcString = logsView.getNpcFromRow(e.getFirstIndex());
        if (npcString == null) return;
        NPC npc = ((NPC) currentGameManager.getCharacter(npcString));

        npc.moveSuspitionRol(1);
        logsView.updateAfterEvent();
        logsView.updateLogsView(currentGameManager.getCharacters(), currentGameManager.getGameLogs(), this);
        logsView.registerController(this);

        gameView.updateCharacters();
        gameView.updateUI();
        gameView.registerWestController(this);
    }

    /**
     * Getter for the image path of a specified room in the current Game map
     * @param roomIndex int
     * @return String path
     */
    public String getGameMapRoomPath (int roomIndex) {
        return currentGameManager.getGameMapRoomPath(roomIndex);
    }

    /**
     * Gets all the characters from the game
     * @return ArrayList<Character>
     */
    public ArrayList<Character> getGameCharacters () { return currentGameManager.getCharacters();}

    /**
     * Gets the position from the Player
     * @return int[]
     */
    public int[] getPositionFromPlayer () {
        return currentGameManager.getPositionFromPlayer();
    }

    /**
     * Checks the number of impostors
     * @return int, the number of impostors
     */
    public int getNumImpostor(){ return currentGameManager.numImpostors(); }
}
