package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.PlayersController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * StartView class that extends JPanel
 * It is the first view to be shown when the program starts the execution
 */
public class StartView extends JPanel {
    public static final String START_LEAVE_BUTTON_COMMAND = "s_leave_command";
    public static final String START_PLAY_BUTTON_COMMAND = "s_play_command";

    private final JPanel principal;
    private JButton leaveButton;
    private JButton startTiangButton;

    /**
     * Constructor of the class
     */
    public StartView () {
        this.setLayout(new BorderLayout());

        principal = new JImagePanel("files/Assets/StartAndAccount/background_principal.jpg");
        principal.setLayout(new BorderLayout());
        principal.setSize(new Dimension(WIDTH,HEIGHT));
        drawAll();

        this.add(principal,BorderLayout.CENTER);
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

        //WEST----------------------------------------------------------------------------------------------------------
        principal.add(west,BorderLayout.WEST);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);
    }

    /**
     * It draws all what is inside the west of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawWest () {
        JPanel west = new JPanel();
        west.setOpaque(false);

        try {
            ImageIcon leaveImg = new ImageIcon(ImageIO.read(new File("files/Assets/StartAndAccount/leave.png")).getScaledInstance(100,100,0) );
            this.leaveButton = new JButton(leaveImg);
            this.leaveButton.setPreferredSize(new Dimension(100,100));
            this.leaveButton.setBackground(Color.WHITE);
            this.leaveButton.setOpaque(false);
            this.leaveButton.setBorderPainted(false);

            west.add(this.leaveButton);

        } catch (IOException e) {
            e.printStackTrace();
        }

        west.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));

        return west;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(670,770,210,850));

        center.setOpaque(false);

        try {
            ImageIcon triangImg = new ImageIcon(ImageIO.read(new File("files/Assets/StartAndAccount/triangPlay.png")).getScaledInstance(200,200,0) );
            this.startTiangButton = new JButton(triangImg);
            this.startTiangButton.setPreferredSize(new Dimension(800,800));
            this.startTiangButton.setBackground(Color.WHITE);
            this.startTiangButton.setOpaque(false);
            this.startTiangButton.setBorderPainted(false);

            center.add(this.startTiangButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return center;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type PlayersController
     */
    public void registerController(PlayersController controller){
        this.leaveButton.setActionCommand(START_LEAVE_BUTTON_COMMAND);
        this.startTiangButton.setActionCommand(START_PLAY_BUTTON_COMMAND);

        this.leaveButton.addActionListener(controller);
        this.startTiangButton.addActionListener(controller);
    }
}
