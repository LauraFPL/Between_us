package salle.url.edu.business;

/**
 * Runnable TimeGame used for counting the time that has passed since a specific event
 */
public class TimeGame implements Runnable{
    private int time;
    private boolean stop;

    /**
     * Constructor by default
     */
    public TimeGame(){
        time = 0;
        stop = false;
    }

    /**
     * Constructor
     * @param time int, time in seconds
     */
    public TimeGame(int time){
        this.time = time;
        stop = false;
    }

    //METHODS----------------------------------------------------------------------------------------------------------
    /**
     * implementation of the run method of the Runnable interface
     * This run() method loops and increment the attribute time each second
     * while the attribute stop is not set to true
     */
    public void run(){

        do {
            try {
                Thread.sleep(1000);
                time++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(!stop);

    }

    //GETTERS----------------------------------------------------------------------------------------------------------
    /**
     * Getter for the time value
     * @return int
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the attribute stop to true so that the run methods stops and ends the counting
     */
    public void stop(){ stop = true;}
}
