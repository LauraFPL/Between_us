package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.PlayersController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * SettingsView class that extends JPanel
 * Will be shown when the user clicks on the settings icon up-right
 */
public class SettingsView extends JPanel {
    public static final String SETTINGS_DELETE_BUTTON_COMMAND = "s_DELETE_command";
    public static final String SETTINGS_BACK_BUTTON_COMMAND = "s_back_command";

    //ATRIBUTS REGISTER
    private final JPanel principal;

    private JButton backButton;
    private JButton deleteButton;

    /**
     * Constructor of the class
     **/
    public SettingsView () {
        this.setLayout(new BorderLayout());

        principal = new JImagePanel("files/Assets/background.jpg");
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

        //SETTINGS______________________________________________________________________________________________________
        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("Aclonica", Font.BOLD, 80));
        settingsTitle.setForeground(Color.WHITE);
        settingsTitle.setOpaque(false);
        settingsTitle.setBorder(BorderFactory.createEmptyBorder(0,630,0,0));

        north.add(settingsTitle, BorderLayout.CENTER);

        north.setBorder(BorderFactory.createEmptyBorder(60,40,0,30));

        return north;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        JPanel auxRectangle = new JPanel(new BorderLayout());
        auxRectangle.setBorder(BorderFactory.createEmptyBorder(100,500,200,500));

        auxRectangle.setOpaque(false);

        JImagePanel rectangle = new JImagePanel("files/Assets/rectangleGran.png");
        rectangle.setOpaque(false);
        rectangle.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        rectangle.setPreferredSize(new Dimension(1000,650));

        rectangle.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        auxRectangle.add(rectangle, BorderLayout.CENTER);

        //BOTÓ__________________________________________________________________________________________________________
        this.deleteButton = new JButton("Delete  Account");
        this.deleteButton.setBackground(new Color(4,205,0));
        this.deleteButton.setForeground(Color.WHITE);
        this.deleteButton.setFont(new Font("Aclonica", Font.BOLD, 55));
        this.deleteButton.setPreferredSize(new Dimension(700,100));

        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        rectangle.add(this.deleteButton, constraints);

        return auxRectangle;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type PlayersController
     */
    public void registerController (PlayersController controller) {
        this.backButton.setActionCommand(SETTINGS_BACK_BUTTON_COMMAND);
        this.deleteButton.setActionCommand(SETTINGS_DELETE_BUTTON_COMMAND);

        this.backButton.addActionListener(controller);
        this.deleteButton.addActionListener(controller);
    }
}
