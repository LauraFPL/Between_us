package salle.url.edu.persistance;

import salle.url.edu.business.Game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * GameDAO interface for Game persistence
 */
public interface GameDAO {
    /**
     * Method that gets all games that a player has stored
     * @param playerName String that contains the player's name
     * @return an ArrayList of all the player's games
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public ArrayList<Game> getAllGamesFromPlayer(String playerName) throws IOException, SQLException;

    /**
     * Method that adds a new game
     * @param game variable with all the different games that the player creates
     * @throws IOException in case anything goes wrong
     */
    public void addGame(Game game) throws IOException;

    /**
     * Method that duplicates an existing game
     * @param name String that contains the game's name
     * @param game of type Game that contains all the game information
     * @throws IOException in case anything goes wrong
     */
    public void duplicateGame(String name, Game game) throws IOException;

    /**
     * Method that deletes an existing game
     * @param game String that contains the game name
     * @param userName String that contains the player's username
     * @throws IOException in case anything goes wrong
     */
    public void deleteGame(String game,String userName) throws IOException;

    /**
     * Method that checks if the player's suspicions were correct to win the game
     * @param game String that contains the game's name
     * @return a boolean containing true (if the player was right) or false (if the player was wrong)
     * @throws IOException in case anything goes wrong
     */
    public boolean checkGame(String game) throws IOException;

    /**
     * Method that saves an already started game and all its information
     * @param game of type Game contains all the information
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public void saveGame(Game game) throws IOException, SQLException;

    /**
     * Method that reads a saved game so that can be continued
     * @param gameName String that contains the game's name
     * @param userName String that contains the player's username
     * @return a game ready to continue playing
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public Game readGame(String gameName, String userName) throws  IOException, SQLException;

    /**
     * Method that starts a game
     * @param gameName String that contains the game's name
     * @param userName String that contains the player's username
     */
    public void startGame(String gameName, String userName);

    /**
     * Method that finishes a game
     * @param gameName String that contains the game's name
     * @param userName String that contains the player's username
     * @param winner boolean that contains true (if the player wins) or false (if the player loses)
     */
    public void finishGame(String gameName, String userName, Boolean winner);

    /**
     * Method that gets a game's name
     * @param gameName String that contains the game's name that has to be found
     * @param userName String that contains the player's username
     * @return a String with the game's name
     */
    String getGameName(String gameName, String userName);

    /**
     * Method that gives the game information of the game that has to be duplicated
     * @param gameName String that contains the game that has to be duplicated name
     * @param userName String that contains the player's username
     * @return a String array with all the information to show
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public String[] readInfoDuplicationGame(String gameName, String userName) throws IOException, SQLException;
}
