package salle.url.edu.business;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class Game
 * Has all the information of a game
 */
public class Game {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private final String name;
    private final int numTripulants;
    private final int numImpostors;
    private boolean started;
    private final boolean finished;
    private final Boolean winner;
    private TimeGame instant;
    private final ArrayList<Character> characters;
    private final ArrayList<GameLogs> gameLogs;
    private Map map;
    private int lastTimeChecked;
    //Auxiliars

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor that is called when we want to create a new Game
     * @param name name of the game
     * @param numJugadors number of tripulants
     * @param numImpostors number of impostors
     * @param playerColor color chosen by the player
     * @param currentPlayer Player
     * @param map map of the game
     */
    public Game(String name, int numJugadors, int  numImpostors, CharacterColors playerColor, Player currentPlayer, Map map) {
        System.out.print("Game > Creating game ... ");

        this.name = name;
        this.numTripulants = numJugadors - numImpostors;
        this.numImpostors = numImpostors;
        this.started = false;
        this.finished = false;
        this.winner = null;

        gameLogs = new ArrayList<>();
        instant = new TimeGame();

        //Generem els jugadors de la partida
        this.characters = new ArrayList<>();
        Player player = new Player(playerColor, true, new int[]{map.getInitialRoom()[0], map.getInitialRoom()[1]}, currentPlayer.getName(), currentPlayer.getEmail(), currentPlayer.getPassword(), this);
        characters.add(player);

        CharacterColors[] colors = CharacterColors.values();

        for (int i = 0; i < numJugadors - 1 - numImpostors; i++){
            NPC npc = new NPC(chooseRandomColor(player.getColor(), colors), new int[]{map.getInitialRoom()[0], map.getInitialRoom()[1]}, this);
            characters.add(npc);
        }

        for (int i = 0; i < numImpostors; i++){
            Impostor imp = new Impostor(chooseRandomColor(player.getColor(), colors), new int[]{map.getInitialRoom()[0], map.getInitialRoom()[1]}, this);
            characters.add(imp);
        }

        this.map = map;

        lastTimeChecked = -60;
    }

    /**
     * Constructor that is called when we want to read all the games from SQL, we don't need GamesLogs neither instant
     * @param name name of the game
     * @param numTripulants number of tripulants
     * @param numImpostors number of impostors
     * @param characters all the characters of the game (Player, tripulants and impostors)
     * @param map map of the game
     * @param started boolean that indicates if it has been started
     * @param finished boolean that indicates if it has been finished
     * @param winner winner of the game if it has been finished
     */
    public Game(String name, int numTripulants, int  numImpostors, ArrayList<Character> characters, Map map, boolean started, boolean finished, Boolean winner) {
        this.name = name;
        this.numTripulants = numTripulants;
        this.numImpostors = numImpostors;
        this.characters = characters;
        this.started = started;
        this.finished = finished;
        this.winner = winner;
        this.map = map;
        gameLogs = new ArrayList<>();

        lastTimeChecked= -60;
    }

