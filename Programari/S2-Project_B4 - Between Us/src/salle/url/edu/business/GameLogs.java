package salle.url.edu.business;

/**
 * Class storing representing a specific log of a game
 */
public class GameLogs {
    private final String colorNPC;
    private final String roomName;
    private final int instant;

    /**
     * Constructor
     * @param colorNPC color of the NPC
     * @param roomName Name of the room it is located
     * @param instant instant representing time since it has ocurred
     */
    public GameLogs(String colorNPC, String roomName, int instant) {
        this.colorNPC = colorNPC;
        this.roomName = roomName;
        this.instant = instant;
    }

    // GETTERS__________________________________________________________________________________________________________
    /**
     * Getter for the color of the NPC
     * @return String with the NPC color
     */
    public String getColorNPC() {
        return colorNPC;
    }

    /**
     * Getter of the roomName
     * @return String with the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Getter of the instant
     * @return int with the instant
     */
    public int getInstant() {
        return instant;
    }
}
