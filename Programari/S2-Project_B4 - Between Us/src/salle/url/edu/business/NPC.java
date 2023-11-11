package salle.url.edu.business;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

/**
 * NPC, extends of character
 * Implements Runnable
 * Is responsible for the movement and actions of any Non Player Character
 */
public class NPC extends Character implements Runnable {
    //ATRIBUTS----------------------------------------------------------------------------------------------------------
    protected NPCSuspition suspitionRol;
    protected int lastMovement = 0;
    protected boolean stop;

    //CONSTRUCTORS------------------------------------------------------------------------------------------------------
    /**
     * Constructor that is called when a new game is created and creates a new impostor
     * @param color is the color of this NPC
     * @param alive is a boolean that returns if the NPC is alive (true) or dead (false)
     * @param position integer of two positions that has the x and y position of the NPC
     * @param suspitionRol tells if this NPC is sus, unknown or not sus
     */
    public NPC(CharacterColors color, boolean alive, int[] position, NPCSuspition suspitionRol, Game game) {
        super(color, alive, position, game);
        this.suspitionRol = suspitionRol;
        this.stop = false;
    }

    /**
     * Constructor that is called when the user resumes an already created game
     * @param color is the color of this NPC
     * @param position integer of two positions that has the x and y position of the NPC
     */
    public NPC(CharacterColors color, int[] position, Game game) {
        super(color, true, position, game);
        this.suspitionRol = NPCSuspition.UNKNOWN;
        this.stop = false;
    }

    //METHODS-----------------------------------------------------------------------------------------------------------
    /**
     * Method run, it is implemented by Runnable. Threads work because of it.
     */
    @Override
    public void run() {
        int canviSala;

        Random rand1 = new Random();

        //System.out.println("\u001B[35m" + "NPC > " + this + " : " + this.getColorString() + " THREAD STARTED!, stop = " + stop + "\u001B[0m");


        while (!stop) {

            // En un interval entre 5 i 15 segons un NPC decideix si canviar de sala o no
            int intervalGeneral = ThreadLocalRandom.current().nextInt(5000, 15000);
            //System.out.println("NPC > " + this + " : " + this.getColorString() + " Interval = " + intervalGeneral);

            canviSala = 1 + rand1.nextInt(100);

            try {
                Thread.sleep(intervalGeneral);

                if (canviSala < 56) {
                    //System.out.println("NPC > " + this + " : " + this.getColorString() + " canvia de sala");
                    npcMoves();
                } /*else {
                    System.out.println("NPC > " + this + " : " + this.getColorString() + " no canvia de sala");
                }*/
            } catch (InterruptedException e) {
                e.printStackTrace();
                //System.out.println("NPC > " + this + " : " + this.getColorString() + " THREAD EXCEPTION");

            }

        }

        //System.out.println("\u001B[35m" + "NPC > " + this + " : " + this.getColorString() + " THREAD STOPPED!, stop = " + stop + "\u001B[0m");


    }

    /**
     * Method that moves the NPC when it is called in the "run" method
     */
    public synchronized void npcMoves () {
        boolean upPossible;
        boolean downPossible;
        boolean rightPossible;
        boolean leftPossible;
        boolean moved;
        int move;
        int probability;
        int maxIterations = 0;

        Random rand2 = new Random();
        Random rand3 = new Random();

        moved = false;
        if (alive) {
            do {
                move = rand2.nextInt(4);
                probability = rand3.nextInt(100);

                if (lastMovement == 0) {
                    if (move == CurrentGameManager.UP) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " UP");
                        upPossible = game.getMap().canMove(position, CurrentGameManager.UP);

                        if (upPossible) {
                            moveCharacter(CurrentGameManager.UP);

                            lastMovement = CurrentGameManager.UP;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT UP");
                        }*/
                    }

                    if (move == CurrentGameManager.DOWN && probability < 30) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " DOWN 15%");
                        downPossible = game.getMap().canMove(position, CurrentGameManager.DOWN);

                        if (downPossible) {
                            moveCharacter(CurrentGameManager.DOWN);

                            lastMovement = CurrentGameManager.DOWN;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT DOWN");
                        }*/
                    }

                    if (move == CurrentGameManager.LEFT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " LEFT");
                        leftPossible = game.getMap().canMove(position, CurrentGameManager.LEFT);

