package salle.url.edu.persistance;

import salle.url.edu.business.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerDAO interface for Player persistence
 */
public interface PlayerDAO {
    /**
     * Method that adds a player
     * @param player the player to be added
     * @throws IOException in case anything goes wrong
     */
    public void addPlayer(Player player) throws IOException;

    /**
     * Method that gets all the saved players
     * @return an ArrayList of all the players
     */
    public ArrayList<Player> getAllPlayers();

    /**
     * Method that deletes a player
     * @param name String containing the player's name
     * @throws IOException in case anything goes wrong
     */
    public void deletePlayer(String name) throws IOException;

    /**
     * Method that gets a player
     * @param email String that contains the player's email
     * @return the Player that had to be found
     * @throws IOException in case anything goes wrong
     */
    public Player getPlayer(String email) throws IOException;

    /**
     * Method that gets the password from an especific player
     * @param userName String that contains the username of the player
     * @return a String with the password
     * @throws IOException in case anything goes wrong
     */
    public String getPasswordFromPlayer(String userName) throws IOException;

    /**
     * Method that gets the games wins of a player for the player evolution graph
     * @param userName String that contains the player's username
     * @return an ArrayList of booleans that contain wins (true) or losses (false)
     * @throws IOException in case anything goes wrong
     */
    public ArrayList <Boolean> getGamesWins(String userName) throws IOException;

    /**
     * Method that returns the user name in case it is in the database
     * @param userName String that contains the username of the player
     * @return a String with the player's name get it from de database
     * @throws IOException in case anything goes wrong
     */
    public String getNameUserToCheck (String userName) throws IOException;

    /**
     * Method that returns the user email in case it is in the database
     * @param email String that contains the email of the player
     * @return a String with the email's name get it from de database
     * @throws IOException in case anything goes wrong
     */

    public String getEmailUserToCheck (String email) throws IOException;
}
