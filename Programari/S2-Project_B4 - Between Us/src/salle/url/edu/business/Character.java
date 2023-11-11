package salle.url.edu.business;

import java.awt.*;

/**
 * Abstract Class that defines the Character
 * The Character is everything that moves around the map in the game and
 * has some actions possible depending its role
 */
public abstract class Character {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    protected CharacterColors color;
    protected boolean alive;
    protected int[] position;
    protected Game game;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor for Character
     * @param color color of the Character, it is of type CharacterColors
     * @param alive boolean that determines if it is alive or dead
     * @param position position that the character has in the map
     * @param game Game in witch the character is playing
     */
    public Character(CharacterColors color, boolean alive, int[] position, Game game) {
        this.color = color;
        this.alive = alive;
        this.position = position;
        this.game = game;
    }

    /**
     * Default Constructor for the character
     * It initializes Character with default values
     */
    public Character(){
        color = null;
        alive = true;
        position = null;
        this.game = null;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Moves the character in the specified direction if it is possible
     * Possible directions are UP, DOWN, LEFT and RIGHT
     * @param direction is an integer containing if the character has to move up, down, left or right
     */
    public synchronized void moveCharacter(int direction){

        if (direction == CurrentGameManager.UP){
            position[1]--;
        }else if (direction == CurrentGameManager.DOWN){
            position[1]++;
        }else if (direction == CurrentGameManager.LEFT){
            position[0]--;
        }else if (direction == CurrentGameManager.RIGHT){
            position[0]++;
        }
    }

    // GETTERS and SETTERS----------------------------------------------------------------------------------------------
    /**
     * Getter of the color of the character
     * @return CharacterColor
     */
    public CharacterColors getColor() {
        return this.color;
    }

    /**
     * Getter of the color in string format of the character
     * @return String with the character color
     */
    public String getColorString() {return color.colorToString();}

    /**
     * Getter of the position of the Character
     * @return position in array format [x,y]
     */
    public int[] getPosition(){return  position;}

    /**
     * Getter of the alive state of the Character
     * @return boolean if the character is alive or not
     */
    public boolean isAlive() { return alive; }

    /**
     * Setter of the color for the character
     * @param alive CharacterColor
     */
    public void setAlive(boolean alive) { this.alive = alive; }

    /**
     * Setter of the color for the character
     * @param playerColor CharacterColor
     */
    public void setColor(CharacterColors playerColor) {
        this.color = playerColor;
    }

    /**
     * Setter of the position of the Character
     * @param position int[] = [x,y];
     */
    public void setPosition(int[] position){
        this.position = position;
    }

    /**
     * Getter of the image Path for the color
     * If alive it will return one image
     * If dead it will return another image
     * @return path to the image for the character
     */
    public String getColorPath(){
        if (alive) return color.getPathAlive();
        return color.getPathDead();
    }

    /**
     * Setter for Game
     * @param game Game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Method that gets the color
     * @return of type Color
     */
    public Color getColorColor() {
        return color.getColor();
    }
}
