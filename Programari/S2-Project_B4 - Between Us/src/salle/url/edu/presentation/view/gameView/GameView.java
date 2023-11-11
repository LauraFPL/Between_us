package salle.url.edu.presentation.view.gameView;

import salle.url.edu.business.*;
import salle.url.edu.business.Character;
import salle.url.edu.presentation.controller.CurrentGameController;
import salle.url.edu.presentation.view.JImagePanel;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameView class that extends JPanel
 * Shows what the user is going to see during the course of the game
 */
public class GameView  extends JPanel implements Runnable {
    public static final String GAME_BACK_BUTTON_COMMAND = "g_back_command";
    public static final String GAME_OBJECTIVE_UP_BUTTON_COMMAND = "g_objective_up_command";
    public static final String GAME_OBJECTIVE_DOWN_BUTTON_COMMAND = "g_objective_down_command";
    public static final String GAME_CONTROL_UP_ARROW_COMMAND = "g_control_up_arrow_command";
    public static final String GAME_CONTROL_DOWN_ARROW_COMMAND = "g_control_down_arrow_command";
    public static final String GAME_CONTROL_LEFT_ARROW_COMMAND = "g_control_left_arrow_command";
    public static final String GAME_CONTROL_RIGHT_ARROW_COMMAND = "g_control_right_arrow_command";
    public static final String GAME_TOGGLE_VISIBILITY_COMMAND = "g_control_visibility_command";
    public static final String GAME_ACTION_CLASSIFICATION_COMMAND = "g_action_classification_command";
    public static final String GAME_ACTION_LOGS_COMMAND = "g_action_logs_command";

    //THREADS
    private boolean stop;

    //RESOURCES
    public static Font aclonica60;
    public static Font aclonica32;

    //GLOBAL
    private JImagePanel backgroundPanel;

    //CENTER
    private JPanel centerPanel;
    private JPanel mapViewPanel;
    private ArrayList<JImagePanel> mapRooms;
    private JPanel playerControlsPanel;
    private JPanel visibilityPanel;
    private JPanel playerActionPanel;
    private JPanel arrowsControlsPanel;
    private JPanel upArrowPanel;
    private JPanel otherArrowsPanel;
    private JButton visibilityButton;
    private ImageIcon visibleImage;
    private ImageIcon notVisibleImage;
    private ImageIcon logsTableActionImage;
    private ImageIcon classificationTableActionImage;
    private ImageIcon logsTableInactiveImage;
    private ImageIcon classificationTableInactiveImage;
    private JButton logsActionButton;
    private JButton classificationActionButton;
    private JButton upArrowButton;
    private JButton downArrowButton;
    private JButton leftArrowButton;
    private JButton rightArrowButton;

    private boolean globalVisibility;

    private Clip clipLooseSound;

    //EAST
    private JPanel eastPanel;
    private JPanel susTablePanel;
    private SusSectionPanel unknownSusPanel;
    private SusSectionPanel susSusPanel;
    private SusSectionPanel notSusSusPanel;

    //WEST
    private JPanel westPanel;
    private JButton backArrowButton;

    private CurrentGameController currentGameController;

