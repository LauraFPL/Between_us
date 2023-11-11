package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.PlayersController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * AccountView class that extends JPanel
 * Shows the user's account
 */
public class AccountView extends JPanel{
    public static final String ACCOUNT_BACK = "a_back_command";
    public static final String ACCOUNT_CONFIG = "a_config_command";
    public static final String ACCOUNT_GAMES = "a_games_command";
    public static final String ACCOUNT_STATS = "a_stats_command";

    private final JPanel principal;
    private JButton backButton;
    private JButton configButton;
    private JButton gamesButton;
    private JButton statsButton;

    /**
     * Constructor of the class
     */
    public AccountView () {
        this.setLayout(new BorderLayout());

        principal = new JImagePanel("files/assets/StartAndAccount/background_principal.jpg");
        principal.setLayout(new BorderLayout());
        drawAll();

        this.add(principal, BorderLayout.CENTER);
    }

    /**
     * Method that makes the tab visible according to the parameter
     * @param visible boolean that tells to show or not show the tab
     */
    public void showWindow (boolean visible) { setVisible(visible);}

    /**
     *  It simply draws all what is goint to be in the tab
     */
    public void drawAll () {
        JPanel center = drawCenter();
        JPanel west = drawWest();
        JPanel east = drawEast();

        //WEST----------------------------------------------------------------------------------------------------------
        principal.add(west,BorderLayout.WEST);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);

        //EAST----------------------------------------------------------------------------------------------------------
        principal.add(east,BorderLayout.EAST);
    }

    /**
     * It draws all what is inside the west of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawWest () {
        JPanel west = new JPanel();
        west.setOpaque(false);

        try {
            ImageIcon backImg = new ImageIcon(ImageIO.read(new File("files/Assets/Back.png")).getScaledInstance(100,100,0) );
            this.backButton = new JButton(backImg);
            this.backButton.setPreferredSize(new Dimension(100,100));
            this.backButton.setBackground(Color.WHITE);
            this.backButton.setOpaque(false);
            this.backButton.setBorderPainted(false);

            west.add(this.backButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        west.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));

        return west;
    }

    /**
     * It draws all what is inside the west of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawEast () {
        JPanel east = new JPanel();
        east.setOpaque(false);

        try {
            ImageIcon configImg = new ImageIcon(ImageIO.read(new File("files/Assets/StartAndAccount/config.png")).getScaledInstance(110,110,0) );
            this.configButton = new JButton(configImg);
            this.configButton.setPreferredSize(new Dimension(110,110));
            this.configButton.setBackground(Color.WHITE);
            this.configButton.setOpaque(false);
            this.configButton.setBorderPainted(false);

            east.add(this.configButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        east.setBorder(BorderFactory.createEmptyBorder(30,0,0,30));

        return east;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        JPanel center = new JPanel(new BorderLayout());
        JPanel auxCenter = new JPanel(new GridBagLayout());
        auxCenter.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(360,0,0,0));

        //Botó Games____________________________________________________________________________________________________
        this.gamesButton = new JButton("Games");
        this.gamesButton.setBackground(new Color(4,205,0));
        this.gamesButton.setForeground(Color.WHITE);
        this.gamesButton.setFont(new Font("Aclonica", Font.BOLD, 50));
        this.gamesButton.setPreferredSize(new Dimension(450,100));
        this.gamesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        auxCenter.add(gamesButton, constraints);

        JPanel auxVoid = new JPanel();
        auxVoid.setSize(new Dimension(10,60));
        auxVoid.setOpaque(false);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        auxCenter.add(auxVoid, constraints);

        this.statsButton = new JButton("Stats");
        this.statsButton.setBackground(new Color(4,205,0));
        this.statsButton.setForeground(Color.WHITE);
        this.statsButton.setFont(new Font("Aclonica", Font.BOLD, 50));
        this.statsButton.setPreferredSize(new Dimension(450,100));
        this.statsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        auxCenter.add(statsButton, constraints);

        center.add(auxCenter,BorderLayout.CENTER);

        return center;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller contains all the action listeners
     */
    public void registerController (PlayersController controller) {
        this.backButton.setActionCommand(ACCOUNT_BACK);
        this.configButton.setActionCommand(ACCOUNT_CONFIG);
        this.gamesButton.setActionCommand(ACCOUNT_GAMES);
        this.statsButton.setActionCommand(ACCOUNT_STATS);

        this.backButton.addActionListener(controller);
        this.configButton.addActionListener(controller);
        this.gamesButton.addActionListener(controller);
        this.statsButton.addActionListener(controller);
    }
}
