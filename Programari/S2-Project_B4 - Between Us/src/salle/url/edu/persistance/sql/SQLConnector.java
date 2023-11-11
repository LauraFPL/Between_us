package salle.url.edu.persistance.sql;

import salle.url.edu.persistance.ConfigDAO;
import salle.url.edu.persistance.json.JSONConfigDAO;
import java.io.IOException;
import java.sql.*;

/**
 * SQLConnector manages the connections with the database
 * It uses the singleton class model
 * We are using MySQL Database
 * To be able to connect to the database, do the following steps:
 *  1- Open MySQL Workbench
 *  2- Create a new Connections and name it Between_Us
 *  ! Remember to create a new User with the credentials in the config file and give
 *  it the right permissions
 *  3- Run the sql script located in the files' folder of the project
 *  4- Good To GO!
 */
public class SQLConnector {
    private static SQLConnector instance = null;

    /**
     * static Method that uses the singleton model to get an instance of the SQLConnector class
     * @return SQLConnector
     * @throws IOException in case anything goes wrong
     */
    public static SQLConnector getInstance () throws IOException {
        if (instance == null ) {

            instance = new SQLConnector(new JSONConfigDAO());
            instance.connect();
        }

        return instance;
    }

    private final String url;
    private final String username;
    private final String password;
    private Connection conn;

    /**
     * Constructor of SQLConnector
     * Reads the data stored in the config file
     * @throws IOException in case anything goes wrong
     */
    private SQLConnector (ConfigDAO configDAO) throws IOException {
        String[] config = configDAO.getConfig();

        this.username = config[0];
        this.password = config[2];
        this.url = "jdbc:mysql://" + config[1] + ":" + config[4]  + "/" + config[3] ;
    }

    /**
     * Method that starts the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void connect () {
        try {
            conn = DriverManager.getConnection(url, username, password);

            System.out.println("Connections achieved with database!");
        } catch (SQLException e) {
            System.err.println("Couldn't connect to --> " + url + " (" + e.getMessage() + ")");
            System.exit(-1);
        }
    }

    /**
     * Method that executes an insertion query to the connected database.
     * @param query String representation of the query to execute.
     */
    public void insertQuery (String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when inserting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Method that executes an update query to the connected database.
     * @param query String representation of the query to execute.
     */
    public void updateQuery (String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problema when updating --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }

    /**
     * Method that executes a deletion query to the connected database.
     * @param query String representation of the query to execute.
     */
    public void deleteQuery (String query) {
        try {
            Statement s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when deleting --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }

    }

    /**
     * Method that executes a selection query to the connected database.
     * @param query String representation of the query to execute.
     * @return The results of the selection.
     */
    public ResultSet selectQuery (String query) {
        ResultSet rs = null;

        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(query);
            System.err.println("Problem when selecting data --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }

        return rs;
    }

    /**
     * Method that closes the inner connection to the database. Ideally, users would disconnect after
     * using the shared instance.
     */
    public void disconnect () {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem when closing the connection --> " + e.getSQLState() + " (" + e.getMessage() + ")");
        }
    }
}
