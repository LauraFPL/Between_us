package salle.url.edu.business;

import salle.url.edu.persistance.GameDAO;
import salle.url.edu.persistance.MapDAO;
import salle.url.edu.persistance.PlayerDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Manages the connection to the database of the Games
 */
public class GameManager {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final String playerName;
    private final GameDAO gameDAO;
    private final PlayerDAO playerDAO;
    private final MapDAO mapDAO;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor
     * @param gameDAO instance of the GameDAO for accessing the information of the Game in the Database
     * @param mapDAO instance of the MapDAO for accessing the information of the Map in the Database
     * @param playerDAO instance of the PlayerDAO for accessing the information of the Player in the Database
     * @param playerName String of the userName that has logged
     */
    public GameManager(GameDAO gameDAO, MapDAO mapDAO, PlayerDAO playerDAO, String playerName){
        this.gameDAO = gameDAO;
        this.mapDAO = mapDAO;
        this.playerName = playerName;
        this.playerDAO = playerDAO;
    }

    //GETTERS-----------------------------------------------------------------------------------------------------------
    /**
     * Getter of the Map stored by persistance given a specific id
     * @param id int contains the map id
     * @return Map
     */
    public Map getMap(int id) {
        Map map = null;

        try {
            map =  mapDAO.getMap(id);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Getter of the Map stored by persistance given a specific Path
     * @param path String to the path of the map file
     * @return Map
     */
    public Map getMap(String path) {
        Map map = null;

        try {
            map =  mapDAO.getMap(path);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Gets a player given a specific email
     * @param userName unique email that corresponds to the player
     * @return Player if it was found
     *          null if it wasn't found
     */
    public Player getPlayer(String userName) {
        Player player = null;

        try {
            player = playerDAO.getPlayer(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return player;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Method that checks if a gamesExists using gameDAO interface
     * @param name String representing the name of the game to be checked
     * @return true if an existing game was found
     *          false if no one was found or if an exception was found
     */
    public boolean checkGameExists(String name, String user){
        String gameName = gameDAO.getGameName(name, user);

        if(gameName != null){
            return true;
        }

        return false;
    }

    /**
     * Adds a Game by persistance
     * @param game Game to be saved
     */
    public void addGame(Game game){
        try {
            gameDAO.addGame(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for all the games a Player has saved in persistance
     * @return ArrayList<Game>
     */
    public ArrayList<Game> getAllGamesFromPlayer(){
        try {
            return gameDAO.getAllGamesFromPlayer(this.playerName);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads the information of the game to be duplicated
     * @param gameName String of the game to be duplicated
     * @param playerName String of the playerName
     * @return String[] containing information of the game
     */
    public String[] readInfoDuplicate(String gameName, String playerName){
        try {
            return gameDAO.readInfoDuplicationGame(gameName,playerName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a a game given a specific name and username
     * @param name String name of the game
     * @param userName String name of the user
     * @throws IOException in case anything goes wrong
     */
    public void deleteGame(String name, String userName) throws IOException {
        gameDAO.deleteGame(name, userName);
    }

    /**
     * Reads a game
     * @param gameName String of the gameName
     * @param userName String of the userName
     * @return Game read
     */
    public Game readGame(String gameName, String userName){
        try {
            return gameDAO.readGame(gameName, userName);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the player name
     * @return a String with the player's name
     */
    public String getPlayerName() {
        return playerName;
    }
}
