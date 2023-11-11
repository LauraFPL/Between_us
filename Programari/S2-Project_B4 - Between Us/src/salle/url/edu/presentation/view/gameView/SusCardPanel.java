package salle.url.edu.presentation.view.gameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * SusCardPanel class that extends JPanel
 * Shows the characters colors that can be changed to SUS, NOT SUS and UNKNOWN
 */
public class SusCardPanel extends JPanel {
    private JPanel auxContainer;
    private Color color;
    private JLabel name;
    private JButton up;
    private JButton down;

    /**
     * Constructor of the class
     * @param color of type Color contains the color of the character that can be changed of section
     * @param name of type string that contains the name of the character
     * @throws IOException in case anything goes wrong
     */
    public SusCardPanel (Color color, String name) throws IOException {
        auxContainer = new JPanel();
        auxContainer.setLayout(new BorderLayout());
        auxContainer.setBackground(color);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.color = color;
        this.name = new JLabel(name);
        this.name.setFont(GameView.aclonica32);
        this.name.setVerticalAlignment(JLabel.CENTER);
        this.name.setHorizontalAlignment(JLabel.CENTER);

        String path =  "files/assets/UI_partida/arrowUp_suspicion.png";
        ImageIcon upArrowIcon = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(50,50,100));
        up = new JButton(upArrowIcon);
        up.setOpaque(false);
        up.setBackground(Color.YELLOW);
        up.setBorderPainted(false);

        path = "files/assets/UI_partida/arrowDown_suspicion.png";
        ImageIcon downArrowIcon = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(50,50,100));
        down = new JButton(downArrowIcon);
        down.setOpaque(false);
        down.setBackground(Color.YELLOW);
        down.setBorderPainted(false);

        setMaximumSize(new Dimension(700,60));
        setPreferredSize(new Dimension(700,60));
        setMinimumSize(new Dimension(700,60));

        auxContainer.add(up, BorderLayout.WEST);
        auxContainer.add(this.name, BorderLayout.CENTER);
        auxContainer.add(down, BorderLayout.EAST);
        add(auxContainer, CENTER_ALIGNMENT);
    }

    /**
     * Gets the name of the character to be moved
     * @return a string with the name of the character
     */
    @Override
    public String getName () {
        return name.getText();
    }

    /**
     * Method that registers the action listeners of the view and sends them to the controller
     * @param controller of type ActionListener that contains all the listeners
     */
    public void registerController (ActionListener controller) {
        this.down.setActionCommand(GameView.GAME_OBJECTIVE_DOWN_BUTTON_COMMAND);
        this.down.addActionListener(controller);
        this.up.setActionCommand(GameView.GAME_OBJECTIVE_UP_BUTTON_COMMAND);
        this.up.addActionListener(controller);
    }
}
