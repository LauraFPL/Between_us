package salle.url.edu.presentation.view;

import salle.url.edu.business.CharacterColors;
import salle.url.edu.presentation.controller.GamesController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * GameCreationView class that extends JPanel
 * It shows the menu to create a game (name, map, number of players, number of impostors and color of the player)
 */
public class GameCreationView extends JPanel {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static final String CREATION_CREATE_GAME_BUTTON_COMMAND = "c_createGame_command";
    public static final String CREATION_BACK_BUTTON_COMMAND = "c_back_command";
    public static final String FILE_CHOOSER_BUTTON_COMMAND = "f_chooser_command";

    // ATRIBUTS GAME CREATION
    private final JPanel principal;
    private JPanel jpanel_game;
    private JPanel jpanel_map;
    private JPanel jpanel_num;
    private JPanel jpanel_last;
    private JPanel jpanel_color;

    private JButton backButton;
    private JButton createButton;
    private JButton chooser;

    private JTextField writeGameName;

    private final String[] numberPla = {"4", "5", "6", "7", "8", "9" ,"10"};
    private final String[] numberImp = {"1", "2", "3"};
    private final String[] colors ={"Red", "Blue", "Green", "Pink", "Orange", "Yellow", "Black", "Purple", "White", "Cyan", "Dark_Green", "Brown"};

    private JComboBox numberPlayers;
    private JComboBox numberImpostors;
    private JComboBox chooseColors;

    private  JFileChooser fileChooser;

    /**
     * Constructor of the class
     */
    public GameCreationView () {
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
        JPanel west = drawWest();

        //WEST-------------------------------------------------------------------------
        principal.add(west,BorderLayout.WEST);

        //CENTER------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);
    }

