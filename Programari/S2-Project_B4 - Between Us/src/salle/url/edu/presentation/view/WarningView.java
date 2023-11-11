package salle.url.edu.presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * WarningView class that extends JDialog
 * Will be shown when an error occurs during the execution of the program
 */
public class WarningView extends JDialog {
    public static final String WARNING_ACCEPT = "Accept button";
    private final JButton acceptButton;
    private final JLabel incorrectTitle;
    private final JLabel errorTitle;
    private final JImagePanel rectangleBackground;

    private final JOptionPane jop;

    /**
     * This method will help us view the warning message when an error occurs
     */
    public WarningView () {
        rectangleBackground = new JImagePanel("files/Assets/WarningAndConfirmation/rectangleBackground.png");

        GridBagConstraints gbc = new GridBagConstraints();

        rectangleBackground.setLayout(new GridBagLayout());
        rectangleBackground.setOpaque(false);
        rectangleBackground.setPreferredSize(new Dimension(600,350));
        rectangleBackground.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //FOTO WARNING__________________________________________________________________________________________________
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 4;
        gbc.insets = new Insets(30,0,10,10);
        gbc.anchor = GridBagConstraints.LINE_START;

        JImagePanel image = new JImagePanel("files/Assets/WarningAndConfirmation/warning.png");
        image.setPreferredSize(new Dimension(250,250));
        image.setSize(new Dimension(250,250));
        image.setMaximumSize(new Dimension(250,250));
        image.setMinimumSize(new Dimension(250,250));

        image.setBorder(BorderFactory.createEmptyBorder(0,0,0,30));
        image.setOpaque(false);
        rectangleBackground.add(image, gbc);

        //TEXT ERROR____________________________________________________________________________________________________
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth  = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        errorTitle = new JLabel("ERROR");
        errorTitle.setFont(new Font("Aclonica", Font.BOLD, 30));
        errorTitle.setBackground(Color.BLACK);
        rectangleBackground.add(errorTitle, gbc);

        incorrectTitle = new JLabel("");
        incorrectTitle.setFont(new Font("Aclonica", Font.BOLD, 15));
        incorrectTitle.setBackground(Color.BLACK);
        incorrectTitle.setPreferredSize(new Dimension(400,300));
        incorrectTitle.setSize(new Dimension(400,300));
        incorrectTitle.setMaximumSize(new Dimension(400,300));
        incorrectTitle.setMinimumSize(new Dimension(400,300));

        //BUTTON ACCEPT_________________________________________________________________________________________________
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth  = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.acceptButton = new JButton("Accept");
        this.acceptButton.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.acceptButton.setPreferredSize(new Dimension(140, 50));
        rectangleBackground.add(this.acceptButton, gbc);

        jop = new JOptionPane(rectangleBackground, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

        setBackground(Color.WHITE);
        jop.setOpaque(false);
        jop.setBackground(new Color(0,0,0,0));
        setPreferredSize(new Dimension(800,500));
        setUndecorated(true);
        setTitle("Error");
        setModal(true);
        setContentPane(jop);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * This method will decide if the window is shown (true) or not shown (false)
     * @param visible is a boolean that carries true or false if the window has to be seen or not
     */
    public void showWindow (boolean visible) {
        setVisible(visible);
    }

    /**
     * Method that controls all the actions that happen in the view (click in accept)
     * @param controller of type PlayersController
     */
    public void registerController (EventListener controller) {
        this.acceptButton.setActionCommand(WARNING_ACCEPT);
        this.acceptButton.addActionListener((ActionListener) controller);
    }

    /**
     * This method will update the warning message depending on which error the user has made
     * @param error is a string that contains the error message
     */
    public void updateWarning (String error) {
        //TEXT INCORRECT_______________________________________________________________________________
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth  = 6;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        incorrectTitle.setText("<html>" + error + "</html>");
        rectangleBackground.add(incorrectTitle, gbc);
        pack();
        setLocationRelativeTo(null);
    }
}
