package salle.url.edu.business;

import salle.url.edu.persistance.PlayerDAO;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class PlayerManager
 * Manages the connection between Presentation, Business and Persistence Layer
 */
public class PlayersManager {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final PlayerDAO playerDAO;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor of PlayersManager
     * @param playerDAO PlayerDAO
     */
    public PlayersManager(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Creates a player that is added to the database
     * @param name of the player to be added
     * @param email of the player to be added
     * @param password of the player to be added
     */
    public void createPlayer (String name, String email, String password){
        Player player = new Player(null, false,null, name, email, password, null);

        try {
            playerDAO.addPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a player from the database
     * @param name String that contains the player's name
     * @return a boolean containing true (if deleted) or false (if not deleted)
     */
    public boolean deletePlayer (String name) {
        try {
            playerDAO.deletePlayer(name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if a player with a specific email is already in the Database
     * @param email of the player to be checked
     * @return true if is already in the database
     *         false if it is not
     */
    public boolean checkEmailPlayer (String email)  {

        try {
            String player = playerDAO.getEmailUserToCheck(email);
            if (player != null) {
                return true;
            } else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks if a player with a specific userName is already in the Database
     * @param userName of the player to be checked
     * @return true if is already in the database
     *         false if it is not
     */
    public Boolean checkUserNamePlayer (String userName) {

        if (!(userName.equals(""))) {

            try {
                String player = playerDAO.getNameUserToCheck(userName);
                if (player != null && userName.equals(player)) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{ return null;}

        return false;
    }

    /**
     * Checks if the player's password is the same from the Database
     * @param userName of the player to be checked
     * @param passwordToCheck String with the password that has to be checked
     * @return true if is the same
     *         false if it is not
     */
    public boolean checkPasswordFromPlayer (String userName, String passwordToCheck) {
        try {
            String realPassword = playerDAO.getPasswordFromPlayer(userName);

            if (realPassword != null) {
                if (realPassword.equals(passwordToCheck)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets a player given a specific username
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

    /**
     * Method that gets the win values
     * @param userName String containing the player's username
     * @return an ArrayList of booleans
     */
    public ArrayList<Boolean> getWinsValue(String userName) {

        try {

            return playerDAO.getGamesWins(userName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
