package salle.url.edu.persistance.sql;

import salle.url.edu.business.*;
import salle.url.edu.business.Character;
import salle.url.edu.persistance.GameDAO;
import salle.url.edu.persistance.MapDAO;
import salle.url.edu.persistance.csv.CSVMapDAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class SQLGameDAO implements GameDAO interface
 * Reads data of the game stored in the Database
 */
public class SQLGameDAO implements GameDAO {
    /**
     * Empty Constructor
     */
    public SQLGameDAO () {

    }

    /**
     * Reads all the games stored in the database that a specific player given its userName
     * @param playerName String of the username we want to read the games
     * @return ArrayList<Game>
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    @Override
    public ArrayList<Game> getAllGamesFromPlayer (String playerName) throws IOException, SQLException {
        String queryPlayer = "SELECT * FROM player WHERE name LIKE '" + playerName + "' ;";
        ResultSet resultPlayer = SQLConnector.getInstance().selectQuery(queryPlayer);
        String email = "";
        String password = "";

        if (resultPlayer.next()) {
            email = resultPlayer.getString("email");
            password = resultPlayer.getString("password");
        }

        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT * FROM game WHERE player LIKE '" + playerName + "' ;";
        ArrayList <String> names = new ArrayList<>();
        ArrayList <String> player_colors = new ArrayList<>();
        ArrayList <Integer> posYs_player = new ArrayList<>();
        ArrayList <Integer> posXs_player = new ArrayList<>();
        ArrayList <Boolean> alives_player = new ArrayList<>();
        ArrayList <Integer> maps = new ArrayList<>();
        ArrayList <Boolean> starteds = new ArrayList<>();
        ArrayList <Boolean> finisheds = new ArrayList<>();
        ArrayList <Boolean> winners = new ArrayList<>();

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        //Guardem en arraylists tota la informació de les partides de la base de dades
        while (result.next()) {

            names.add( result.getString("name") );
            player_colors.add( result.getString("player_color") );
            posYs_player.add( result.getInt("posY_player") );
            posXs_player.add( result.getInt("posX_player") );
            alives_player.add( result.getBoolean("alive_player") );
            maps.add( result.getInt("map") );
            starteds.add( result.getBoolean("started") );
            finisheds.add( result.getBoolean("finished") );
            winners.add( result.getBoolean("winner") );
        }

        //Per cada partida, llegim quins NPC té
        for (int i = 0; i < names.size(); i++) { //Per cada partida mirem els seus jugadors
            ArrayList <Character> characters = new ArrayList<>();
            ArrayList <String> NPC_ids = new ArrayList<>();
            ArrayList <Integer> posXs = new ArrayList<>();
            ArrayList <Integer> posYs = new ArrayList<>();
            ArrayList <Boolean> alives = new ArrayList<>();
            ArrayList <String> suspiciontRoles = new ArrayList<>();
            ArrayList <String> roles = new ArrayList<>();

            String query2 = "SELECT NPC_id, posX, posY, role, alive, suspicionRole, role FROM npc_game WHERE game_name LIKE '" + names.get(i) + "'"
                            + "AND player_name LIKE '" + playerName + "' ;";

            ResultSet result2 = SQLConnector.getInstance().selectQuery(query2);

            while (result2.next()) {
                NPC_ids.add( result2.getString("NPC_id") );
                posXs.add( result2.getInt("posX") );
                posYs.add( result2.getInt("posY") );
                alives.add( result2.getBoolean("alive") );
                suspiciontRoles.add( result2.getString("suspicionRole"));
                roles.add( result2.getString("role") );
            }

            //Un cop llegim els NPC, ja disposem a guardar la informació en la Classe
            //1r afegim al player --> public Player(CharacterColors color, boolean alive, int[] position, String name, String email, String password)
            characters.add(new Player(CharacterColors.getCharacterColorFromString(player_colors.get(i)), alives_player.get(i), new int[]{posXs_player.get(i), posYs_player.get(i)}, playerName, email, password, null ));

            //2n --> Fiquem els NPC --> public NPC(CharacterColors color, boolean alive, int[] position, NPCSuspition assignedRol)
            int numTripulants = 0;
            int numImpostors = 0;

            for (int j = 0; j < NPC_ids.size(); j++) {
                if (roles.get(j).equals("Impostor")) {
                    //characters.add(new Impostor(CharacterColors.getCharacterColorFromString(NPC_ids.get(j)), alives.get(j), new int[]{posXs.get(j), posYs.get(j)}, NPCSuspition.valueOf(suspiciontRoles.get(j))) );
                    numImpostors++;
                }else if (roles.get(j).equals("Tripulant")) {
                    //characters.add(new NPC(CharacterColors.getCharacterColorFromString(NPC_ids.get(j)), alives.get(j), new int[]{posXs.get(j), posYs.get(j)}, NPCSuspition.valueOf(suspiciontRoles.get(j))) );
                    numTripulants++;
                }
            }

            //Ya tenim tots els Characters, ara fiquem la informació en els games
            //public Game(String name, int numJugadors, int  numImpostors, ArrayList<Character> characters, Map map, boolean started, boolean finished, String winner)
            games.add(new Game(names.get(i), numTripulants + 1, numImpostors, characters, new Map(maps.get(i)), starteds.get(i), finisheds.get(i), winners.get(i)));
        } //tanquem for per cada partida

        return games;
    }

    /**
     * Method to add a Game to the Database with an INSERT query
     * @param game Game to be added
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void addGame (Game game) throws IOException {
        String query = "INSERT INTO game(name, player_color, posX_player, posY_player, map, player, started, finished, creation_date) VALUES('" +
                game.getName() + "', '" +
                game.getColorPlayerString() + "', " +
                game.getPositionFromPlayer()[0] + ", " +
                game.getPositionFromPlayer()[1] + ", " +
                game.getMapId() + ", '" +
                game.getPlayerUserName() + "', " +
                game.getStarted() + ", " +
                game.getFinished() + ", " +
                "NOW()" +
                ");";

        SQLConnector.getInstance().insertQuery(query);

        int indexPlayer = game.getIndexPlayer();

        for (int i = 0; i < game.getNumCharacters(); i++) {
            if (i != indexPlayer) {
                String query2 = "INSERT INTO npc_game(game_name, player_name, NPC_id, posX, posY, role, num_game) VALUES('" +
                        game.getName() + "', '" +
                        game.getPlayerUserName() + "', '" +
                        game.getColorFromCharacter(i) + "', " +
                        game.getPositionFromCharacter(i)[0] + ", " +
                        game.getPositionFromCharacter(i)[1] + ", '" +
                        game.getRoleCharacter(i) +
                        "' , (SELECT num_game FROM game WHERE name LIKE '" + game.getName() + "' AND player LIKE '" + game.getPlayerUserName() +"'));";

                SQLConnector.getInstance().insertQuery(query2);
            }
        }
    }

    /**
     * Method to save a Game to the Database with an Update query
     * @param game Game to be saved
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void saveGame (Game game) throws IOException, SQLException {
        //Updejem l'estat de la partida i del jugador
        String queryGame = "UPDATE game " +
        "SET posX_player = " + game.getPositionFromPlayer()[0] + " , " +
        "posY_player = " + game.getPositionFromPlayer()[1] + " , " +
        "alive_player = " + game.getAliveFromPlayer() + " , " +
        "instant = " + game.getInstant() + " " +
        "WHERE name LIKE '" + game.getName() +
        "' AND player LIKE '" + game.getPlayerUserName() + "' ; ";

        SQLConnector.getInstance().insertQuery(queryGame);

        //Updejem l'estat de cada NPC
        int indexPlayer = game.getIndexPlayer();

        for (int i = 0; i < game.getNumCharacters(); i++) {
            if (i != indexPlayer) {
                String queryNPC = "UPDATE npc_game " +
                "SET posX = " +  game.getPositionFromCharacter(i)[0] + ", " +
                "posY = " + game.getPositionFromCharacter(i)[1] + ", " +
                "alive = " + game.getAliveFromCharacter(i) + ", " +
                "suspicionRole = '" + game.getSuspicionRoleFromCharacter(i) +
                "' WHERE game_name LIKE '" + game.getName() + "' AND player_name LIKE '" + game.getPlayerUserName() +
                "' AND NPC_id LIKE '" + game.getColorFromCharacter(i) +  "' ; ";

                SQLConnector.getInstance().insertQuery(queryNPC);
            }
        }

        //Afegim a la taula Logs la nova informació
        ArrayList<GameLogs> gameLogs = game.getGameLogs();

        int id_game = 0;
        String queryID_game = "SELECT num_game FROM game WHERE name LIKE '" + game.getName() + "' AND player LIKE '" + game.getPlayerUserName() +"';";
        ResultSet idResult = SQLConnector.getInstance().selectQuery(queryID_game);

        while (idResult.next()) {
            id_game = idResult.getInt("num_game");
        }

        for (int i = 0; i < gameLogs.size(); i++) {
            String queryLogs = "INSERT INTO gameLogs (game_name, player_name, gameLogs_id, NPC_id, room_name, instant, num_game) " +
                    " SELECT '" + game.getName() + "', '" + game.getPlayerUserName() + "' , "+ i + " ,'"+ gameLogs.get(i).getColorNPC() + "' ,'" + gameLogs.get(i).getRoomName() + "' , " + gameLogs.get(i).getInstant() + ", " + id_game +
                    " WHERE ("+ i +" NOT IN (SELECT gl.gameLogs_id FROM gameLogs AS gl WHERE gl.game_name LIKE '" + game.getName()
                    + "' AND gl.player_name LIKE '" + game.getPlayerUserName() + "'  ));";

            SQLConnector.getInstance().insertQuery(queryLogs);
        }
    }

    /**
     * Method to change the state of a game to start
     * @param gameName String name of the game
     * @param userName String username of the player
     */
    @Override
    public void startGame (String gameName, String userName) {
        //Updejem l'estat de la partida
        String query = "UPDATE game " +
                "SET started = 1, start_game_date = NOW() " +
                "WHERE name LIKE '" + gameName +
                "' AND player LIKE '" + userName + "' ;";

        try {
            SQLConnector.getInstance().insertQuery(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change the state of a game to finished and set the winner
     * @param gameName String game name
     * @param userName String username of the player
     * @param winner String name of the winner
     */
    @Override
    public void finishGame (String gameName, String userName, Boolean winner) {
        //Updejem l'estat de la partida
        String query = "UPDATE game " +
                "SET finished = 1, " +
                "winner = " + winner + " , finish_game_date = NOW() " +
                "WHERE name LIKE '" + gameName +
                "' AND player LIKE '" + userName + "' ;";

        try {
            SQLConnector.getInstance().insertQuery(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get a Game given the name of the game and the username of the player
     * @param gameName String name of the game
     * @param userName String username of the player
     * @return Game read
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public Game readGame (String gameName, String userName) throws  IOException, SQLException {
        String queryPlayer = "SELECT * FROM player WHERE name LIKE '" + userName + "' ;";
        ResultSet resultPlayer = SQLConnector.getInstance().selectQuery(queryPlayer);
        String email = "";
        String password = "";

        if(resultPlayer.next()) {
            email = resultPlayer.getString("email");
            password = resultPlayer.getString("password");
        }

        String query = "SELECT * FROM game WHERE player LIKE '" + userName + "' AND name LIKE '" + gameName + "' ;";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        String player_color = "";
        int posY_player = 0, posX_player = 0, map = 0, instant = 0;
        boolean alive_player = false, started = false, finished = false,  winner = false;

        //Guardem en arraylists tota la informació de la partida en concret del jugador
        while (result.next()) {
            player_color = result.getString("player_color");
            posY_player = result.getInt("posY_player");
            posX_player = result.getInt("posX_player");
            alive_player = result.getBoolean("alive_player");
            map = result.getInt("map");
            started = result.getBoolean("started");
            finished = result.getBoolean("finished");
            winner = result.getBoolean("winner");
            instant = result.getInt("instant");
        }

        //Mirem els jugadors de la partida
        ArrayList <Character> characters = new ArrayList<Character>();
        ArrayList <String> NPC_ids = new ArrayList<String>();
        ArrayList <Integer> posXs = new ArrayList<Integer>();
        ArrayList <Integer> posYs = new ArrayList<Integer>();
        ArrayList <Boolean> alives = new ArrayList<Boolean>();
        ArrayList <String> suspiciontRoles = new ArrayList<String>();
        ArrayList <String> roles = new ArrayList<String>();

        String query2 = "SELECT NPC_id, posX, posY, role, alive, suspicionRole, role FROM npc_game WHERE game_name LIKE '" + gameName + "'"
                + "AND player_name LIKE '" + userName + "' ;";

        ResultSet result2 = SQLConnector.getInstance().selectQuery(query2);

        while (result2.next()) {
            NPC_ids.add(result2.getString("NPC_id"));
            posXs.add(result2.getInt("posX"));
            posYs.add(result2.getInt("posY"));
            alives.add(result2.getBoolean("alive"));
            suspiciontRoles.add( result2.getString("suspicionRole"));
            roles.add(result2.getString("role"));
        }


        //Mirem la taula LOGS
        String queryLogs = "SELECT * FROM gameLogs WHERE game_name LIKE '" + gameName + "'"
                + "AND player_name LIKE '" + userName + "' ORDER BY gameLogs_id ASC;";

        ArrayList <GameLogs> gameLogs = new ArrayList<GameLogs>();
        ResultSet result3 = SQLConnector.getInstance().selectQuery(queryLogs);

        while (result3.next()) {
            gameLogs.add(new GameLogs(result3.getString("NPC_id"), result3.getString("room_name"), result3.getInt("instant")));
        }

        //Un cop llegim els NPC, ja disposem a guardar la informació en la Classe
        //1r afegim al player --> public Player(CharacterColors color, boolean alive, int[] position, String name, String email, String password)
        MapDAO mapDAO = new CSVMapDAO();

        Map mapReaded = mapDAO.getMap(map);
        TimeGame instantTimeGame = new TimeGame(instant);

        characters.add(new Player(CharacterColors.getCharacterColorFromString(player_color), alive_player, new int[]{posX_player, posY_player}, userName, email, password, null));

        //2n --> Fiquem els NPC --> public NPC(CharacterColors color, boolean alive, int[] position, NPCSuspition assignedRol)
        int numTripulants = 0;
        int numImpostors = 0;

        for (int j = 0; j < NPC_ids.size(); j++) {
            if (roles.get(j).equals("Impostor")) {
                characters.add(new Impostor(CharacterColors.getCharacterColorFromString(NPC_ids.get(j)), alives.get(j), new int[]{posXs.get(j), posYs.get(j)}, NPCSuspition.valueOf(suspiciontRoles.get(j)),null) );
                numImpostors++;
            } else if(roles.get(j).equals("Tripulant")) {
                characters.add(new NPC(CharacterColors.getCharacterColorFromString(NPC_ids.get(j)), alives.get(j), new int[]{posXs.get(j), posYs.get(j)}, NPCSuspition.valueOf(suspiciontRoles.get(j)), null) );
                numTripulants++;
            }
        }

        //Ja tenim tots els Characters, tota la info de la taula logs, ara fiquem la informació en els games
        //public Game(String name, int numJugadors, int  numImpostors, ArrayList<Character> characters, Map map, boolean started, boolean finished, String winner, ArrayList<GameLogs> gameLogs, int instant)
        Game game = new Game(gameName, numTripulants + 1, numImpostors, characters, mapReaded, started, finished, winner, gameLogs, instantTimeGame) ;
        for (Character character: characters) {
            character.setGame(game);
        }

        return game;
    }

    /**
     * Method that consults the database to get a game's name
     * @param gameName String that contains the game's name that has to be found
     * @param userName String that contains the player's username
     * @return a String with the game's name
     */
    @Override
    public String getGameName (String gameName, String userName) {
        String query = "SELECT name FROM game WHERE name LIKE '" + gameName + "' AND player LIKE '" + userName + "';";

        try {
            ResultSet result = SQLConnector.getInstance().selectQuery(query);
            String newGameName = null;

            while (result.next()) {
                newGameName = result.getString("name");
            }

            return (newGameName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return (null);
    }

    /**
     * Method that reads the game that has to be duplicated to show to the user the information
     * @param gameName String that contains the game that has to be duplicated name
     * @param userName String that contains the player's username
     * @return a String array with the game information (game that has to be duplicated)
     * @throws IOException in case anything goes wrong
     * @throws SQLException in case anything goes wrong
     */
    public String[] readInfoDuplicationGame (String gameName, String userName) throws IOException, SQLException {
        String [] arrayString = new String[4];
        String query = "SELECT player_color, map," +
                "(SELECT COUNT(NPC_id) FROM npc_game WHERE player_name LIKE '" + userName + "' AND game_name LIKE '" + gameName + "' AND role LIKE 'Tripulant') + 1 As numTripulants, " +
                "(SELECT COUNT(NPC_id) FROM npc_game WHERE player_name LIKE '" + userName +"' AND game_name LIKE '" + gameName + "' AND role LIKE 'Impostor') AS numImpostors " +
                "FROM game WHERE player LIKE '" + userName + "' AND name LIKE '" + gameName + "' ;";

        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        while(result.next()){
            arrayString[0] = result.getString("player_color");
            arrayString[1] = result.getString("map");
            arrayString[2] = result.getString("numTripulants");
            arrayString[3] = result.getString("numImpostors");
        }

        return arrayString;
    }

    /**
     * Method that duplicates a game by giving the original game and the string of the new Game
     * @param name String new Game name
     * @param game Game game to be duplicated
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void duplicateGame (String name, Game game) throws IOException {
        if (checkGame(name)) {
            System.out.println("Error el nom ja existeix");
        } else if (!checkGame(name)) {
            System.out.println("correcte");
            addGame(game);
        }
    }

    /**
     * Deletes a game from the database
     * @param name String name of the game
     * @param userName String username
     * @throws IOException in case anything goes wrong
     */
    @Override
    public void deleteGame (String name, String userName) throws IOException {
        String queryNPCgame = "DELETE FROM npc_game WHERE player_name LIKE '" + userName + "' and game_name LIKE '" +name+ "' ;";
        SQLConnector.getInstance().deleteQuery(queryNPCgame);

        String queryLogs = "DELETE FROM gameLogs WHERE player_name LIKE '" + userName + "' and game_name LIKE '" +name+ "' ;";
        SQLConnector.getInstance().deleteQuery(queryLogs);

        String queryGame = "DELETE FROM game WHERE player LIKE '" + userName + "' and name LIKE '" +name+ "' ;";
        SQLConnector.getInstance().deleteQuery(queryGame);
    }

    /**
     * Checks if a game with a specific name exists in the Database
     * @param name of the game to check
     * @return true if the game exists
     *         false if the game doesnt
     * @throws IOException thrown if an error occurred while loading a file
     */
    @Override
    public boolean checkGame (String name) throws IOException {
        String query = "SELECT name FROM game WHERE name = '" + name + "';";
        Game game = null;
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            if(result.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
