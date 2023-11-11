package salle.url.edu.business;

import java.util.ArrayList;

/**
 * Class Map, model for the map data
 */
public class Map {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final int mapId;
    private int[] initialRoom;
    private int[] dimension;
    private ArrayList<Room> rooms;
    private ArrayList<Integer[]> roomPositions;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor for Map
     * @param mapId int mapID
     * @param initialRoom int[] must be 2D
     * @param dimension int[], 2D for Width and Height
     * @param rooms ArrayList<Room>
     */
    public Map(int mapId, int[] initialRoom, int[] dimension, ArrayList<Room> rooms) {
        this.mapId = mapId;
        this.initialRoom = initialRoom;
        this.dimension = dimension;
        this.rooms = rooms;

        roomPositions = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                Integer[] position = new Integer[2];

                position[0] = k;
                position[1] = j;

                roomPositions.add(position);
            }
        }
    }

    /**
     * Constructor by default
     * @param mapId int mapID
     */
    public Map(int mapId) {
        this.mapId = mapId;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Checks if a Character can move from one position to a specific direction
     * @param pos int[] position of the character
     * @param move direction to move
     * @return boolean with true (if character can move) or false (if not)
     */
    public boolean canMove(int[] pos, int move) {
        boolean canMove = false;
        int pastMove = 4;
        int[] futurePos = new int[2];

        // Si move == 0  --> UP
        if (move == CurrentGameManager.UP) {
            futurePos[0] = pos[0];
            futurePos[1] = pos[1] - 1;

            if (futurePos[1] >= 0) { // Perquè no surti del mapa per dalt
                Room futureRoom = rooms.get(getRoomIndex(futurePos));
                Room actualRoom = rooms.get(getRoomIndex(pos));

                if (futureRoom.getRoomType().isDown() && actualRoom.getRoomType().isUp()) {
                    canMove = true;
                }
            }
        }

        // Si move == 1  --> DOWN
        if (move == CurrentGameManager.DOWN/*1*/) {
            futurePos[0] = pos[0];
            futurePos[1] = pos[1] + 1;

            if (futurePos[1] <= 3) { // Perquè no surti del mapa per avall
                Room futureRoom = rooms.get(getRoomIndex(futurePos));
                Room actualRoom = rooms.get(getRoomIndex(pos));

                if (futureRoom.getRoomType().isUp() && actualRoom.getRoomType().isDown()) {
                    canMove = true;
                }
            }
        }

        // Si move == 2  --> LEFT
        if (move == CurrentGameManager.LEFT/*2*/) {
            futurePos[0] = pos[0] - 1;
            futurePos[1] = pos[1];

            if (futurePos[0] >= 0) { // Perquè no surti del mapa per la dreta
                Room futureRoom = rooms.get(getRoomIndex(futurePos));
                Room actualRoom = rooms.get(getRoomIndex(pos));

                if (futureRoom.getRoomType().isRight() && actualRoom.getRoomType().isLeft()) {
                    canMove = true;
                }
            }
        }

        // Si move == 3  --> RIGHT
        if (move == CurrentGameManager.RIGHT/*3*/) {
            futurePos[0] = pos[0] + 1;
            futurePos[1] = pos[1];

            if (futurePos[0] <= 3) { // Perquè no surti del mapa per dalt
                Room futureRoom = rooms.get(getRoomIndex(futurePos));
                Room actualRoom = rooms.get(getRoomIndex(pos));

                if (futureRoom.getRoomType().isLeft() && actualRoom.getRoomType().isRight()) {
                    canMove = true;
                }
            }
        }

        return canMove;
    }

    /**
     * Compares two positions if they are the same
     * @param position1 int[] position of the room in the map
     * @param position2 int[] position of the room in the array
     * @return boolean true (if is the same) or false (if not the same)
     */
    public boolean checkPosition(Integer[] position1, int[] position2) {
        return position1[0] == position2[0] && position1[1] == position2[1];
    }

    //GETTERS-----------------------------------------------------------------------------------------------------------
    /**
     * Converts the position array to the index of the ArrayList
     * @param position int[]
     * @return int index of the room that we are looking for
     */
    public int getRoomIndex(int[] position) {
        for (int i = 0; i < roomPositions.size(); i++) {
            if (checkPosition(roomPositions.get(i), position)) {
                return i;
            }
        }

        return (-1);
    }

    /**
     * Getter for the name of a room given a specific position
     * @param position int[]
     * @return String name of the room
     */
    public String getNameRoomPosition(int[] position) {
        return rooms.get(getRoomIndex(position)).getRoomName();
    }

    /**
     * Getter for the map id
     * @return int
     */
    public int getMapId() {
        return mapId;
    }

    /**
     * Getter for the initial room
     * @return int[] position of the initial room
     */
    public int[] getInitialRoom() {
        return initialRoom;
    }

    /**
     * Getter for the dimension of the Map
     * @return int[]
     */
    public int[] getDimension() {
        return dimension;
    }

    /**
     * Getter for the rooms List
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Checks if the room with the given position has the action of LOGS
     * @param position int[]
     * @return boolean
     */
    public boolean roomIsActionLogs(int[] position) {
        Room room = rooms.get(position[0] + position[1] * 4);
        if (room.getActionType() == ActionType.LOG) return true;
        return false;
    }

    /**
     * Checks if the room with the given position has the action of CLASSIFICATION
     * @param position int[]
     * @return boolean
     */
    public boolean roomIsActionClassification(int[] position) {
        Room room = rooms.get(position[0] + position[1] * 4);
        if (room.getActionType() == ActionType.CLASSIFICATION) return true;
        return false;
    }

    /**
     * Checks if the room has any vent
     * @param position int[]
     * @return boolean true (if room has ventilation) or false (if not)
     */
    public boolean roomHasVent(int[] position) {
        Room room = rooms.get(position[0] + position[1] * 4);
        return room.getHasVent();
    }

    /**
     * Getter for the image path of a specified room in the current Game map
     * @param roomIndex int
     * @return String with the room's path
     */
    public String getRoomPath(int roomIndex) {
        return rooms.get(roomIndex).getRoomPath();
    }
}