    /**
     * Constructor of the class
     * @param currentGameController of type CurrentGameController has control of the game that is currently running
     * @throws IOException in case something goes wrong
     */
    public GameView(CurrentGameController currentGameController) throws IOException {

        this.currentGameController = currentGameController;


        try {
            //create the font to use. Specify the size!
            aclonica60 = Font.createFont(Font.TRUETYPE_FONT, new File("files/assets/Aclonica-Regular.ttf")).deriveFont(60f);
            aclonica32 = Font.createFont(Font.TRUETYPE_FONT, new File("files/assets/Aclonica-Regular.ttf")).deriveFont(32f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(aclonica60);
            ge.registerFont(aclonica32);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


        Map map = currentGameController.getMap();

        int[] dim = map.getDimension();
        int width = dim[0];
        int height = dim[1];

        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        configureWindow();
        configureCenterView(width, height, currentGameController.getGame());
        configureEastView(currentGameController.getGame());
        configureWestView();

        add(backgroundPanel, BorderLayout.CENTER);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.add(westPanel, BorderLayout.WEST);
        backgroundPanel.add(eastPanel, BorderLayout.EAST);

        stop = false;

        String soundName = "files/assets/Audio/game.wav";
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
     * Method that sets the window visible
     */
    public void showWindow() {
        setVisible(true);
    }

    /**
     * Method that configures the game view creating a JPanel and setting its size
     * @throws IOException in case anything goes wrong
     */
    private void configureWindow () throws IOException {
        backgroundPanel = new JImagePanel(1920, 1080,ImageIO.read(new File("files/assets/background.jpg")));
        setSize(1920,1080);
        backgroundPanel.setLayout(new BorderLayout());

    }

    /**
     * Method that updates the map with the characters positions (movements)
     * @throws IOException in case anything goes wrong
     */
    public synchronized void updateMap () throws IOException {
        //System.out.println("Update");
        for (JPanel rooms: mapRooms) {
            rooms.removeAll();
        }

        int[] playerPos = currentGameController.getPositionFromPlayer();

        for (Character character: currentGameController.getGameCharacters()) {
            int[] position = character.getPosition();

            if (globalVisibility || (position[0] == playerPos[0] && position[1] == playerPos[1])) {
                mapRooms.get(position[0] + position[1] * 4).add(new JImagePanel(40, 40, ImageIO.read(new File(character.getColorPath()))));
            }

            if (character instanceof Player) {
                if (currentGameController.roomIsActionLogs(position)) {
                    logsActionButton.setIcon(logsTableActionImage);
                    logsActionButton.setEnabled(true);

                } else if (currentGameController.roomIsActionClassification(position)) {
                    classificationActionButton.setIcon(classificationTableActionImage);
                    classificationActionButton.setEnabled(true);

                } else {
                    classificationActionButton.setIcon(classificationTableInactiveImage);
                    classificationActionButton.setEnabled(false);
                    logsActionButton.setIcon(logsTableInactiveImage);
                    logsActionButton.setEnabled(false);
                }
            }
        }

        for (JPanel panel: mapRooms) {
            mapViewPanel.add(panel);
        }

        updateUI();
    }

    /**
     * Method that configures the center view of the current game
     * @param width integer that contains the width of the view
     * @param height integer that contains the height of the game
     * @param game of type Game contains the information of the game that is being played
     * @throws IOException in case anything goes wrong
     */
    private void configureCenterView (int width, int height, Game game) throws IOException {
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        mapViewPanel = new JPanel();
        mapViewPanel.setOpaque(false);
        mapViewPanel.setLayout(new GridLayout(width, height));
        mapViewPanel.setSize(width*200, height*200);

        mapRooms = new ArrayList<>();

        for (int i = 0; i < width*height; i++) {
            String path = currentGameController.getGameMapRoomPath(i);
            mapRooms.add(new JImagePanel(200,200, ImageIO.read(new File(path))));
            mapRooms.get(i).setOpaque(false);
        }

        for (Character character: game.getCharacters()) {
            int[] position = character.getPosition();
            mapRooms.get(position[0]+position[1]*4).add(new JImagePanel(40,40, ImageIO.read(new File(character.getColorPath()))));
        }

        for (JPanel panel: mapRooms) {
            mapViewPanel.add(panel);
        }

        playerControlsPanel = new JPanel();
        playerControlsPanel.setOpaque(false);
        arrowsControlsPanel = new JPanel();
        arrowsControlsPanel.setOpaque(false);
        arrowsControlsPanel.setLayout(new BoxLayout(arrowsControlsPanel, BoxLayout.Y_AXIS));
        visibilityPanel = new JPanel();
        visibilityPanel.setOpaque(false);
        upArrowPanel = new JPanel();
        upArrowPanel.setOpaque(false);
        otherArrowsPanel = new JPanel();
        otherArrowsPanel.setOpaque(false);
        playerActionPanel = new JPanel();
        playerActionPanel.setOpaque(false);

        //Visibility Button
        String path = "files/assets/UI_partida/icon-visible.png";
        visibleImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        path = "files/assets/UI_partida/icon-not-visible.png";
        notVisibleImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        visibilityButton = new JButton(notVisibleImage);
        visibilityButton.setBorderPainted(false);
        visibilityButton.setOpaque(false);
        visibilityButton.setBackground(Color.WHITE);
        globalVisibility = false;

        //Player Action
        path = "files/assets/UI_partida/verify.png";
        classificationTableActionImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        path = "files/assets/UI_partida/history.png";
        logsTableActionImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        path = "files/assets/UI_partida/verify_inactive.png";
        classificationTableInactiveImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        path = "files/assets/UI_partida/history_inactive.png";
        logsTableInactiveImage = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));

        classificationActionButton = new JButton(classificationTableInactiveImage);
        classificationActionButton.setBorderPainted(false);
        classificationActionButton.setOpaque(false);
        classificationActionButton.setBackground(Color.WHITE);
        logsActionButton = new JButton(logsTableInactiveImage);
        logsActionButton.setBorderPainted(false);
        logsActionButton.setOpaque(false);
        logsActionButton.setBackground(Color.WHITE);

        //Up Arrow
        path = "files/assets/UI_partida/Arrow-Up.png";
        ImageIcon image = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(80,80,100));
        upArrowButton = new JButton(image);
        upArrowButton.setBorderPainted(false);
        upArrowButton.setOpaque(false);
        upArrowButton.setBackground(Color.WHITE);
        upArrowButton.setFocusable(true);

        //Down Arrow
        path = "files/assets/UI_partida/Arrow-Down.png";
        image = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(80,80,100));
        downArrowButton = new JButton(image);
        downArrowButton.setBorderPainted(false);
        downArrowButton.setOpaque(false);
        downArrowButton.setBackground(Color.WHITE);
        downArrowButton.setFocusable(true);

