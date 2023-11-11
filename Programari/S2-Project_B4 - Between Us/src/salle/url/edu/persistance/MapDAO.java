package salle.url.edu.persistance;

import salle.url.edu.business.Map;

import java.io.IOException;

/**
 * MapDAO interface for Map persistence
 */
public interface MapDAO {
    /**
     * Method to get a map given a specific path to a file
     * @param path String path to the file
     * @return Map or null if it wasn't found
     * @throws IOException in case anything goes wrong
     */
    Map getMap(String path) throws IOException;

    /**
     * Method to get a map given a specific path to a file
     * @param id int id of the map
     * @return Map or null if it wasn't found
     * @throws IOException in case anything goes wrong
     */
    Map getMap(int id) throws IOException;
}
