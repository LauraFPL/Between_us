package salle.url.edu.presentation.controller;

import salle.url.edu.business.*;
import salle.url.edu.persistance.csv.CSVMapDAO;
import salle.url.edu.persistance.sql.SQLGameDAO;
import salle.url.edu.persistance.sql.SQLPlayerDAO;
import salle.url.edu.presentation.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * GamesController class that implements ActionListener
 * Controls everything from the games in general
 */
public class GamesController implements ActionListener {
    public static final String GAME_ALREADY_EXISTS = "This game already exists";
    public static final String GAME_NAME_NULL = "The name of the game can't be empty";
    public static final String GAME_NAME_EQUAL_COMILLA = "The name of the game can't have an equal (=) or a (')";

    public static final String NUM_IMPOSTORS_HIGH = "The number of impostors is too high";
    public static final String MAP_NOT_SELECTED = "Please select a map";

    private static GameManager gameManager;
    private static GameManagerView gameManagerView;
    private static String userName;

    private final MasterView masterView;
    private final GameCreationView gameCreationView;
    private final DuplicateGameView duplicateGameView;
    private final ConfirmationView confirmationViewGames;
    private final WarningView warningView;

    /**
     * Default constructor that creates an instance of GameManager so that we can
     * connect with the model
     */
    public GamesController (MasterView masterView, String userName) {
        System.out.print("GamesController > Loading views ... ");
        GamesController.userName = userName;

        gameManager = new GameManager(new SQLGameDAO(), new CSVMapDAO(), new SQLPlayerDAO(), GamesController.userName);
        this.masterView = masterView;

        // GAME MANAGER VIEW
        gameManagerView = new GameManagerView(gameManager.getAllGamesFromPlayer());
        gameManagerView.showWindow(true);
        gameManagerView.registerController(this);
        masterView.addView(gameManagerView, MasterView.GAMES_MANAGER_VIEW);

        // GAME CREATION VIEW
        this.gameCreationView = new GameCreationView();
        this.gameCreationView.showWindow(true);
        this.gameCreationView.registerController(this);
        masterView.addView(gameCreationView, MasterView.GAME_CREATION_VIEW);

        // DUPLICATE CREATION VIEW
        this.duplicateGameView = new DuplicateGameView();
        this.duplicateGameView.showWindow(true);
        this.duplicateGameView.registerController(this);
        masterView.addView(duplicateGameView, MasterView.DUPLICATION_CREATION_VIEW);

        //CONFIRMATION VIEW
        this.confirmationViewGames = new ConfirmationView();
        this.confirmationViewGames.showWindow(true);
        this.confirmationViewGames.registerControllerGames(this);
        masterView.addView(confirmationViewGames, MasterView.CONFIRMATION_VIEW_GAMES);

        //WARNING VIEW
        this.warningView = new WarningView();
        this.warningView.registerController(this);

        System.out.println("   . . . done.");

    }