                        if (leftPossible) {
                            moveCharacter(CurrentGameManager.LEFT);

                            lastMovement = CurrentGameManager.LEFT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT LEFT");

                        }*/
                    }

                    if (move == CurrentGameManager.RIGHT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " RIGHT");
                        rightPossible = game.getMap().canMove(position, CurrentGameManager.RIGHT);

                        if (rightPossible) {
                            moveCharacter(CurrentGameManager.RIGHT);

                            lastMovement = CurrentGameManager.RIGHT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT RIGHT");
                        }*/
                    }
                }

                if (lastMovement == 1) {
                    if (move == CurrentGameManager.UP && probability < 30) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " UP 15%");
                        upPossible = game.getMap().canMove(position, CurrentGameManager.UP);

                        if (upPossible) {
                            moveCharacter(CurrentGameManager.UP);

                            lastMovement = CurrentGameManager.UP;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT UP");
                        }*/
                    }

                    if (move == CurrentGameManager.DOWN) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " DOWN");
                        downPossible = game.getMap().canMove(position, CurrentGameManager.DOWN);

                        if (downPossible) {
                            moveCharacter(CurrentGameManager.DOWN);

                            lastMovement = CurrentGameManager.DOWN;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT DOWN");
                        }*/
                    }

                    if (move == CurrentGameManager.LEFT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + "LEFT");
                        leftPossible = game.getMap().canMove(position, CurrentGameManager.LEFT);

                        if (leftPossible) {
                            moveCharacter(CurrentGameManager.LEFT);

                            lastMovement = CurrentGameManager.LEFT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT LEFT");
                        }*/
                    }

                    if (move == CurrentGameManager.RIGHT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " RIGHT");
                        rightPossible = game.getMap().canMove(position, CurrentGameManager.RIGHT);

                        if (rightPossible) {
                            moveCharacter(CurrentGameManager.RIGHT);

                            lastMovement = CurrentGameManager.RIGHT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT RIGHT");
                        }*/
                    }
                }

                if (lastMovement == 2) {
                    if (move == CurrentGameManager.UP) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " UP");
                        upPossible = game.getMap().canMove(position, CurrentGameManager.UP);

                        if (upPossible) {
                            moveCharacter(CurrentGameManager.UP);

                            lastMovement = CurrentGameManager.UP;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT UP");
                        }*/
                    }

                    if (move == CurrentGameManager.DOWN) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + "  DOWN");
                        downPossible = game.getMap().canMove(position, CurrentGameManager.DOWN);

                        if (downPossible) {
                            moveCharacter(CurrentGameManager.DOWN);

                            lastMovement = CurrentGameManager.DOWN;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT DOWN");
                        }*/
                    }

                    if (move == CurrentGameManager.LEFT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " LEFT");
                        leftPossible = game.getMap().canMove(position, CurrentGameManager.LEFT);

                        if (leftPossible) {
                            moveCharacter(CurrentGameManager.LEFT);

                            lastMovement = CurrentGameManager.LEFT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT LEFT");
                        }*/
                    }

                    if (move == CurrentGameManager.RIGHT && probability < 30) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " RIGHT 15%");
                        rightPossible = game.getMap().canMove(position, CurrentGameManager.RIGHT);

                        if (rightPossible) {
                            moveCharacter(CurrentGameManager.RIGHT);

                            lastMovement = CurrentGameManager.RIGHT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT RIGHT");
                        }*/
                    }
                }

                if (lastMovement == 3) {
                    if (move == CurrentGameManager.UP) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " UP");
                        upPossible = game.getMap().canMove(position, CurrentGameManager.UP);

                        if (upPossible) {
                            moveCharacter(CurrentGameManager.UP);

                            lastMovement = CurrentGameManager.UP;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT UP");
                        }*/
                    }

                    if (move == CurrentGameManager.DOWN) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " DOWN");
                        downPossible = game.getMap().canMove(position, CurrentGameManager.DOWN);

                        if (downPossible) {
                            moveCharacter(CurrentGameManager.DOWN);

                            lastMovement = CurrentGameManager.DOWN;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT DOWN");
                        }*/
                    }

                    if (move == CurrentGameManager.LEFT && probability < 30) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " LEFT 15%");
                        leftPossible = game.getMap().canMove(position, CurrentGameManager.LEFT);

                        if (leftPossible) {
                            moveCharacter(CurrentGameManager.LEFT);

                            lastMovement = CurrentGameManager.LEFT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT LEFT");
                        }*/
                    }

                    if (move == CurrentGameManager.RIGHT) {
                        //System.out.println("NPC > " + this + " : " + this.getColorString() + " RIGHT");
                        rightPossible = game.getMap().canMove(position, CurrentGameManager.RIGHT);

                        if (rightPossible) {
                            moveCharacter(CurrentGameManager.RIGHT);

                            lastMovement = CurrentGameManager.RIGHT;

                            game.addGameLogs(color.colorToString(), game.getMap().getNameRoomPosition(position), game.getInstant());
                            moved = true;
                        } /*else {
                            System.out.println("NPC > " + this + " : " + this.getColorString() + " NOT RIGHT");
                        }*/
                    }
                }

                maxIterations++;
            } while (!moved && maxIterations < 5);

        }
    }

    /**
     * Metohd that checks if the suspicions are good or not
     * @return a boolean
     */
    public boolean checkRoleSusRole(){
        if (this instanceof Impostor && suspitionRol == NPCSuspition.SUS) {
            return true;
        }else if (suspitionRol == NPCSuspition.NOT_SUS){
            return true;
        }

        return false;
    }

    /**
     * Method that moves the suspicion roles of the NPCs
     * @param direction is the direction in which the user wants to move the suspicion role (UP or DOWN)
     */
    public void moveSuspitionRol (int direction){
        int number = NPCSuspition.valueOf(suspitionRol.name()).ordinal() + direction;
        if (number > 2) number = 0;
        if (number < 0) number = 2;

        suspitionRol = NPCSuspition.values()[number];
        System.out.println(this.color.colorToString() + " " + suspitionRol.toString());
    }

    /**
     * Method that changes the boolean stop to true when the threads must be finished
     */
    public void stop() {
        this.stop = true;
    }

    /**
     * Method that returns the suspicion role of an NPC in type string
     * @return string with the suspicion role value
     */
    public String getSuspicionRoleString(){
        return String.valueOf(this.suspitionRol);
    }

    //GETTERS and SETTERS-----------------------------------------------------------------------------------------------
    /**
     * Getter of the suspicion role of an NPC
     * @return variable of type NPCSuspition
     */
    public NPCSuspition getSuspitionRol() {
        return suspitionRol;
    }
}
