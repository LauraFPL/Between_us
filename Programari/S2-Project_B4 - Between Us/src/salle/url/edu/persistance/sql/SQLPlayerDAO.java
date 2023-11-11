package salle.url.edu.persistance.sql;

import salle.url.edu.business.Player;
import salle.url.edu.persistance.PlayerDAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class SQLPlayerDAO implements PlayerDAO interface
 * Reads data of the player stored in the Database
 */
public class SQLPlayerDAO implements PlayerDAO {
    /**
     * Empty Constructor
     */
    public SQLPlayerDAO () {

    }

    /**
     * Method that adds a new player to the database
     * @param player the player to be added
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void addPlayer (Player player) throws IOException {
        String query = "INSERT INTO player(name, email, password) VALUES('" +
                player.getName() + "', '" +
                player.getEmail() + "', '" +
                player.getPassword() +
                "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    /**
     * Method that gets all the players in the database
     * @return an ArrayList of players
     */
    @Override
    public ArrayList<Player> getAllPlayers () {
        ArrayList<Player> players = new ArrayList<>();
        String query = "SELECT name, email, password FROM player;";

        try {
            ResultSet result = SQLConnector.getInstance().selectQuery(query);

            try {
                while (result.next()) {
                    String name =  result.getString("name");
                    String email = result.getString("email");
                    String password = result.getString("password");

                    players.add(new Player(name, email, password));
                }

                System.out.println("Players loaded");
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    /**
     * Method that deletes a player form the database
     * @param name String containing the player's name
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void deletePlayer (String name) throws IOException {

        String queryNPC = "DELETE FROM npc_game WHERE player_name = '" + name + "';";
        SQLConnector.getInstance().deleteQuery(queryNPC);

        String queryGameLogs = "DELETE FROM gameLogs WHERE player_name = '" + name + "';";
        SQLConnector.getInstance().deleteQuery(queryGameLogs);

        String queryGame = "DELETE FROM game WHERE player = '" + name + "';";
        SQLConnector.getInstance().deleteQuery(queryGame);

        String query = "DELETE FROM Player WHERE name = '" + name + "';";
        SQLConnector.getInstance().deleteQuery(query);

    }

    /**
     * Method that gets one player from the database
     * @param userName String containing the player's username
     * @return the wanted player
     * @throws IOException in case anything goes wrong
     */
    @Override
    public Player getPlayer (String userName) throws IOException {
        String query = "SELECT name, email, password FROM player WHERE name = '" + userName + "';";
        Player player = null;
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        String name = null;

        try {

            if (result.next()) {
                name = result.getString("name");
                String mail = result.getString("email");
                String password = result.getString("password");
                player = new Player(name, mail, password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return player;
    }

    /**
     * Method that gets the password from one player
     * @param userName String that contains the username of the player
     * @return a String with the player's password
     * @throws IOException in case anything goes wrong
     */
    @Override
    public String getPasswordFromPlayer (String userName) throws IOException{
        String query = "SELECT password FROM player WHERE name = '" + userName + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {

            if (result.next()) {
                return result.getString("password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Method that returns the user name in case it is in the database
     * @param userName String that contains the username of the player
     * @return a String with the player's name get it from de database
     * @throws IOException in case anything goes wrong
     */
    @Override
    public String getNameUserToCheck (String userName) throws IOException{
        String query = "SELECT name FROM player WHERE name = '" + userName + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {

            if (result.next()) {
                return result.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Method that returns the user email in case it is in the database
     * @param email String that contains the username of the player
     * @return a String with the player's email get it from de database
     * @throws IOException in case anything goes wrong
     */
    @Override
    public String getEmailUserToCheck (String email) throws IOException{
        String query = "SELECT email FROM player WHERE email = '" + email + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {

            if (result.next()) {
                return result.getString("email");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Method that gets the wins from a player
     * @param userName String that contains the player's username
     * @return an ArrayList of booleans containing true (if win) or false (if loss)
     * @throws IOException in case anything goes wrong
     */
    public ArrayList<Boolean> getGamesWins (String userName) throws IOException {

        String query = "SELECT winner FROM game WHERE player = '" + userName + "' AND finished = true ORDER BY finish_game_date ASC;";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        ArrayList <Boolean> numWins = new ArrayList<Boolean>();

        try {

            while (result.next()) {

                numWins.add(result.getBoolean("winner"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return numWins;
    }
}

