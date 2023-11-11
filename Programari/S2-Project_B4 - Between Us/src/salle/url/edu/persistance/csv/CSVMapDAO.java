package salle.url.edu.persistance.csv;

import salle.url.edu.business.ActionType;
import salle.url.edu.business.Map;
import salle.url.edu.business.Room;
import salle.url.edu.business.RoomType;
import salle.url.edu.persistance.MapDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CSVMapDAO class is an implementation of the MapDAO interface for .csv files persistence
 */
public class CSVMapDAO implements MapDAO {
    /**
     * Gets one map from a CSV file given a path to the file
     * @param path String path to the file
     * @return Map
     * @throws IOException in case anything goes wrong
     */
    @Override
    public Map getMap(String path) throws IOException {
        return parseCSVToMap(path);
    }

    /**
     * Gets one map from a CSV file given an id of the map
     * @param id int id of the map
     * @return Map
     * @throws IOException in case anything goes wrong
     */
    @Override
    public Map getMap(int id) throws IOException {
        String path = "files/maps/map-" + id + ".csv";

        return parseCSVToMap(path);
    }

    /**
     * Method used in both of the getMap methods used to convert a csv file to a Map object
     * @param path String
     * @return Map
     * @throws IOException in case anything goes wrong
     */
    private Map parseCSVToMap(String path) throws IOException {
        String output;
        String[] splitedOutput;
        Map map = null;

        BufferedReader br = new BufferedReader( new FileReader(path));
        output = br.readLine(); //Skip 1st line
        output = br.readLine();

        splitedOutput = output.split(",");

        int mapID = Integer.parseInt(splitedOutput[0]);
        int[] initialRoom = {Integer.parseInt(splitedOutput[1]), Integer.parseInt(splitedOutput[2])};
        int[] dimension = {Integer.parseInt(splitedOutput[3]), Integer.parseInt(splitedOutput[4])};

        ArrayList<Room> rooms = new ArrayList<>();

        int length = dimension[0]*dimension[1]*3;

        for (int i = 5; i < length + 4; i+=3){
            RoomType roomId = RoomType.values()[(Integer.parseInt(splitedOutput[i]))];
            boolean hasVent = Boolean.parseBoolean(splitedOutput[i+1]);
            ActionType action = ActionType.values()[(Integer.parseInt(splitedOutput[i+2]))];
            rooms.add(new Room(roomId, hasVent, action));
        }

        map = new Map(mapID, initialRoom, dimension, rooms);

        return map;
    }
}
