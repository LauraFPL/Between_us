package salle.url.edu.persistance;

import java.io.FileNotFoundException;

/**
 * ConfigDAO interface for Configuration persistence
 */
public interface ConfigDAO {
    /**
     * Gets the data inside the config file
     * @return String[] containing the data
     * @throws FileNotFoundException
     */
    String[] getConfig() throws FileNotFoundException;
}
