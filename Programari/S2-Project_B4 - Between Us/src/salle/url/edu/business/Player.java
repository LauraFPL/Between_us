package salle.url.edu.business;

/**
 * Class Player,
 * Information and controls of the player
 */
public class Player extends Character {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final String name;
    private final String email;
    private final String password;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor of Player
     * @param color CharacterColor contains the color of the player
     * @param alive boolean that contains true (alive) or false (dead)
     * @param position int[] contains the position of the player
     * @param name String contains the name of the player
     * @param email String contains the email of the player
     * @param password String contains the password that the player invented
     * @param game Game contains the game information
     */
    public Player(CharacterColors color, boolean alive, int[] position, String name, String email, String password, Game game) {
        super(color, alive, position, game);
        this.name = name;
        this.email = email;
        this.password = password;

    }

    /**
     * Basic constructor of Player
     * @param name String contains the name of the player
     * @param email String contains the email of the player
     * @param password String contains the password that the player invented
     */
    public Player (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // METHODS----------------------------------------------------------------------------------------------------------
    /**
     * Method for moving the Player in a specified direction
     * @param direction int direction to move
     */
    public void move(int direction){
        if(game.getMap().canMove(position, direction)){
            if (direction == CurrentGameManager.UP) {
                position[1]--;
            } else if (direction == CurrentGameManager.DOWN) {
                position[1]++;
            } else if (direction == CurrentGameManager.LEFT) {
                position[0]--;
            } else if (direction == CurrentGameManager.RIGHT) {
                position[0]++;
            }
        }

        //System.out.println("new position: " + position[0] + ", " + position[1]);
    }

    // GETTERS and SETTERS----------------------------------------------------------------------------------------------
    /**
     * Getter for the name of the Player
     * @return String with the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the email of the Player
     * @return String with the player's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for the password of the Player
     * @return String with the player's passwor
     */
    public String getPassword() {
        return password;
    }
}
