package salle.url.edu.persistance.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import salle.url.edu.persistance.ConfigDAO;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * JSONConfigDAO implements ConfigDAO
 */
public class JSONConfigDAO implements ConfigDAO {
    /**
     * Gets the data inside the config file
     * @return String[] containing the data
     * @throws FileNotFoundException in case anything goes wrong
     */
    @Override
    public String[] getConfig() throws FileNotFoundException {
        String[] configString = new String[0];
        Gson gson = new Gson();
        String path = "files/config.json";
        JsonElement e = JsonParser.parseReader(new FileReader(path));

        if (e.isJsonObject()) {
            JsonObject config = e.getAsJsonObject();
            String username = config.get("Username").getAsString();
            String ip = config.get("ServerIP").getAsString();
            String password = config.get("Password").getAsString();
            String database = config.get("Name").getAsString();
            String port = config.get("Port").getAsString();

            configString = new String[]{username, ip, password, database, port};
        } else {
            System.err.println("Error while reading json");
        }

        return configString;
    }
}
