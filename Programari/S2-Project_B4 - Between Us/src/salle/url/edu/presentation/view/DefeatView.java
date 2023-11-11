package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.CurrentGameController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * DefeatView class that extends JFrame
 * Shows an image when the user loses a game
 */
public class DefeatView extends JPanel{
    public static final String DEFEAT_BACK_BUTTON_COMMAND = "defeat_back_command";

    //ATRIBUTS LOGIN
    private JButton backButton;
    private JPanel principal;

    private Clip clipLooseSound;

    /**
     * Constructor of the class
     */
    public DefeatView () {
        this.setLayout(new BorderLayout());

        principal = new JImagePanel("files/Assets/GameFinish/loseView.png");
        principal.setLayout(new BorderLayout());
        drawAll();

        this.add(principal,BorderLayout.CENTER);
        this.setSize(1920,1080);

        String soundName = "files/assets/Audio/lose.wav";
        AudioInputStream audioInputStream = null;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clipLooseSound = AudioSystem.getClip();
            clipLooseSound.open(audioInputStream);
            clipLooseSound.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that makes the tab visible according to the parameter
     * @param visible boolean that tells to show or not show the tab
     */
    public void showWindow (boolean visible) {
        setVisible(visible);
    }

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

        ImageIcon backImg = new ImageIcon(new ImageIcon("files/Assets/Back.png").getImage().getScaledInstance(80,80,0));
        this.backButton = new JButton(backImg);
        this.backButton.setPreferredSize(new Dimension(80,80));
        this.backButton.setBackground(Color.WHITE);
        this.backButton.setOpaque(false);
        this.backButton.setBorderPainted(false);

        west.add(this.backButton);

        west.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));

        return west;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBorder(BorderFactory.createEmptyBorder(140,0,0,110));
        center.setOpaque(false);

        // We suppose you have already set your JFrame
        Icon imgIcon = null;

        imgIcon = new ImageIcon(new ImageIcon("files/Assets/GameFinish/patricio.gif").getImage().getScaledInstance(1000,600,0));
        JLabel gif = new JLabel(imgIcon);
        center.add(gif);

        return center;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller CurrentGameController that manage the listeners
     */
    public void registerController (CurrentGameController controller) {
        this.backButton.setActionCommand(DEFEAT_BACK_BUTTON_COMMAND);
        this.backButton.addActionListener(controller);
    }

    /**
     * Used to stop the sound
     */
    public void stopSound () {
        clipLooseSound.stop();
    }
}
