package salle.url.edu.presentation.view;

import salle.url.edu.presentation.controller.PlayersController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * RegisterView class that extends JPanel
 * Will be shown if the user does not have an account and clicks on "register now"
 */
public class RegisterView extends JPanel {
    public static final String REGISTER_START_BUTTON_COMMAND = "r_start_command";
    public static final String REGISTER_BACK_BUTTON_COMMAND = "r_back_command";
    public static final String REGISTER_LOGIN_BUTTON_COMMAND = "r_LOGIN_command";

    //ATRIBUTS REGISTER
    private final JPanel principal;

    private JButton backButton;
    private JButton loginButton;
    private JButton startButton;

    private JTextField writeUserName;
    private JPasswordField writePassword;
    private JTextField writeEmail;
    private JPasswordField writeCPassword;

    /**
     * Constructor of the class
     */
    public RegisterView () {
        this.setLayout(new BorderLayout());

        principal = new JImagePanel("files/Assets/background.jpg");
        principal.setLayout(new BorderLayout());
        drawAll();

        this.add(principal, BorderLayout.CENTER);
        setFocusable(true);
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
            ImageIcon backImg = new ImageIcon(ImageIO.read(new File("files/Assets/Back.png")).getScaledInstance(80,80,0) );
            this.backButton = new JButton(backImg);
            this.backButton.setPreferredSize(new Dimension(80,80));
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
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        JPanel auxRectangle = new JPanel(new BorderLayout());
        auxRectangle.setBorder(BorderFactory.createEmptyBorder(150,570,150,650));

        auxRectangle.setOpaque(false);

        JImagePanel rectangle = new JImagePanel("files/Assets/Login_Register/rectangle.png");
        rectangle.setOpaque(false);
        rectangle.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        rectangle.setPreferredSize(new Dimension(550,650));

        rectangle.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        auxRectangle.add(rectangle, BorderLayout.CENTER);

        //REGISTER______________________________________________________________________________________________________
        JLabel loginTitle = new JLabel("REGISTER");
        loginTitle.setFont(new Font("Aclonica", Font.BOLD, 64));
        loginTitle.setForeground(Color.WHITE);
        loginTitle.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0.1;

        rectangle.add(loginTitle, constraints);

        //IMATGE AMOGUS_________________________________________________________________________________________________
        JImagePanel redAmogus = new JImagePanel("files/Assets/Login_Register/RedAmogus.png");
        redAmogus.setPreferredSize(new Dimension(100,100));
        redAmogus.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        rectangle.add(redAmogus, constraints);

        //USER NAME_____________________________________________________________________________________________________
        JLabel userName = new JLabel("User name: ");
        userName.setFont(new Font("Aclonica", Font.BOLD, 28));
        userName.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(userName, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.writeUserName = new JTextField();
        this.writeUserName.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        rectangle.add(this.writeUserName, constraints);
        constraints.fill = GridBagConstraints.NONE;

        //Email_________________________________________________________________________________________________________
        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Aclonica", Font.BOLD, 28));
        email.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(email, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.writeEmail = new JTextField();
        this.writeEmail.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        rectangle.add(writeEmail,constraints);
        constraints.fill = GridBagConstraints.NONE;

        //password______________________________________________________________________________________________________
        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Aclonica", Font.BOLD, 28));
        password.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(password, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.writePassword = new JPasswordField();
        this.writePassword.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.writePassword.setEchoChar('*'); //mostrar el text de la password com a *

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        rectangle.add(writePassword,constraints);
        constraints.fill = GridBagConstraints.NONE;

        //Confirmacio password__________________________________________________________________________________________
        JLabel confirmacio_password = new JLabel("Confirm Password: ");
        confirmacio_password.setFont(new Font("Aclonica", Font.BOLD, 28));
        confirmacio_password.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(confirmacio_password, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.writeCPassword = new JPasswordField();
        this.writeCPassword.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.writePassword.setEchoChar('*'); //mostrar el text de la password com a *

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        rectangle.add(writeCPassword,constraints);
        constraints.fill = GridBagConstraints.NONE;

        //You dont have an account______________________________________________________________________________________
        JLabel noAccount = new JLabel("Already have an account?");
        this.loginButton = new JButton("Login here");

        //noAccount
        noAccount.setFont(new Font("Aclonica", Font.BOLD, 14));
        noAccount.setForeground(new Color(89,62,255));
        noAccount.setForeground(new Color(89,62,255));

        //Register
        this.loginButton.setFont(new Font("Aclonica", Font.BOLD, 14));
        this.loginButton.setForeground(new Color(89,62,255));
        this.loginButton.setBackground(Color.WHITE);
        this.loginButton.setOpaque(false);
        this.loginButton.setBorderPainted(false);
        this.loginButton.setPreferredSize(new Dimension(150,30));

        JPanel dontHaveAccount = new JPanel(new FlowLayout());
        dontHaveAccount.add(noAccount);
        dontHaveAccount.add(this.loginButton);
        dontHaveAccount.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        rectangle.add(dontHaveAccount,constraints);

        //BOTÓ__________________________________________________________________________________________________________
        this.startButton = new JButton("Register");
        this.startButton.setBackground(new Color(254,222,41));
        this.startButton.setForeground(Color.WHITE);
        this.startButton.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        rectangle.add(this.startButton, constraints);

        return auxRectangle;
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type PlayersController
     */
    public void registerController (PlayersController controller) {
        this.backButton.setActionCommand(REGISTER_BACK_BUTTON_COMMAND);
        this.loginButton.setActionCommand(REGISTER_LOGIN_BUTTON_COMMAND);
        this.startButton.setActionCommand(REGISTER_START_BUTTON_COMMAND);

        this.backButton.addActionListener(controller);
        this.loginButton.addActionListener(controller);
        this.startButton.addActionListener(controller);

        addKeyListener(controller);
    }

    //GETTERS___________________________________________________________________________________________________________
    /**
     * Method that gets the username
     * @return a String with the username
     */
    public String getUserName () {return this.writeUserName.getText();}

    /**
     * Method that gets the password
     * @return a String with the password
     */
    public String getPassword () {
        return String.valueOf(this.writePassword.getPassword());
    }

    /**
     * Method that gets the email
     * @return a String with the email
     */
    public String getEmail () {return this.writeEmail.getText();}

    /**
     * Method that gets the confirmed password
     * @return a String with the confirmed password
     */
    public String getConfirmedPassword () {
        return String.valueOf(this.writeCPassword.getPassword());
    }

    /**
     * Method that puts the jtextfields empty
     */
    public void setJtextFieldEmpty () {
        writePassword.setText("");
        writeUserName.setText("");
        writeCPassword.setText("");
        writeEmail.setText("");
    }
}
