package salle.url.edu.presentation.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * MatserView class that extends JFrame
 * This class is the controller of all views
 * Contains all the others Views using a CardLayout
 */
public class MasterView extends JFrame {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static final String START_VIEW = "startView";
    public static final String LOGIN_VIEW = "loginView";
    public static final String REGISTER_VIEW = "registerView";
    public static final String ACCOUNT_VIEW = "accountView";
    public static final String SETTINGS_VIEW = "settingsView";
    public static final String GAME_CREATION_VIEW = "gameCreationView";
    public static final String GAMES_MANAGER_VIEW = "gamesManagerView";
    public static final String GAME_VIEW = "gameView";
    public static final String LOGS_VIEW = "logsView";
    public static final String PLAYER_EVOLUTION_VIEW = "playerEvolutionView";
    public static final String DUPLICATION_CREATION_VIEW = "duplicationView";
    public static final String CONFIRMATION_VIEW_PLAYERS = "confirmatinoView_players";
    public static final String CONFIRMATION_VIEW_GAMES = "confirmatinoView_games";
    public static final String VICTORY_VIEW = "victoryView";
    public static final String DEFEAT_VIEW = "defeatView";

    private CardLayout cardLayout;

    private String currentView;

    /**
     * Constructor of the class
     */
    public MasterView () {
        setSize(WIDTH,HEIGHT);
        setTitle("Between Us");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            setIconImage( (new ImageIcon(ImageIO.read(new File("files/assets/npc/NPC_RED.png")).getScaledInstance(400,400,100))).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }

    /**
     * Method that makes the tab visible according to the parameter
     * @param visible boolean that tells to show or not show the tab
     */
    public void showWindow (boolean visible) {setVisible(visible);}

    /**
     * Method that adds a new view to the master view
     * @param view of type JPanel contains the view that must be added
     * @param constantView of type string contains the constant assigned to that view
     */
    public void addView (JPanel view, String constantView) {
        this.add(constantView, view);
    }



    /**
     * Method that change to the view we want. We know each view thanks to the constants of this class
     * @param constantView The constant of this class that indicates one of the views
     */
    public void changeView (String constantView) {
        cardLayout.show(this.getContentPane(),constantView);
        currentView = constantView;
    }

    /**
     * Getter
     * @return returns the current view
     */
    public String getCurrentView () {return currentView;}
}