    /**
     * It draws all what is inside the west of the tab
     * @return JPanel of what we need to put in the west of the Border Layout
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
     * It dras all that is inside the center of the view
     * @return JPanel that we need to put in the center of the Border Layout
     */
    public JPanel drawCenter () {
        JPanel auxRectangle = new JPanel(new BorderLayout());
        auxRectangle.setBorder(BorderFactory.createEmptyBorder(100,70,100,150));

        auxRectangle.setOpaque(false);

        JImagePanel rectangle = new JImagePanel("files/Assets/GamesManager/rectangle2.png");
        //rectangle.setPreferredSize(new Dimension(1000,500));
        rectangle.setOpaque(false);
        rectangle.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        rectangle.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        auxRectangle.add(rectangle, BorderLayout.CENTER);

        //IMATGE AMOGUS_________________________________________________________________________________________________
        JImagePanel redAmogus = new JImagePanel("files/Assets/Login_Register/RedAmogus.png");
        redAmogus.setPreferredSize(new Dimension(100,100));
        redAmogus.setSize(new Dimension(100, 100));
        redAmogus.setOpaque(false);

        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.anchor = GridBagConstraints.NORTHEAST;

        rectangle.add(redAmogus, constraints);
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        // GAME CREATION________________________________________________________________________________________________
        JLabel createGame = new JLabel("CREATE GAME");
        createGame.setFont(new Font("Aclonica", Font.BOLD, 58));
        createGame.setForeground(Color.WHITE);
        createGame.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weighty = 0.1;

        rectangle.add(createGame, constraints);

        //JPANEL_GAME___________________________________________________________________________________________________
        this.jpanel_game = new JPanel();
        this.jpanel_game.setOpaque(false);
        this.jpanel_game.setPreferredSize(new Dimension(200,40));

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 8;
        constraints.gridheight = 1;

        rectangle.add(this.jpanel_game, constraints);

        // GAME NAME____________________________________________________________________________________________________
        JLabel gameName = new JLabel("Name of the game: ");
        gameName.setFont(new Font("Aclonica", Font.BOLD, 28));
        gameName.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(gameName, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.writeGameName = new JTextField();
        this.writeGameName.setFont(new Font("Aclonica", Font.BOLD, 18));
        this.writeGameName.setPreferredSize(new Dimension(500,40));

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        rectangle.add(this.writeGameName, constraints);
        constraints.fill = GridBagConstraints.NONE;

        //JPANEL_MAP____________________________________________________________________________________________________
        this.jpanel_map = new JPanel();
        this.jpanel_map.setOpaque(false);
        this.jpanel_map.setPreferredSize(new Dimension(200,40));

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 8;
        constraints.gridheight = 1;

        rectangle.add(this.jpanel_map, constraints);

        // SELECT MAP___________________________________________________________________________________________________
        JLabel selectMap = new JLabel("Select the map: ");
        selectMap.setFont(new Font("Aclonica", Font.BOLD, 28));
        selectMap.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(selectMap, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        JPanel flow = new JPanel();
        flow.setOpaque(false);
        flow.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        flow.setPreferredSize(new Dimension(1000,200));

        flow.setLayout(new FlowLayout(FlowLayout.LEFT,30, 6));

        JImagePanel map1 = new JImagePanel("files/Assets/Maps/MAP1.png");
        map1.setPreferredSize(new Dimension(200,200));
        JImagePanel map2 = new JImagePanel("files/Assets/Maps/MAP2.png");
        map2.setPreferredSize(new Dimension(200,200));
        JImagePanel map3 = new JImagePanel("files/Assets/Maps/MAP3.png");
        map3.setPreferredSize(new Dimension(200,200));
        JImagePanel map4 = new JImagePanel("files/Assets/Maps/MAP4.png");
        map4.setPreferredSize(new Dimension(200,200));
        JImagePanel map5 = new JImagePanel("files/Assets/Maps/MAP5.png");
        map5.setPreferredSize(new Dimension(200,200));
        this.chooser = new JButton("Choose map");
        chooser.setBackground(new Color(98,120,255));
        chooser.setForeground(Color.WHITE);
        chooser.setFont(new Font("Aclonica", Font.BOLD, 20));
        chooser.setPreferredSize(new Dimension(180,60));

        flow.add(map1);
        flow.add(map2);
        flow.add(map3);
        flow.add(map4);
        flow.add(map5);
        flow.add(chooser);

        rectangle.add(flow);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 8;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(flow, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;

        //JPANEL_NUM____________________________________________________________________________________________________
        this.jpanel_num = new JPanel();
        this.jpanel_num.setOpaque(false);
        this.jpanel_num.setPreferredSize(new Dimension(200,40));

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 8;
        constraints.gridheight = 1;

        rectangle.add(this.jpanel_num, constraints);

        // NUM PLAYERS__________________________________________________________________________________________________
        JLabel numPlayers = new JLabel("Number of players: ");
        numPlayers.setFont(new Font("Aclonica", Font.BOLD, 22));
        numPlayers.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(numPlayers, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.numberPlayers = new JComboBox(numberPla);
        this.numberPlayers.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.numberPlayers.setBackground(new Color(255,255,255));
        this.numberPlayers.setPreferredSize(new Dimension(70,40));

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(this.numberPlayers, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        // NUM IMPOSTORS________________________________________________________________________________________________
        JLabel numImpostors = new JLabel("Number of impostors: ");
        numImpostors.setFont(new Font("Aclonica", Font.BOLD, 22));
        numImpostors.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(numImpostors, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.numberImpostors = new JComboBox(numberImp);
        this.numberImpostors.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.numberImpostors.setBackground(new Color(255,255,255));
        this.numberImpostors.setPreferredSize(new Dimension(70,40));

        constraints.gridx = 3;
        constraints.gridy = 10;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.2;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(this.numberImpostors, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        //JPANEL_COLOR__________________________________________________________________________________________________
        this.jpanel_color = new JPanel();
        this.jpanel_color.setOpaque(false);
        this.jpanel_color.setPreferredSize(new Dimension(200,40));

        constraints.gridx = 6;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;

        rectangle.add(this.jpanel_color, constraints);

        // COLOR________________________________________________________________________________________________________
        JLabel color = new JLabel("Choose color: ");
        color.setFont(new Font("Aclonica", Font.BOLD, 22));
        color.setForeground(Color.WHITE);

        constraints.gridx = 7;
        constraints.gridy = 9;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(color, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        this.chooseColors = new JComboBox(colors);
        this.chooseColors.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.chooseColors.setBackground(new Color(255,255,255));
        this.chooseColors.setPreferredSize(new Dimension(155,40));

        constraints.gridx = 7;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weighty = 0.2;
        constraints.anchor = GridBagConstraints.WEST;

        rectangle.add(this.chooseColors, constraints);
        constraints.anchor = GridBagConstraints.CENTER;

        //JPANEL_LAST___________________________________________________________________________________________________
        this.jpanel_last = new JPanel();
        this.jpanel_last.setOpaque(false);
        this.jpanel_last.setPreferredSize(new Dimension(200,40));

        constraints.gridx = 0;
        constraints.gridy = 11;
        constraints.gridwidth = 8;
        constraints.gridheight = 1;

        rectangle.add(this.jpanel_last, constraints);

        //BOTÓ__________________________________________________________________________________________________________
        this.createButton = new JButton("Create Game");
        this.createButton.setBackground(new Color(254,222,41));
        this.createButton.setForeground(Color.WHITE);
        this.createButton.setFont(new Font("Aclonica", Font.BOLD, 26));
        this.createButton.setPreferredSize(new Dimension(250,80));

        constraints.gridx = 0;
        constraints.gridy = 12;
        constraints.gridwidth = 8;
        constraints.gridheight = 1;

        rectangle.add(this.createButton, constraints);

        return auxRectangle;
    }

    /**
     * Method that sends the action listeners when a button is clicked
     * @param controller of type GamesController contains all the action listeners of the view
     */
    public void registerController (GamesController controller) {
        this.backButton.setActionCommand(CREATION_BACK_BUTTON_COMMAND);
        this.chooser.setActionCommand(FILE_CHOOSER_BUTTON_COMMAND);
        this.createButton.setActionCommand(CREATION_CREATE_GAME_BUTTON_COMMAND);

        this.backButton.addActionListener(controller);
        this.chooser.addActionListener(controller);
        this.createButton.addActionListener(controller);
    }

    /**
     * Method that creates a JFileChooser
     * @return a file chooser so that the player can choose the map
     */
    public JFileChooser filechooser () {
        fileChooser = null;
        fileChooser = new JFileChooser("files/maps");
        fileChooser.showSaveDialog(null);

        return fileChooser;
    }

    //GETTERS
    /**
     * Gets the name of the game
     * @return a string with the name of the game
     */
    public String getGameName () {
       return writeGameName.getText();
    }

    /**
     * Gets the number of impostors of the game
     * @return an integer with the number of impostors
     */
    public int getNumImpostors () {
        return  Integer.parseInt(numberImp[numberImpostors.getSelectedIndex()]);
    }

    /**
     * Gets the number of players of the game
     * @return an integer with the number of players
     */
    public int getNumPlayers () {
        return  Integer.parseInt(numberPla[numberPlayers.getSelectedIndex()]);
    }

    /**
     * Gets the path of the map of the game
     * @return a string with the path of the map
     */
    public String getMapPath () {
        if (fileChooser == null){
            return null;
        }

        return fileChooser.getSelectedFile().getPath();
    }

    /**
     * Gets the color of the main player
     * @return a CharacterColors with the color of the user
     */
    public CharacterColors getPlayerColor () {
        return CharacterColors.getCharacterColorFromString(colors[chooseColors.getSelectedIndex()]);
    }

    /**
     * Method that puts the jtextfield i JfileChooser empty
     */
    public void setJtextFieldJfilechooserEmpty () {
        writeGameName.setText("");
        //fileChooser.cancelSelection();
    }
}