    //INTERFICIE GRÀFICA________________________________________________________________________________________________
    /**
     * Method that controls all the actions listeners sent from other classes
     * @param e of type ActionEvent that contains the action performed on the other classes
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        String button = e.getActionCommand(); //Retorna string
        String[] buttons = button.split("="); //Primer split = CONSTANT, segon split (si és botó play, copy, trash) és el nom de la game

        //GAME MANAGER VIEW---------------------------------------------------------------------------------------------
        if (buttons[0].equals(GameManagerView.GAME_MANAGER_BACK_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.ACCOUNT_VIEW);
            System.out.println("GamesController > GameManager, Back pressed .. heading back to accountView ");

        } else if (buttons[0].equals(GameManagerView.GAME_MANAGER_CREATE_BUTTON_COMMAND)) {
            System.out.println("GamesController > GameManager, Create pressed .. Loading GameCreationView");
            gameCreationView.setJtextFieldJfilechooserEmpty();
            gameCreationView.updateUI();
            masterView.changeView(MasterView.GAME_CREATION_VIEW);

        } else if (buttons[0].equals(GameManagerView.GAME_MANAGER_PLAY_BUTTON_COMMAND)) {
            System.out.println("GamesController > GameManager, " + buttons[0] + " Play! ");

            CurrentGameManager currentGameManager = new CurrentGameManager(gameManager.readGame(buttons[1], userName), userName, new SQLGameDAO());
            new CurrentGameController(masterView, currentGameManager);

            masterView.changeView(MasterView.GAME_VIEW);

        } else if (buttons[0].equals(GameManagerView.GAME_MANAGER_COPY_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.DUPLICATION_CREATION_VIEW);
            duplicateGameView.updateInformationDuplicate(gameManager.readInfoDuplicate(buttons[1], userName));
            duplicateGameView.setJtextFieldEmpty();
            duplicateGameView.updateUI();
            System.out.print("GamesController > GameManager, " + buttons[1] + " Copy Game: " + duplicateGameView.getGameName());
            //Elimina el joc

        } else if (buttons[0].equals(GameManagerView.GAME_MANAGER_DELETE_BUTTON_COMMAND)) {
            confirmationViewGames.setGameName(buttons[1]);
            confirmationViewGames.updateConfirmationGame();
            confirmationViewGames.updateUI();
            masterView.changeView(MasterView.CONFIRMATION_VIEW_GAMES);
            System.out.println("GamesController > GameManager, " + buttons[1] + " Delete Game: " + duplicateGameView.getGameName());
        }

        //DELETE VIEW CONFIRMATION--------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(ConfirmationView.YES_BUTTON_GAMES)) {
            try {
                System.out.println("GamesController > GameManager, delete confirmed");

                gameManager.deleteGame(confirmationViewGames.getGameName(),userName);
                gameManagerView.removeGame(confirmationViewGames.getGameName());
                gameManagerView.updateUI();
                masterView.changeView(MasterView.GAMES_MANAGER_VIEW);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals(ConfirmationView.NO_BUTTON_GAMES)) {
            System.out.println("GamesController > GameManager, delete canceled");
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
        }

        //WARNING_CLOSE_________________________________________________________________________________________________
        if (e.getActionCommand().equals(WarningView.WARNING_ACCEPT)) {
            warningView.dispose();

            return;
        }

        //GAME CREATION VIEW--------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(GameCreationView.FILE_CHOOSER_BUTTON_COMMAND)) {
            gameCreationView.filechooser();

        } else if (e.getActionCommand().equals(GameCreationView.CREATION_BACK_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);

        } else if (e.getActionCommand().equals(GameCreationView.CREATION_CREATE_GAME_BUTTON_COMMAND)) {

            if (gameCreationView.getGameName() == null || gameCreationView.getGameName().equals("")) {
                //ERROR: This game name is empty / null
                warningView.updateWarning(GAME_NAME_NULL);
                warningView.showWindow(true);

                return;
            }

            if (gameCreationView.getGameName().contains("=") || gameCreationView.getGameName().contains("'")) {
                warningView.updateWarning(GAME_NAME_EQUAL_COMILLA);
                warningView.showWindow(true);

                return;
            }

            if (gameManager.checkGameExists(gameCreationView.getGameName(), gameManager.getPlayerName())) {
                //ERROR: This game already exists
                warningView.updateWarning(GAME_ALREADY_EXISTS);
                warningView.showWindow(true);

                return;
            }

            if (checkImpostorsAmounts(gameCreationView.getNumImpostors(), gameCreationView.getNumPlayers())) {
                //ERROR: The number of Impostors is too high
                warningView.updateWarning(NUM_IMPOSTORS_HIGH);
                warningView.showWindow(true);

                return;
            }

            if (gameCreationView.getMapPath() == null) {
                //ERROR: Map has not been selected
                warningView.updateWarning(MAP_NOT_SELECTED);
                warningView.showWindow(true);

                return;
            }

            //Tots els valors seleccionats per el joc son correctes, creem un nou joc
            System.out.println("GamesController > GameCreation, GAME CREATED!");
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
            Map map = gameManager.getMap(gameCreationView.getMapPath());

            Game game = new Game(gameCreationView.getGameName(),
                    gameCreationView.getNumPlayers(),
                    gameCreationView.getNumImpostors(),
                    gameCreationView.getPlayerColor(),
                    gameManager.getPlayer(userName), map);

            gameManager.addGame(game);
            gameManagerView.addGame(game);
            gameManagerView.registerControllerAddGame(this);
            gameManagerView.updateUI();
        }

        //GAME DUPLICATION VIEW-----------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(DuplicateGameView.DUPLICATION_BACK_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);

            return;
        }

        if (e.getActionCommand().equals(DuplicateGameView.DUPLICATION_CREATE_GAME_BUTTON_COMMAND)) {
            if (duplicateGameView.getGameName() == null || duplicateGameView.getGameName().equals("")) {
                //ERROR: This game name is empty / null
                warningView.updateWarning(GAME_NAME_NULL);
                warningView.showWindow(true);

                return;
            }

            if (duplicateGameView.getGameName().contains("=") || duplicateGameView.getGameName().contains("'")) {
                warningView.updateWarning(GAME_NAME_EQUAL_COMILLA);
                warningView.showWindow(true);

                return;
            }

            if (gameManager.checkGameExists(duplicateGameView.getGameName(), gameManager.getPlayerName())) {
                //ERROR: This game already exists
                warningView.updateWarning(GAME_ALREADY_EXISTS);
                warningView.showWindow(true);

                return;
            }

            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
            Map map = gameManager.getMap(duplicateGameView.getMapId());

            Game game = new Game(duplicateGameView.getGameName(),
                    duplicateGameView.getNumPlayers(),
                    duplicateGameView.getNumImpostors(),
                    duplicateGameView.getPlayerColor(),
                    gameManager.getPlayer(userName), map);

            gameManager.addGame(game);
            gameManagerView.addGame(game);
            gameManagerView.registerControllerAddGame(this);
            gameManagerView.updateUI();
        }
    }

    /**
     * Method that checks if the amount of impostors is valid
     * The amount of impostor has to be maximum a third of the number of total players
     * @param impostors integer with the number of impostors
     * @param players integer with the number of players
     * @return a boolean with true (if it is valid) or false (if it is not valid)
     */
    private boolean checkImpostorsAmounts (int impostors, int players) {
        return impostors > players / 3;
    }


    /**
     * Static method to be called when the player finishes de game to update the view and make the
     * play button of that finished game dissapear
     * @param gameName name of the game we want to delete it's button
     */
    public static void updateGameJustFinished (String gameName) {
        gameManagerView.deletePlayButtonFromGame(gameName);
        gameManagerView.updateUI();
    }
}
