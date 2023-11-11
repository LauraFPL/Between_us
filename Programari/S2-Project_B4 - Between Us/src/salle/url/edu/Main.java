package salle.url.edu;

import salle.url.edu.presentation.controller.PlayersController;
import salle.url.edu.presentation.view.*;

/**
 * Main class of the whole project that initializes first views
 * and shows the  first one.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Between Us... ");
        //1. Create the master View
        MasterView masterView = new MasterView();

        //2. Start with the main controller
        new PlayersController(masterView);
    }
}
