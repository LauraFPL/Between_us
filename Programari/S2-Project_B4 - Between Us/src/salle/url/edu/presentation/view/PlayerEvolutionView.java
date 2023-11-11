package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.PlayersController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

/**
 * PlayerEvolutionGame class that extends JPanel
 * Will be shown if the user wants to see their evolution
 */
public class PlayerEvolutionView extends JPanel {
    public static final String PLAYER_EVOLUTION_BACK_BUTTON_COMMAND = "playerEvolution_back_command";

    //ATRIBUTS PLAYER EVOLUTION
    private final JPanel principal;
    private JButton backButton;

    private JPanel center;
    private GraphDrawer graph;

    /**
     * Constructor of the class
     */
    public PlayerEvolutionView () {
        this.setLayout(new BorderLayout());
        principal = new JImagePanel("files/Assets/background.jpg");
        principal.setLayout(new BorderLayout());
        this.add(principal, BorderLayout.CENTER);
        drawAll();
    }

    /**
     * Method that will draw the players evolution graphic
     * @param numWins is an ArrayList of booleans that has the wins of the user
     */
    public void updateGraph (ArrayList<Boolean> numWins) {
        if (graph != null ){center.remove(graph);}

        graph = new GraphDrawer(numWins);
        center.add(graph);
    }

    /**
     * Method that sets the variable visible to true or false if the window must be shown or not
     * @param visible is a boolean that contains true (visible window) or false (not visible window)
     */
    public void showWindow (boolean visible) {setVisible(visible);}

    /**
     *  It simply draws all what is goint to be in the tab
     */
    public void drawAll () {
        JPanel center = drawCenter();
        JPanel north = drawNorth();

        //NORTH---------------------------------------------------------------------------------------------------------
        principal.add(north,BorderLayout.NORTH);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);
    }

    /**
     * It draws all what is inside the nordth of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawNorth () {
        JPanel north = new JPanel();
        north.setOpaque(false);

        north.setLayout(new BorderLayout());

        //BACK__________________________________________________________________________________________________________
        try {
            ImageIcon backImg = new ImageIcon(ImageIO.read(new File("files/Assets/Back.png")).getScaledInstance(100,100,0) );
            this.backButton = new JButton(backImg);
            this.backButton.setPreferredSize(new Dimension(100,100));
            this.backButton.setBackground(Color.WHITE);
            this.backButton.setOpaque(false);
            this.backButton.setBorderPainted(false);

            north.add(this.backButton, BorderLayout.WEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //PLAYER EVOLUTION______________________________________________________________________________________________
        JLabel evolutionTitle = new JLabel("Player Evolution");
        evolutionTitle.setFont(new Font("Aclonica", Font.BOLD, 80));
        evolutionTitle.setForeground(Color.WHITE);
        evolutionTitle.setOpaque(false);
        evolutionTitle.setBorder(BorderFactory.createEmptyBorder(0,450,0,0));

        north.add(evolutionTitle, BorderLayout.CENTER);

        north.setBorder(BorderFactory.createEmptyBorder(60,40,0,30));

        return north;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(100,500,200,500));

        center.setOpaque(false);

        JImagePanel rectangle = new JImagePanel("files/Assets/rectangleGran.png");
        rectangle.setOpaque(false);
        rectangle.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        rectangle.setPreferredSize(new Dimension(1200,1050));

        rectangle.setLayout(new GridBagLayout());

        center.add(rectangle, BorderLayout.CENTER);

        return center;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type PlayersControllers
     */
    public void registerController (PlayersController controller) {
        this.backButton.setActionCommand(PLAYER_EVOLUTION_BACK_BUTTON_COMMAND);
        this.backButton.addActionListener(controller);
    }

    /**
     * Method that checks for any records in the database to show in the graph
     * @param numWins of type ArrayList of booleans
     */
    public boolean nullWins (ArrayList<Boolean> numWins) {
        if (numWins.size() == 0){
            return true;
        }

        return false;
    }
}