    /**
     * Constructor that is called when we want to read the current game
     * @param name String with the game name
     * @param numTripulants int with the number of players
     * @param numImpostors int with the number of impostors
     * @param characters ArrayList of characters
     * @param map of type Map containing the map
     * @param started boolean containing if the game has started
     * @param finished boolean containing if the game has finished
     * @param winner of type Boolean contains the winner
     * @param gameLogs ArrayList of GameLogs
     * @param instant of type TimeGame
     */
    public Game(String name, int numTripulants, int  numImpostors, ArrayList<Character> characters, Map map, boolean started, boolean finished, Boolean winner, ArrayList<GameLogs> gameLogs, TimeGame instant) {
        this.name = name;
        this.numTripulants = numTripulants;
        this.numImpostors = numImpostors;
        this.characters = characters;
        this.started = started;
        this.finished = finished;
        this.winner = winner;
        this.map = map;
        this.instant = instant;
        this.gameLogs = gameLogs;

        lastTimeChecked= -60;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Selects an existing CharacterColor that hasn't been chosen already and that is diferent
     * from the color of the player
     * @param playerColor color of the player
     * @return CharacterColor
     */
    private CharacterColors chooseRandomColor(CharacterColors playerColor, CharacterColors[] colors) {
        while (true) {
            int rand = new Random().nextInt(CharacterColors.values().length);

            if (colors[rand] != null && colors[rand] != playerColor) {
                CharacterColors pick = colors[rand];
                colors[rand] = null;
                return pick;
            }
        }
    }

    /**
     *  Moves the character in the specified direction if it is possible
     *  Possible directions are UP, DOWN, LEFT and RIGHT
     *  @param direction contains the direction to move the player
     */
    public void movePlayer(int direction){
       Player player = (Player) characters.get(getIndexPlayer());
       player.move(direction);
    }

    /**
     * Checks the results of the player classification Table
     * @return Boolean that says if the player is correct (so he would win), or is wrong.
     * If he can't check becouse of the time, the variable is null
     */
    public Boolean checkResultsFromPlayer(){
        Boolean correct = true;

        if (instant.getTime() - lastTimeChecked >= 60) {
            for (int i = 0; i < characters.size() && correct; i++) {

                lastTimeChecked = instant.getTime();
                if (characters.get(i) instanceof NPC) {
                    correct = correct && ((NPC) characters.get(i)).checkRoleSusRole();
                }

            }
        } else {correct = null;}

        return correct;
    }

    /**
     * Stops all the Threads of the game whenever a game is exited
     */
    public void stopGame() {

        for (int i = 0; i < characters.size(); i++) {

            if (characters.get(i) instanceof NPC) {
                ((NPC) characters.get(i)).stop();
            }
        }

        instant.stop();
    }

    /**
     * Starts all the Thread of the game whenever a game is started
     */
    public void startThreads () {
        for (int i = 0; i < characters.size(); i++) {

            if (characters.get(i) instanceof NPC) {
                if (characters.get(i) instanceof Impostor) {
                    Thread impostorNPC = new Thread((Impostor)characters.get(i));
                    impostorNPC.start();
                    //System.out.println("\u001B[32m" + "Game > startThread(), started Thread of Impostor " + characters.get(i).toString() + " (index = " + i + " , name = " + characters.get(i).getColorString() + " )" + "\u001B[0m");

                } else {
                    Thread threadNPC = new Thread((NPC)characters.get(i));
                    threadNPC.start();
                    //System.out.println("\u001B[32m" + "Game > startThread(), started Thread of NPC " + characters.get(i).toString() + " (index = " + i + " , name = " + characters.get(i).getColorString() + " )" + "\u001B[0m");

                }
            }
        }

        Thread instantThread = new Thread(instant);
        instantThread.start();
        //System.out.println("Game > startThread(), started Thread instant: " + instantThread.toString());

    }

    /**
     * Add a gameLog in the gameLogs of the game
     */
    public void addGameLogs(String color, String nameRoom, int time){

        gameLogs.add(new GameLogs(color,nameRoom,time));
    }

    //GETTERS I SETTERS-----------------------------------------------------------------------------------------------------------
    /**
     * Getter for the name of the Game
     * @return String with the game name
     */
    public String getName() {return name;}

    /**
     * Getter for knowing if the game has started
     * @return boolean if the game started or not
     */
    public boolean getStarted() {return started;}

    /**
     * Getter for knowing if the game has finished
     * @return boolean if the game finished or not
     */
    public boolean getFinished() {return finished;}

    /**
     * Getter for the winner of the game
     * @return String colorName of the winner
     */
    public Boolean getWinner() {return winner;}

    /**
     * Getter for the time that has passed since the start of the game in seconds
     * @return int with the instant
     */
    public int getInstant(){ return instant.getTime();}

    /**
     * Getter for the map of the game
     * @return Map with the game's map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Getter for the characters of the game
     * @return ArrayList<Character>
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Geter for the gameLogs of the game
     * @return ArrayList<GameLogs>
     */
    public ArrayList<GameLogs> getGameLogs() { return gameLogs; }

    /**
     * Getter for the id of the map of the game
     * @return int with the map's id
     */
    public int getMapId(){ return map.getMapId();}

    /**
     * Getter for the position of the specified character in the map
     * @param index index representing the character
     * @return int[] position ([x,y])
     */
    public int [] getPositionFromCharacter(int index){
        if (index > -1 && index < characters.size()) {
            return characters.get(index).getPosition();
        }

        return null;
    }

    /**
     * Setter that say that the game has started
     */
    public void hasStarted(){ started = true; }

    /**
     * Getter for the number of characters that are playing the game
     * @return int
     */
    public int getNumCharacters(){return  characters.size();}

    /**
     * Method that get the Color of the character indicated
     * @param index index of the character you want to know his color.
     * @return String with the name of the color of the character
     */
    public String getColorFromCharacter(int index){
        if (index > -1 && index < characters.size()) {
            return characters.get(index).getColorString();
        }

        return null;
    }

    /**
     * Method that get the Color of the player
     * @return String with the name of the color of the character
     */
    public String getColorPlayerString(){
        return getColorFromCharacter(getIndexPlayer());
    }

    /**
     * Getter for the position of the player in the game
     * @return an int array with the position of the player
     */
    public int[] getPositionFromPlayer(){
        return getPositionFromCharacter(getIndexPlayer());
    }

    /**
     * Getter for the alive state of the Player
     * @return boolean if alive or not from player
     */
    public boolean getAliveFromPlayer(){
        return getAliveFromCharacter(getIndexPlayer());
    }

    /**
     * Method that get the role of the character indicated
     * @param index index of the character you want to know his role.
     * @return String with: "Impostor", "Player" or "Tripulant" depending on the role of the character
     */
    public String getRoleCharacter(int index){
        if (index > -1 && index < characters.size()) {
            Character character = characters.get(index);

            if (character instanceof Impostor) {
                return "Impostor";
            } else if (character instanceof  Player) {
                return "Player";
            } else { return "Tripulant";}
        }

        return null;
    }

    /**
     * Getter for the suspected Role of the player
     * @param index int representing the character
     * @return String suspicion
     */
    public String getSuspicionRoleFromCharacter(int index){
        if (index > -1 && index < characters.size()) {

            if (characters.get(index) instanceof NPC) {
                NPC npc = (NPC) characters.get(index);
                return npc.getSuspicionRoleString();
            }
        }

        return null;
    }

    /**
     * Getter for the alive state of a specified character
     * @param index int representing the id of the Character
     * @return boolean if alive or not from character
     */
    public boolean getAliveFromCharacter(int index){

        if (index > -1 && index < characters.size()) {
            return characters.get(index).isAlive();
        }

        return false;
    }

    /**
     * Getter for the number of crew members
     * @return int with the number of crew members
     */
    public int getNumTripulants(){ return  numTripulants;}

    /**
     * Getter for the number  of impostors in the game
     * @return int with the number of impostors
     */
    public int getNumImpostors(){ return numImpostors; }

    /**
     * Method that finds the index of the player on the character of the game
     * @return index of the player
     */
    public int getIndexPlayer(){
        for (int i = 0; i < characters.size(); i++) {

            if(getRoleCharacter(i).equals("Player")){
                return i;
            }
        }

        return -1;
    }

    /**
     * Getter for the player userName
     * @return String with the player's username
     */
    public String getPlayerUserName(){
        Player player =  (Player) characters.get(getIndexPlayer());

        return player.getName();
    }

    /**
     * Getter of a Character given a specific Name
     * @param name String name of the character (The name is the same as its color)
     * @return Character
     */
    public Character getCharacter(String name){
        for (Character character: characters) {

            if (name.equals(character.getColor().colorToString())) return character;
        }

        return null;
    }

    /**
     * Checks if a specified room has the LOG Action
     * @param position position of the room
     * @return boolean if the room can check the logs or not
     */
    public boolean roomIsActionLogs(int[] position) {
        return map.roomIsActionLogs(position);
    }

    /**
     * Checks if a specified room has the CLASSIFICATION Action
     * @param position position of the room
     * @return boolean if the room can send the suspicions from the player
     */
    public boolean roomIsActionClassification(int[] position) {
        return map.roomIsActionClassification(position);
    }

    /**
     * Getter for the image path of a specified room in the current Game map
     * @param roomIndex int
     * @return String with the room path of a concrete map
     */
    public String getGameMapRoomPath(int roomIndex) {
        return map.getRoomPath(roomIndex);
    }
}


