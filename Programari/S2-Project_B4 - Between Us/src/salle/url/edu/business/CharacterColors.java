package salle.url.edu.business;

import java.awt.*;

/**
 * Enumeration for colors of the Characters
 */
public enum CharacterColors {
    RED("RED", Color.RED, "files/assets/npc/NPC_RED.png", "files/assets/npc/NPC_DEAD_RED.png" ),
    BLUE("BLUE", new Color(91, 93, 199), "files/assets/npc/NPC_BLUE.png", "files/assets/npc/NPC_DEAD_BLUE.png"),
    GREEN("GREEN", Color.GREEN, "files/assets/npc/NPC_LIGHT_GREEN.png", "files/assets/npc/NPC_DEAD_LIGHT_GREEN.png"),
    PINK("PINK", Color.PINK, "files/assets/npc/NPC_PINK.png", "files/assets/npc/NPC_DEAD_PINK.png"),
    ORANGE("ORANGE", Color.ORANGE, "files/assets/npc/NPC_ORANGE.png", "files/assets/npc/NPC_DEAD_ORANGE.png"),
    YELLOW("YELLOW", Color.YELLOW, "files/assets/npc/NPC_YELLOW.png", "files/assets/npc/NPC_DEAD_YELLOW.png"),
    BLACK("BLACK", new Color(92, 92, 92), "files/assets/npc/NPC_BLACK.png", "files/assets/npc/NPC_DEAD_BLACK.png"),
    PURPLE("PURPLE", new Color(102,64,155), "files/assets/npc/NPC_PURPLE.png", "files/assets/npc/NPC_DEAD_PURPLE.png"),
    WHITE("WHITE", Color.WHITE, "files/assets/npc/NPC_WHITE.png", "files/assets/npc/NPC_DEAD_WHITE.png"),
    CYAN("CYAN", Color.CYAN, "files/assets/npc/NPC_CYAN.png", "files/assets/npc/NPC_DEAD_CYAN.png"),
    DARK_GREEN("DARK_GREEN", new Color(25,128,71), "files/assets/npc/NPC_GREEN.png", "files/assets/npc/NPC_DEAD_GREEN.png"),
    BROWN("BROWN", new Color(111,77,44), "files/assets/npc/NPC_BROWN.png","files/assets/npc/NPC_DEAD_BROWN.png");

    private String toString;
    private Color color;
    private String pathAlive;
    private String pathDead;

    /**
     * Constructor of the enumerations
     * @param toString  String equivalent of the color
     * @param color Color value
     * @param pathAlive Path of the image of the NPC alive
     * @param pathDead  Path of the image of the NPC Dead
     */
    CharacterColors(String toString, Color color, String pathAlive, String pathDead) {
        this.toString = toString;
        this.color = color;
        this.pathAlive = pathAlive;
        this.pathDead = pathDead;
    }

    /**
     * Getter of the Color value
     * @return Color value
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Getter of the String equivalent of the color
     * @return String of the color
     */
    public String colorToString(){
        return this.toString;
    }

    /**
     * Getter of the path of the image if the NPC when alive
     * @return String with the path of the alive NPC image
     */
    public String getPathAlive() {
        return pathAlive;
    }

    /**
     * Getter of the path of the image if the NPC is dead
     * @return String with the path of the dead NPC image
     */
    public String getPathDead() {
        return pathDead;
    }

    /**
     * Static method for getting the CharacterColor from a specified String
     * The String must be equal to the name of one of the colors
     * @param color String
     * @return CharacterColors
     */
    public static CharacterColors getCharacterColorFromString(String color) {
        CharacterColors[] characterColors = CharacterColors.values();

        for (int i = 0; i < CharacterColors.values().length; i++){

            if (characterColors[i].colorToString().equalsIgnoreCase(color)) {
                return characterColors[i];
            }
        }

        return null;
    }
}