        //Left Arrow
        path = "files/assets/UI_partida/Arrow-Left.png";
        image = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(80,80,100));
        leftArrowButton = new JButton(image);
        leftArrowButton.setBorderPainted(false);
        leftArrowButton.setOpaque(false);
        leftArrowButton.setBackground(Color.WHITE);
        leftArrowButton.setFocusable(true);

        //Right Arrow
        path = "files/assets/UI_partida/Arrow-Right.png";
        image = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(80,80,100));

        rightArrowButton = new JButton(image);
        rightArrowButton.setBorderPainted(false);
        rightArrowButton.setOpaque(false);
        rightArrowButton.setBackground(Color.WHITE);
        upArrowButton.setFocusable(true);
        upArrowPanel.add(upArrowButton);

        otherArrowsPanel.add(leftArrowButton);
        otherArrowsPanel.add(downArrowButton);
        otherArrowsPanel.add(rightArrowButton);

        arrowsControlsPanel.add(upArrowPanel, CENTER_ALIGNMENT);
        arrowsControlsPanel.add(otherArrowsPanel, CENTER_ALIGNMENT);

        playerActionPanel.add(classificationActionButton);
        playerActionPanel.add(logsActionButton);

        visibilityPanel.add(visibilityButton);

        playerControlsPanel.add(arrowsControlsPanel);
        playerControlsPanel.add(visibilityPanel);
        playerControlsPanel.add(playerActionPanel);

        centerPanel.add(mapViewPanel, BorderLayout.CENTER);
        centerPanel.add(playerControlsPanel, BorderLayout.CENTER);
    }

    /**
     * Method that configures the west view of the current game
     * @throws IOException in case anything goes wrong
     */
    private void configureWestView () throws IOException {
        westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        String path = "files/assets/Back.png";
        ImageIcon backArrowIcon = new ImageIcon( ImageIO.read(new File(path)).getScaledInstance(100,100,100));
        backArrowButton = new JButton(backArrowIcon);
        backArrowButton.setPreferredSize(new Dimension(100,100));
        backArrowButton.setOpaque(false);
        backArrowButton.setBackground(Color.YELLOW);
        backArrowButton.setBorderPainted(false);

        westPanel.add(backArrowButton);
    }

    /**
     * Method that configures the east view of the current game
     * @param game of type Game that contains the information of the current game
     */
    private void configureEastView (Game game) {
        eastPanel = new JPanel();
        eastPanel.setOpaque(false);
        eastPanel.setPreferredSize(new Dimension(750,1080));
        eastPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,50));

        susTablePanel = new JPanel();
        susTablePanel.setLayout(new GridBagLayout());
        susTablePanel.setOpaque(true);
        susTablePanel.setBackground(new Color(255,255,255,50));
        susTablePanel.setBorder(BorderFactory.createEmptyBorder(20,80,20,80));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;

        unknownSusPanel = new SusSectionPanel("Unknown");
        susSusPanel = new SusSectionPanel("Sus");
        notSusSusPanel = new SusSectionPanel("Not Sus");

        updateCharacters();

        susTablePanel.add(unknownSusPanel, constraints);
        constraints.gridy = 1;
        susTablePanel.add(susSusPanel, constraints);
        constraints.gridy = 2;
        susTablePanel.add(notSusSusPanel, constraints);

        eastPanel.add(susTablePanel);
    }

    /**
     * Method that updates the character's roles (SUS, NOT SUS or UNKNOWN)
     */
    public synchronized void updateCharacters () {
        unknownSusPanel.resetSuspitious();
        susSusPanel.resetSuspitious();
        notSusSusPanel.resetSuspitious();

        for (Character character : currentGameController.getGame().getCharacters()) {
            if (character instanceof NPC) {
                if (((NPC) character).getSuspitionRol() == NPCSuspition.UNKNOWN) {
                    unknownSusPanel.addSuspicious(character.getColorColor(), character.getColorString());
                } else if (((NPC) character).getSuspitionRol() == NPCSuspition.SUS) {
                    susSusPanel.addSuspicious(character.getColorColor(), character.getColorString());
                } else {
                    notSusSusPanel.addSuspicious(character.getColorColor(), character.getColorString());
                }
            }
        }
    }

    /**
     * Method that registers the action listeners and sends them to the controller
     * @param controller of type CurrentGameController contains all the action listeners of the view
     */
    public void registerController (CurrentGameController controller) {
        this.backArrowButton.setActionCommand(GAME_BACK_BUTTON_COMMAND);
        this.backArrowButton.addActionListener(controller);

        this.visibilityButton.setActionCommand(GAME_TOGGLE_VISIBILITY_COMMAND);
        this.visibilityButton.addActionListener(controller);

        this.upArrowButton.setActionCommand(GAME_CONTROL_UP_ARROW_COMMAND);
        this.upArrowButton.addActionListener(controller);
        this.upArrowButton.addKeyListener(controller);
        this.downArrowButton.setActionCommand(GAME_CONTROL_DOWN_ARROW_COMMAND);
        this.downArrowButton.addActionListener(controller);
        this.downArrowButton.addKeyListener(controller);
        this.leftArrowButton.setActionCommand(GAME_CONTROL_LEFT_ARROW_COMMAND);
        this.leftArrowButton.addActionListener(controller);
        this.leftArrowButton.addKeyListener(controller);
        this.rightArrowButton.setActionCommand(GAME_CONTROL_RIGHT_ARROW_COMMAND);
        this.rightArrowButton.addActionListener(controller);
        this.rightArrowButton.addKeyListener(controller);
        this.addKeyListener(controller);

        this.logsActionButton.setActionCommand(GAME_ACTION_LOGS_COMMAND);
        this.logsActionButton.addActionListener(controller);
        this.classificationActionButton.setActionCommand(GAME_ACTION_CLASSIFICATION_COMMAND);
        this.classificationActionButton.addActionListener(controller);

        registerWestController(controller);

    }

    /**
     * Method to register the controllers of the WestPanel
     * @param controller ActionListener
     */
    public void registerWestController(ActionListener controller ){
        unknownSusPanel.registerController(controller);
        susSusPanel.registerController(controller);
        notSusSusPanel.registerController(controller);
    }

    /**
     * Method that puts the variable stop to true
     * It is used to stop threads
     */
    public void stopGame () {stop = true;}

    /**
     * Method run of Runnable
     * Threads work because of it
     */
    @Override
    public void run () {

        while(!stop) {
            try {
                Thread.sleep(30);
                updateMap();
                int countAlive = 0;
                for(int i = 0; i < currentGameController.getGameCharacters().size(); i++) {
                    if (currentGameController.getGameCharacters().get(i).isAlive()) {
                        countAlive++;
                    }
                }

                if(!currentGameController.playerIsAlive() || ((countAlive - currentGameController.getNumImpostor()) == currentGameController.getNumImpostor())){
                    currentGameController.finishGameDefeat();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that shows all the players on the map when activated, and only yourself when deactivated
     */
    public void toggleGlobalVisibility () {
        System.out.println("GameView > toggleGlobalVisibility");
        if (globalVisibility) {
            globalVisibility = false;
            visibilityButton.setIcon(notVisibleImage);
        } else {
            globalVisibility = true;
            visibilityButton.setIcon(visibleImage);
        }
    }

    /**
     * Method that shows that your suspicious thoughts are not correct
     */
    public void showSusSuspectIncorrect () {
        JOptionPane.showMessageDialog(this, "Your suspicion thoughts aren't correct");
    }

    /**
     * Used to stop the sound
     */
    public void stopSound () {
        clipLooseSound.stop();
    }
}