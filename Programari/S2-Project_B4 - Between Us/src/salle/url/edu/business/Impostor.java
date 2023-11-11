package salle.url.edu.business;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Impostor class, that extends an NPC
 */
public class Impostor extends NPC {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    private int lastKill;
    private int indexCharacterNextToKiller = 0;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor that is called when a new game is created and creates a new impostor
     * @param color is the color of this impostor
     * @param alive is a boolean that returns if the impostor is alive (true) or dead (false)
     * @param position integer of two positions that has the x and y position of the impostor
     * @param assignedRol tells if this impostor is sus, unknown or not sus
     */
    public Impostor(CharacterColors color, boolean alive, int[] position, NPCSuspition assignedRol, Game game) {
        super(color, alive, position, assignedRol, game);
        this.lastKill = 0;
    }

    /**
     * Constructor that is called when the user resumes an already created game
     * @param color is the color of this impostor
     * @param position integer of two positions that has the x and y position of the impostor
     */
    public Impostor(CharacterColors color, int[] position, Game game) {
        super(color, position, game);
        this.lastKill = 0;

    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Method run, it is implemented by Runnable. Threads work because of it.
     */
    @Override
    public void run() {
        int canviSala;
        int countAlive = 0;

        // En un interval entre 6 i 8 segons un IMPOSTOR decideix si canviar de sala o no
        int intervalGeneral = ThreadLocalRandom.current().nextInt(6000, 8000);
        System.out.println(stop);
        Random rand1 = new Random();
        System.out.println("\u001B[35m" + "IMPOSTOR > " + this + " : " + this.getColorString() + " THREAD STARTED!" + "\u001B[0m");

        while (!stop) {
            canviSala = 1 + rand1.nextInt(100);

            try {
                Thread.sleep(intervalGeneral);
                if (impostorCanKill(position, game.getCharacters()) && (game.getInstant() - lastKill) > 25) {
                    impostorKill(game.getCharacters());
                    lastKill = game.getInstant();
                    npcMoves();
                } else {
                    if (canviSala < 46) {

                        Random rand3 = new Random();
                        int esVentila = 1 + rand3.nextInt(100);

                        if (game.getMap().roomHasVent(position) && esVentila < 51 && impostorAlone(position, game.getCharacters())) {
                            if (impostorAlone(useVent(position), game.getCharacters())) {
                                int[] moveVent = useVent(position);
                                this.position[0] = moveVent[0];
                                this.position[1] = moveVent[1];
                            } else if (impostorCanKill(useVent(position), game.getCharacters()) && (game.getInstant() - lastKill) > 25) {
                                int[] moveVent = useVent(position);
                                this.position[0] = moveVent[0];
                                this.position[1] = moveVent[1];
                                impostorKill(game.getCharacters());
                                lastKill = game.getInstant();
                                npcMoves();
                            } else {
                                npcMoves();
                            }
                        } else {
                            npcMoves();
                        }
                    } else {
                        System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " No canvia sala");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\u001B[35m" + "IMPOSTOR > " + this + " : " + this.getColorString() + " THREAD STOPPED!" + "\u001B[0m");


    }

    /**
     * Method that allows to control the movement of the different players
     */
    @Override
    public synchronized void npcMoves () {
        boolean upPossible;
        boolean downPossible;
        boolean rightPossible;
        boolean leftPossible;
        boolean moved;
        int move;

        int maxIterations = 0;

        System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " Canvia Sala");
        moved = false;

        Random rand2 = new Random();
        do {
            move = rand2.nextInt(4);

            if (move == CurrentGameManager.UP) {
                System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " UP");
                upPossible = game.getMap().canMove(position, CurrentGameManager.UP);

                if (upPossible) {
                    moveCharacter(CurrentGameManager.UP);

                    game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                    moved = true;
                } else {
                    System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " NO UP");

                }
            }

            if (move == CurrentGameManager.DOWN) {
                System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " DOWN 15%");
                downPossible = game.getMap().canMove(position, CurrentGameManager.DOWN);

                if (downPossible) {
                    moveCharacter(CurrentGameManager.DOWN);

                    game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                    moved = true;
                } else {
                    System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + "NO DOWN");

                }
            }

            if (move == CurrentGameManager.LEFT) {
                System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " LEFT");

                leftPossible = game.getMap().canMove(position, CurrentGameManager.LEFT);

                if (leftPossible) {
                    moveCharacter(CurrentGameManager.LEFT);

                    game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                    moved = true;
                } else {
                    System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " NOT LEFT");
                }
            }

            if (move == CurrentGameManager.RIGHT) {
                System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " RIGHT");
                rightPossible = game.getMap().canMove(position, CurrentGameManager.RIGHT);

                if (rightPossible) {
                    moveCharacter(CurrentGameManager.RIGHT);

                    game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                    moved = true;
                } else {
                    System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + "NOT RIGHT");
                }
            }
            maxIterations++;
        } while (!moved);
    }

    /**
     * Method that allows the impostor to use ventilation
     * @param position position of the characters
     * @return positionVent
     */
    public synchronized int[] useVent(int[] position) {
        boolean found = false;
        int[] positionVent = new int[2];
        Room room = game.getMap().getRooms().get(position[0] + position[1] * 4);

        for (int i = 0; i < 4 && !found; i++) {
            for (int j = 0; j < 4 && !found; j++) {
                Room roomVent = game.getMap().getRooms().get(i+j*4);

                if (((i+j*4) != position[0] + position[1] * 4) && roomVent.getHasVent()) {
                    found = true;
                    positionVent[0] = i;
                    positionVent[1] = j;
                }
            }
        }

        return positionVent;
    }

    /**
     * Method that helps us to know if the impostor is alone with another character in a room
     * @param position position of the characters
     * @param characters Arraylist with the information of the characters
     * @return alone variable that show us if the impostor is alone
     */
    public synchronized boolean impostorAlone (int[] position, ArrayList<Character> characters) {
        boolean alone = true;

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);

            if (character.getPosition()[0] == position[0] && character.getPosition()[1] == position[1] && !(character instanceof Impostor)) {
                if(characters.get(i).isAlive()) {
                    alone = false;
                }
            }
        }

        return alone;
    }

    /**
     * Method that helps us to know if the impostor can kill
     * @param position position of the characters
     * @param characters Arraylist with the information of the characters
     * @return canKill variable that show us if we can kill or no
     */
    public synchronized boolean impostorCanKill(int[] position, ArrayList<Character> characters) {
        boolean canKill = false;
        int count = 0;

        for (int i = 0; i < characters.size(); i++) {
            Character character = characters.get(i);

            if (character.getPosition()[0] == position[0] && character.getPosition()[1] == position[1] && !(character instanceof Impostor)) {
                if(characters.get(i).isAlive()) {
                    count = count + 1;

                    if (count == 1) {
                        indexCharacterNextToKiller = i;
                    }
                }
            }
        }

        if (count == 1) {
            canKill = true;
        }

        //System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " CAN KILL");
        return canKill;
    }

    /**
     * Method that "kill" the character and change the skin
     * @param characters ArrayList with all the information of all the characters
     */
    public synchronized boolean impostorKill (ArrayList<Character> characters) {
        boolean kill = false;

        Character character = characters.get(indexCharacterNextToKiller);
        if (!(character instanceof Impostor)) {
            if (!(character instanceof Player)) {
                ((NPC) character).stop();
            }
            character.setAlive(false);
        }

        kill = true;
        //System.out.println("IMPOSTOR > " + this + " : " + this.getColorString() + " HAS KILLED");
        return kill;
    }
}
