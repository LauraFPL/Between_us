package salle.url.edu.presentation.view;
import salle.url.edu.presentation.controller.GamesController;
import salle.url.edu.presentation.controller.PlayersController;

import javax.swing.*;
import java.awt.*;

/**
 * ConfirmationView class that extends JPanel
 */
public class ConfirmationView extends JPanel {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    //GAMES
    public static final String YES_BUTTON_GAMES = "Yes button game";
    public static final String NO_BUTTON_GAMES = "No button game";

    //PLAYERS
    public static final String YES_BUTTON_PLAYERS = "Yes button players";
    public static final String NO_BUTTON_PLAYERS = "No button players";

    //ATRIBUTS CONFIRMATION VIEW
    private GridLayout WarningView;
    private final GridBagConstraints gbc;
    private final GridBagConstraints gbc2;
    private final JPanel rect;
    private final JLabel questionText;
    private final JButton yesButton;
    private final JButton noButton;
    private String gameName;

    /**
     * Constructor of the class
     */
    public ConfirmationView () {
        setLayout(new BorderLayout());
        JImagePanel backgroundImage = new JImagePanel("files/Assets/background.jpg");
        JImagePanel rectangleBackground = new JImagePanel("files/Assets/WarningAndConfirmation/ConfirmationRectangle.png");

        add(backgroundImage,BorderLayout.CENTER);

        backgroundImage.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc2 = new GridBagConstraints();
        backgroundImage.setOpaque(false);

        rect = new JPanel();
        rectangleBackground.setLayout(new GridBagLayout());
        rectangleBackground.setOpaque(false);
        rectangleBackground.setPreferredSize(new Dimension(600,350));
        rectangleBackground.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 1;
        backgroundImage.add(rectangleBackground,gbc2);

        //QUESTION TEXT_________________________________________________________________________________________________
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        questionText = new JLabel("Are you sure you want to delete? ");
        questionText.setFont(new Font("Aclonica", Font.BOLD, 20));
        questionText.setBackground(Color.BLACK);
        gbc.insets = new Insets(30,0,10,0);
        rectangleBackground.add(questionText, gbc);

        //BUTTON YES____________________________________________________________________________________________________
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        yesButton = new JButton("Yes");
        yesButton.setFont(new Font("Aclonica", Font.BOLD, 20));
        yesButton.setPreferredSize(new Dimension(140, 50));
        rectangleBackground.add(yesButton, gbc);

        //BUTTON NO_____________________________________________________________________________________________________
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        noButton = new JButton("No");
        noButton.setFont(new Font("Aclonica", Font.BOLD, 20));
        noButton.setPreferredSize(new Dimension(140, 50));
        rectangleBackground.add(noButton, gbc);

        //SIZE__________________________________________________________________________________________________________
        setSize(WIDTH, HEIGHT);
        setPreferredSize(getSize());
        setVisible(true);
    }

    /**
     * Method that asks the user if he wants to delete a game
     */
    public void updateConfirmationGame () {
        questionText.setText("<html> Are you sure you want to delete '"+gameName+"' ? <html>");
    }

    /**
     * Method that asks the user if he wants to delete an account
     */
    public void updateConfirmationPlayer (String userName) {
        questionText.setText("<html> Are you sure you want to delete '"+userName+"' <html>? ");
    }

    /**
     * Method that makes the tab visible according to the parameter
     * @param visible boolean that tells to show or not show the tab
     */
    public void showWindow (boolean visible) { setVisible(visible);}

    /**
     * Method that connects the listeners with the controller
     * @param controller of type GamesController that contains all action commands of the game
     */
    public void registerControllerGames (GamesController controller) {
        this.yesButton.setActionCommand(YES_BUTTON_GAMES);
        this.yesButton.addActionListener(controller);

        this.noButton.setActionCommand(NO_BUTTON_GAMES);
        this.noButton.addActionListener(controller);
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type PlayersController that contains all action commands of the player
     */
    public void registerControllerPlayers (PlayersController controller) {
        this.yesButton.setActionCommand(YES_BUTTON_PLAYERS);
        this.yesButton.addActionListener(controller);

        this.noButton.setActionCommand(NO_BUTTON_PLAYERS);
        this.noButton.addActionListener(controller);
    }

    /**
     * Gets the name of the game
     * @return a string with the game name
     */
    public String getGameName () {
        return gameName;
    }

    /**
     * Sets the game name
     * @param gameName of type string that contains the new settled game
     */
    public void setGameName (String gameName) {
        this.gameName = gameName;
    }
}