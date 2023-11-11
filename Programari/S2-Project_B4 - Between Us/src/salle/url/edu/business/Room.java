package salle.url.edu.business;

/**
 * Class Room, has the information of a Room
 */
public class Room {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final RoomType room;
    private final boolean hasVent;
    private final ActionType action;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor of Room
     * @param room RoomType
     * @param hasVent boolean
     * @param action ActionType
     */
    public Room( RoomType room, boolean hasVent, ActionType action) {
        this.room = room;
        this.hasVent = hasVent;
        this.action = action;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Method used to check if the name of the Room is equal to a given String
     * @param name String
     * @return boolean
     */
    public boolean checkName(String name) {
        return room.getRoomName().equals(name);
    }

    //GETTERS___________________________________________________________________________________________________________
    /**
     * Getter of the RoomType
     * @return RoomType
     */
    public RoomType getRoomType(){
        return this.room;
    }

    /**
     * Getter for the roomName from the RoomType
     * @return String
     */
    public String getRoomName() {
        return room.getRoomName();
    }

    /**
     * Getter of the ActionType
     * @return ActionType
     */
    public ActionType getActionType() {
        return action;
    }

    /**
     * Getter of hasVent
     * @return boolean
     */
    public boolean getHasVent() {return hasVent;}

    /**
     * Method that gets the room path
     * @return a String containing the room path
     */
    public String getRoomPath() {
        return room.getImagePath();
    }
}
