package salle.url.edu.business;

/**
 * Enumeration RoomType
 * Defines the types of room that we can use
 * Corridors are included
 */
public enum RoomType {
    EMPTY("EMPTY", "files/assets/rooms/EMPTY.png", false, false, false, false),
    MEDBAY("MEDBAY", "files/assets/rooms/MEDBAY.png", false, true, true, false),
    SECURITY("SECURITY", "files/assets/rooms/SECURITY.png", true, true, true, true),
    CAFETERIA("CAFETERIA", "files/assets/rooms/CAFETERIA.png", true, false, false, true),
    TECHNOLOGY("TECHNOLOGY", "files/assets/rooms/TECHNOLOGIE.png", false, false, true, true),
    STORAGE("STORAGE", "files/assets/rooms/STORAGE.png", true, true, true, true),
    CORRIDOR_T_BOTTOM("T_DOWN", "files/assets/rooms/CORRIDOR_T_DOWN.png", true, false, true, true),
    CORRIDOR_HORIZONTAL("HORIZONTAL", "files/assets/rooms/CORRIDOR_HORIZONTAL.png", false, false, true, true),
    CORRIDOR_VERTICAL("VERTICAL", "files/assets/rooms/CORRIDOR_VERTICAL.png", true, true, false, false),
    CORRIDOR_T_UP("T_UP", "files/assets/rooms/CORRIDOR_T_UP.png", false, true, true, true),
    CORRIDOR_CORNER_TOP_LEFT("TOP_LEFT", "files/assets/rooms/CORRIDOR_CORNER_TOP_LEFT.png", false, true, false, true),
    CORRIDOR_CORNER_BOTTOM_LEFT("BOTTOM_LEFT", "files/assets/rooms/CORRIDOR_CORNER_BOTTOM_LEFT.png", true, false, false, true),
    CORRIDOR_T_RIGHT("T_RIGHT", "files/assets/rooms/CORRIDOR_T_RIGHT.png", true, true, true, false),
    CORRIDOR_T_LEFT("T_LEFT", "files/assets/rooms/CORRIDOR_T_LEFT.png", true, true, false, true),
    CORRIDOR_CORNER_BOTTOM_RIGHT("BOTTOM_RIGHT", "files/assets/rooms/CORRIDOR_CORNER_BOTTOM_RIGHT.png", true, false, true, false),
    CORRIDOR_CORNER_TOP_RIGHT("TOP_RIGHT", "files/assets/rooms/CORRIDOR_CORNER_TOP_RIGHT.png", false, true, true, false);

    private final String roomName;
    private final String imagePath;
    private final boolean up;
    private final boolean down;
    private final boolean left;
    private final boolean right;

    /**
     * Constructor for the enumeration
     * @param roomName String defines the name given to the room
     * @param imagePath String path to the image file of the room
     * @param up boolean that defines if it can go up
     * @param down boolean that defines if it can go down
     * @param left boolean that defines if it can go left
     * @param right boolean that defines if it can go right
     */
    RoomType(String roomName, String imagePath, boolean up, boolean down, boolean left, boolean right) {
        this.roomName = roomName;
        this.imagePath = imagePath;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    /**
     * Getter for the roomName
     * @return String with the room's name
     */
    public String getRoomName() { return roomName; }

    /**
     * Getter for the imagePath
     * @return String with the path image
     */
    public String getImagePath(){ return this.imagePath;}

    /**
     * Getter for knowing if the Character can go up based on this room
     * @return boolean true (if room can be accessed through up) or false (if not)
     */
    public boolean isUp() { return up; }

    /**
     * Getter for knowing if the Character can go down based on this room
     * @return boolean true (if room can be accessed through down) or false (if not)
     */
    public boolean isDown() { return down; }

    /**
     * Getter for knowing if the Character can go left based on this room
     * @return boolean true (if room can be accessed through left) or false (if not)
     */
    public boolean isLeft() { return left; }

    /**
     * Getter for knowing if the Character can go right based on this room
     * @return boolean true (if room can be accessed through right) or false (if not)
     */
    public boolean isRight() { return right; }
}
