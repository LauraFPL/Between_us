package salle.url.edu.business;

import salle.url.edu.persistance.GameDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class CurrentGameManager
 * Manages the currentGame and communicates information of the Game to the CurrentGameController
 */
public class CurrentGameManager {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private Game game;
    private String userName;
    private final GameDAO gameDAO;

    /**
     * Constructor of the class
     * @param game  Game to be maanaged
     * @param userName  UserName of the Player that is playing
     * @param gameDAO   Connection with the database
     */
    public CurrentGameManager(Game game, String userName, GameDAO gameDAO){
        this.game = game;
        this.userName = userName;
        this.gameDAO = gameDAO;

        if (!game.getStarted()){ // Apreten play de la partida i encara no havia començat
            game.hasStarted(); // Li diem al game que la partida ja ha comneçat
            gameDAO.startGame(game.getName(), userName); // La data del start date, es posa a la BBDD
        }

        game.startThreads();
    }

    // METHODS__________________________________________________________________________________________________________
    /**
     *  Moves the character in the specified direction if it is possible
     *  Possible directions are UP, DOWN, LEFT and RIGHT
     *  @param direction direction in which the player has to move
     */
    public void movePlayer(int direction){
        game.movePlayer(direction);
    }

    /**
     * Method that stop all the active threads that are in game and save the game in the database
     */
    public void stopGame() {
        game.stopGame();

        try {
            gameDAO.saveGame(game);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that finishes the game
     * @param win boolean that says if the player has won (true), or has been defeated (false)
     */
    public void finishGame(boolean win){
        stopGame();
        gameDAO.finishGame(game.getName(), game.getPlayerUserName(), win);
    }

    /**
     * Checks the results of the player classification Table
     * @return Boolean of the result with true if it is correct, false if not and null if he couldn't check
     */
    public Boolean checkResultsFromPlayer(){
        return game.checkResultsFromPlayer();
    }


    /**
     * Checks the number of impostors
     * @return int, the number of impostors
     */
    public int numImpostors(){
        return game.getNumImpostors();
    }


    //GETTERS-----------------------------------------------------------------------------------------------------------
    /**
     * Getter of the GameLogs
     * @return ArrayList containing all the logs
     */
    public ArrayList<GameLogs> getGameLogs(){
        return  game.getGameLogs();
    }

    /**
     * Getter of the Game
     * @return Game with its information
     */
    public Game getGame() {return this.game;}

    /**
     * Getter of the Game name
     * @return a String with the game's name
     */
    public String getGameName(){ return  game.getName();}

    /**
     * Checks if a specified room has the LOG Action
     * @param position position of the room
     * @return boolean if the room can check the game logs
     */
    public boolean roomIsActionLogs(int[] position) {
        return game.roomIsActionLogs(position);
    }

    /**
     * Checks if a specified room has the CLASSIFICATION Action
     * @param position position of the room
     * @return boolean if the room is able to send the player's suspicions
     */
    public boolean roomIsActionClassification(int[] position) {
        return game.roomIsActionClassification(position);
    }

    /**
     * Getter of the characters of the Game
     * @return ArrayList of characters
     */
    public ArrayList<Character> getCharacters(){ return game.getCharacters();}

    /**
     * Getter of the state of the player in the game
     * @return boolean with true if it is alive
     */
    public boolean playerIsAlive(){ return  game.getAliveFromPlayer();}

    /**
     * Gets a character from a specified String
     * @param name String containing character's the name
     * @return Character
     */
    public Character getCharacter(String name) {
        return game.getCharacter(name);
    }

    /**
     * Getter for the image path of a specified room in the current Game map
     * @param roomIndex int containing the index of the room in the array
     * @return String containing the room's path of an especific map
     */
    public String getGameMapRoomPath(int roomIndex) {
        return game.getGameMapRoomPath(roomIndex);
    }

    /**
     * Gets the position from the Player
     * @return int[] with the position of the player
     */
    public int[] getPositionFromPlayer() {
        return game.getPositionFromPlayer();
    }



}
