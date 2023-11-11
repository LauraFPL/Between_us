package salle.url.edu.presentation.view;

import salle.url.edu.business.Game;
import salle.url.edu.presentation.controller.GamesController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameManagerView class that extends JPanel
 * Shows the list of all games
 * Possibilities to go back, play games, create games, duplicate games and delet games
 */
public class GameManagerView extends JPanel {
    public static final String GAME_MANAGER_CREATE_BUTTON_COMMAND = "gm_create_game_command";
    public static final String GAME_MANAGER_BACK_BUTTON_COMMAND = "gm_back_command";
    public static final String GAME_MANAGER_PLAY_BUTTON_COMMAND = "gm_playButton_command";
    public static final String GAME_MANAGER_COPY_BUTTON_COMMAND = "gm_copyButton_command";
    public static final String GAME_MANAGER_DELETE_BUTTON_COMMAND = "gm_deleteButton_command";

    //ATRIBUTS LOGIN
    private JPanel principal;

    private JButton backButton;
    private JButton createGameButton;
    private ArrayList<JButton> playButtons;
    private ArrayList<JButton> trashButtons;
    private ArrayList<JButton> copyButtons;
    private ArrayList<String> gamesNames;
    private int numGames;

    private JPanel jPanelAux;

    private JPanel gamesNorthFromCenter;

    /**
     * Constructor of the class
     */
    public GameManagerView (ArrayList<Game> games) {
        playButtons = new ArrayList<JButton>();
        trashButtons = new ArrayList<JButton>();
        copyButtons = new ArrayList<JButton>();
        gamesNames = new ArrayList<String>();
        numGames = 0;

        jPanelAux = new JPanel();
        jPanelAux.setOpaque(false);
        jPanelAux.setSize(1200,220);
        jPanelAux.setBorder(BorderFactory.createEmptyBorder(0,0,200,0));

        this.setLayout(new BorderLayout());
        this.setSize(1920,1080);
        principal = new JImagePanel("files/Assets/background.jpg");
        principal.setLayout(new BorderLayout());
        drawAll(games);

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
    public void drawAll (ArrayList<Game> games) {
        JPanel center = drawCenter(games);
        JPanel north = drawNorth();

        //NORTH---------------------------------------------------------------------------------------------------------
        principal.add(north,BorderLayout.NORTH);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);
    }

    /**
     * It draws all what is inside the west of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawNorth () {
        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);

        try {
            ImageIcon backImg = new ImageIcon(ImageIO.read(new File("files/Assets/Back.png")).getScaledInstance(80,92,0) );
            this.backButton = new JButton(backImg);
            this.backButton.setPreferredSize(new Dimension(80,80));
            this.backButton.setBackground(Color.WHITE);
            this.backButton.setOpaque(false);
            this.backButton.setBorderPainted(false);

            north.add(this.backButton, BorderLayout.WEST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel gamesTitle = new JLabel("Games");
        gamesTitle.setFont(new Font("Aclonica", Font.BOLD, 80));
        gamesTitle.setForeground(Color.WHITE);
        north.add(gamesTitle, BorderLayout.CENTER);
        gamesTitle.setBorder(BorderFactory.createEmptyBorder(0,685, 0,0));

        north.setBorder(BorderFactory.createEmptyBorder(30,30,0,0));

        return north;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter (ArrayList<Game> games) {
        JPanel auxRectangle = new JPanel(new BorderLayout());
        auxRectangle.setBorder(BorderFactory.createEmptyBorder(20,250,100,250));

        auxRectangle.setOpaque(false);

        JImagePanel rectangle = new JImagePanel("files/Assets/GamesManager/rectangle2.png");
        rectangle.setOpaque(false);

        rectangle.setLayout(new BorderLayout());
        auxRectangle.add(rectangle, BorderLayout.CENTER);

        //NORTH FROM CENTER_____________________________________________________________________________________________
        gamesNorthFromCenter = new JPanel();
        gamesNorthFromCenter.setLayout(new BoxLayout(gamesNorthFromCenter, BoxLayout.Y_AXIS));
        gamesNorthFromCenter.setOpaque(false);
        gamesNorthFromCenter.setBackground(new Color(0,0,0,0));

        JScrollPane scroll = new JScrollPane(gamesNorthFromCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder(30,0,10,10));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        updateGames(games);

        rectangle.add(scroll, BorderLayout.CENTER);

        //SOUTH FROM CENTER_____________________________________________________________________________________________
        //BOTÓ__________________________________________________________________________________________________________
        JPanel southFromCenter = new JPanel(new GridBagLayout());
        this.createGameButton = new JButton("Create Game");
        this.createGameButton.setBackground(new Color(254,222,41));
        this.createGameButton.setForeground(Color.WHITE);
        this.createGameButton.setFont(new Font("Aclonica", Font.BOLD, 20));
        this.createGameButton.setPreferredSize(new Dimension(200,40));
        southFromCenter.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        southFromCenter.setOpaque(false);

        southFromCenter.add(this.createGameButton);
        rectangle.add(southFromCenter, BorderLayout.SOUTH);

        return auxRectangle;
    }

    /**
     * Method that updates the list of games
     * @param games of type ArrayList contains all the games created
     */
    public void updateGames (ArrayList<Game> games) {
        gamesNorthFromCenter.removeAll();

        for (int i = 0; i < games.size(); i++) {
            addGame(games.get(i));
        }
    }

    /**
     * Method that adds a fake game to test how it looks
     */
    public void addGame () {
        JPanel auxGame = new JPanel(new FlowLayout());
        auxGame.setOpaque(false);

        JImagePanel game = new JImagePanel("files/Assets/GamesManager/greenGame.png");
        game.setOpaque(false);
        game.setPreferredSize(new Dimension(1200,220));
        game.setLayout(new BorderLayout());

        auxGame.add(game);

        gamesNorthFromCenter.add(auxGame);

        //WEST----------------------------------------------------------------------------------------------------------
        JPanel infoWest = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        infoWest.setBorder(BorderFactory.createEmptyBorder(20,70,20,20));
        infoWest.setOpaque(false);

        game.add(infoWest, BorderLayout.WEST);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.weighty = 0.1;

        //Num partida---------------------------------------------------------------------------------------------------
        numGames++;
        JLabel numGame = new JLabel(numGames + ". ");
        numGame.setFont(new Font("Aclonica", Font.BOLD, 40));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        infoWest.add(numGame, constraints);

        //Nom partida---------------------------------------------------------------------------------------------------
        JLabel nomGame = new JLabel("Nom partida");
        nomGame.setFont(new Font("Aclonica", Font.BOLD, 40));

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        infoWest.add(nomGame, constraints);

        //Nom mapa------------------------------------------------------------------------------------------------------
        JLabel nomMapa = new JLabel("mapa: ");
        nomMapa.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        infoWest.add(nomMapa, constraints);

        //Mapa----------------------------------------------------------------------------------------------------------
        JImagePanel mapa = new JImagePanel("files/Assets/Maps/MAP1.png");
        mapa.setPreferredSize(new Dimension(50,50));

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        infoWest.add(mapa, constraints);

        //Num tripulants------------------------------------------------------------------------------------------------
        JLabel numTripulants = new JLabel("num tripulants: ");
        numTripulants.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(numTripulants, constraints);

        JLabel tripulants = new JLabel("10");
        tripulants.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(tripulants, constraints);

        //Num impostors-------------------------------------------------------------------------------------------------
        JLabel numImpostors = new JLabel("num impostors: ");
        numImpostors.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(numImpostors, constraints);

        JLabel impostors = new JLabel("10");
        impostors.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(impostors, constraints);

        //EAST----------------------------------------------------------------------------------------------------------
        JPanel buttonsEast = new JPanel(new GridBagLayout());
        buttonsEast.setOpaque(false);
        game.add(buttonsEast, BorderLayout.EAST);
        buttonsEast.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
        buttonsEast.setAlignmentX(Component.CENTER_ALIGNMENT);

        //PLAY BUTTON---------------------------------------------------------------------------------------------------
        try {
            ImageIcon playImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/play.png")).getScaledInstance(80,80,0) );
            JButton auxPlayButton = new JButton(playImg);
            auxPlayButton.setPreferredSize(new Dimension(100,100));
            auxPlayButton.setBackground(Color.WHITE);
            auxPlayButton.setOpaque(false);
            auxPlayButton.setBorderPainted(false);

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            playButtons.add(auxPlayButton);
            buttonsEast.add(auxPlayButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //copyButton----------------------------------------------------------------------------------------------------
        try {
            ImageIcon copyImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/copy.png")).getScaledInstance(80,80,0) );
            JButton auxCopyButton = new JButton(copyImg);
            auxCopyButton.setPreferredSize(new Dimension(100,100));
            auxCopyButton.setBackground(Color.WHITE);
            auxCopyButton.setOpaque(false);
            auxCopyButton.setBorderPainted(false);

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            copyButtons.add(auxCopyButton);
            buttonsEast.add(auxCopyButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TRASH BUTTON--------------------------------------------------------------------------------------------------
        try {
            ImageIcon trashImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/trash.png")).getScaledInstance(80,80,0) );
            JButton auxTrashButton = new JButton(trashImg);
            auxTrashButton.setPreferredSize(new Dimension(80,80));
            auxTrashButton.setBackground(Color.WHITE);
            auxTrashButton.setOpaque(false);
            auxTrashButton.setBorderPainted(false);

            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            trashButtons.add(auxTrashButton);
            buttonsEast.add(auxTrashButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamesNames.add("hola_" + numGames);
    }

    /**
     * Method that adds a new game to the view
     * @param gameToShow of type Game contains the game to be shown
     */
    public void addGame (Game gameToShow) {
        //Inicialitzem part de la informació
        numGames++;
        JPanel auxGame = new JPanel(new FlowLayout());
        auxGame.setOpaque(false);

        //Color del personatge
        JImagePanel game; // = new JImagePanel("files/Assets/GamesManager/greenGame.png");
        //System.out.println(gameToShow.getColorPlayerString());
        if (gameToShow.getColorPlayerString().equals("RED")) {
            game = new JImagePanel("files/Assets/GamesManager/redGame.png");
        } else if (gameToShow.getColorPlayerString().equals("BLUE")) {
            game = new JImagePanel("files/Assets/GamesManager/blueGame.png");
        } else if (gameToShow.getColorPlayerString().equals("GREEN")) {
            game = new JImagePanel("files/Assets/GamesManager/greenGame.png");
        } else if (gameToShow.getColorPlayerString().equals("PINK")) {
            game = new JImagePanel("files/Assets/GamesManager/pinkGame.png");
        } else if (gameToShow.getColorPlayerString().equals("ORANGE")) {
            game = new JImagePanel("files/Assets/GamesManager/orangeGame.png");
        } else if (gameToShow.getColorPlayerString().equals("YELLOW")) {
            game = new JImagePanel("files/Assets/GamesManager/yellowGame.png");
        } else if (gameToShow.getColorPlayerString().equals("BLACK")) {
            game = new JImagePanel("files/Assets/GamesManager/blackGame.png");
        } else if (gameToShow.getColorPlayerString().equals("PURPLE")) {
            game = new JImagePanel("files/Assets/GamesManager/purpleGame.png");
        } else if (gameToShow.getColorPlayerString().equals("WHITE")) {
            game = new JImagePanel("files/Assets/GamesManager/whiteGame.png");
        } else if (gameToShow.getColorPlayerString().equals("CYAN")) {
            game = new JImagePanel("files/Assets/GamesManager/cyanGame.png");
        } else if (gameToShow.getColorPlayerString().equals("DARK GREEN")) {
            game = new JImagePanel("files/Assets/GamesManager/darkGreenGame.png");
        } else {
            game = new JImagePanel("files/Assets/GamesManager/brownGame.png");
        }

        game.setOpaque(false);
        game.setPreferredSize(new Dimension(1200,220));
        game.setLayout(new BorderLayout());

        auxGame.add(game);

        gamesNorthFromCenter.add(auxGame);

        if (numGames == 2) {
            gamesNorthFromCenter.add(jPanelAux);
        } else if (numGames == 3) {
            gamesNorthFromCenter.remove(jPanelAux);
            gamesNorthFromCenter.validate();
            gamesNorthFromCenter.repaint();
        }

        //WEST----------------------------------------------------------------------------------------------------------
        JPanel infoWest = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        infoWest.setBorder(BorderFactory.createEmptyBorder(20,70,20,20));
        infoWest.setOpaque(false);

        game.add(infoWest, BorderLayout.WEST);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.weighty = 0.1;

        //Num partida---------------------------------------------------------------------------------------------------
        JLabel numGame = new JLabel( numGames + ". ");
        numGame.setFont(new Font("Aclonica", Font.BOLD, 40));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        infoWest.add(numGame, constraints);

        //Nom partida---------------------------------------------------------------------------------------------------
        JLabel nomGame = new JLabel(gameToShow.getName());
        nomGame.setFont(new Font("Aclonica", Font.BOLD, 40));

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        infoWest.add(nomGame, constraints);

        //Nom mapa------------------------------------------------------------------------------------------------------
        JLabel nomMapa = new JLabel("mapa: ");
        nomMapa.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        infoWest.add(nomMapa, constraints);

        //Mapa----------------------------------------------------------------------------------------------------------
        JImagePanel mapa;

        if (gameToShow.getMapId() == 1) {
            mapa = new JImagePanel("files/Assets/Maps/MAP1.png");
        } else if (gameToShow.getMapId() == 2) {
            mapa = new JImagePanel("files/Assets/Maps/MAP2.png");
        } else if (gameToShow.getMapId() == 3) {
            mapa = new JImagePanel("files/Assets/Maps/MAP3.png");
        } else if (gameToShow.getMapId() == 4) {
            mapa = new JImagePanel("files/Assets/Maps/MAP4.png");
        } else {
            mapa = new JImagePanel("files/Assets/Maps/MAP5.png");
        }

        mapa.setPreferredSize(new Dimension(50,50));

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.WEST;

        infoWest.add(mapa, constraints);

        //Num tripulants------------------------------------------------------------------------------------------------
        JLabel numTripulants = new JLabel("num tripulants: ");
        numTripulants.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(numTripulants, constraints);

        JLabel tripulants = new JLabel(String.valueOf(gameToShow.getNumTripulants()));
        tripulants.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(tripulants, constraints);

        //Num impostors-------------------------------------------------------------------------------------------------
        JLabel numImpostors = new JLabel("num impostors: ");
        numImpostors.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(numImpostors, constraints);

        JLabel impostors = new JLabel(String.valueOf(gameToShow.getNumImpostors()));
        impostors.setFont(new Font("Aclonica", Font.BOLD, 20));

        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        infoWest.add(impostors, constraints);

        //EAST----------------------------------------------------------------------------------------------------------
        JPanel buttonsEast = new JPanel(new GridBagLayout());
        buttonsEast.setOpaque(false);
        game.add(buttonsEast, BorderLayout.EAST);
        buttonsEast.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
        buttonsEast.setAlignmentX(Component.CENTER_ALIGNMENT);

        //PLAY BUTTON---------------------------------------------------------------------------------------------------
        if (!gameToShow.getFinished()) { //Si no s'ha acabat la partida, es pot tornar a entrar
            try {
                ImageIcon playImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/play.png")).getScaledInstance(80, 80, 0));
                JButton auxPlayButton = new JButton(playImg);
                auxPlayButton.setPreferredSize(new Dimension(100, 100));
                auxPlayButton.setBackground(Color.WHITE);
                auxPlayButton.setOpaque(false);
                auxPlayButton.setBorderPainted(false);

                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;

                playButtons.add(auxPlayButton);
                buttonsEast.add(auxPlayButton, constraints);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            playButtons.add(null);
        }

        //copyButton----------------------------------------------------------------------------------------------------
        try {
            ImageIcon copyImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/copy.png")).getScaledInstance(80,80,0) );
            JButton auxCopyButton = new JButton(copyImg);
            auxCopyButton.setPreferredSize(new Dimension(100,100));
            auxCopyButton.setBackground(Color.WHITE);
            auxCopyButton.setOpaque(false);
            auxCopyButton.setBorderPainted(false);

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            copyButtons.add(auxCopyButton);
            buttonsEast.add(auxCopyButton, constraints);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TRASH BUTTON--------------------------------------------------------------------------------------------------
        try {
            ImageIcon trashImg = new ImageIcon(ImageIO.read(new File("files/Assets/GamesManager/trash.png")).getScaledInstance(80,80,0) );
            JButton auxTrashButton = new JButton(trashImg);
            auxTrashButton.setPreferredSize(new Dimension(80,80));
            auxTrashButton.setBackground(Color.WHITE);
            auxTrashButton.setOpaque(false);
            auxTrashButton.setBorderPainted(false);

            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;

            trashButtons.add(auxTrashButton);
            buttonsEast.add(auxTrashButton, constraints);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamesNames.add(gameToShow.getName());
    }

    /**
     * Method that removes a game from the view
     * @param gameName of type string contains the name of the game to be removed
     */
    public void removeGame (String gameName) {
        boolean trobat = false;

        for (int i = 0; i < numGames && !trobat; i++) {
            if (gamesNames.get(i).equals(gameName)){
                gamesNorthFromCenter.remove(i);
                playButtons.remove(i);
                copyButtons.remove(i);
                trashButtons.remove(i);
                gamesNames.remove(i);

                numGames--;
                trobat = true;
            }

            if (numGames == 2) {
                gamesNorthFromCenter.add(jPanelAux);
            } else {
                gamesNorthFromCenter.remove(jPanelAux);
            }
        }
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller of type GamesController contains the ActionListeners of the view
     */
    public void registerController (GamesController controller) {

        backButton.setActionCommand(GAME_MANAGER_BACK_BUTTON_COMMAND);
        createGameButton.setActionCommand(GAME_MANAGER_CREATE_BUTTON_COMMAND);

        backButton.addActionListener(controller);
        createGameButton.addActionListener(controller);
        
        for (int i = 0; i < playButtons.size(); i++) {

            JButton playButton = playButtons.get(i);
            JButton copyButton = copyButtons.get(i);
            JButton trashButton = trashButtons.get(i);

            if (playButton != null){playButton.setActionCommand(GAME_MANAGER_PLAY_BUTTON_COMMAND + "="+ gamesNames.get(i));}
            copyButton.setActionCommand(GAME_MANAGER_COPY_BUTTON_COMMAND + "=" + gamesNames.get(i));
            trashButton.setActionCommand(GAME_MANAGER_DELETE_BUTTON_COMMAND + "=" + gamesNames.get(i));

            if (playButton != null){playButton.addActionListener(controller);}
            copyButton.addActionListener(controller);
            trashButton.addActionListener(controller);
        }
    }

    /**
     * Method that connects the listeners with the controller but just of the new game inserted
     * @param controller of type GamesController contains the ActionListeners of the view
     */
    public void registerControllerAddGame (GamesController controller) {

        int index = playButtons.size() - 1;

        JButton playButton = playButtons.get(index);
        JButton copyButton = copyButtons.get(index);
        JButton trashButton = trashButtons.get(index);

        if (playButton != null){playButton.setActionCommand(GAME_MANAGER_PLAY_BUTTON_COMMAND + "="+ gamesNames.get(index));}
        copyButton.setActionCommand(GAME_MANAGER_COPY_BUTTON_COMMAND + "=" + gamesNames.get(index));
        trashButton.setActionCommand(GAME_MANAGER_DELETE_BUTTON_COMMAND + "=" + gamesNames.get(index));

        if (playButton != null){playButton.addActionListener(controller);}
        copyButton.addActionListener(controller);
        trashButton.addActionListener(controller);

    }



    /**
     * Method that eliminates the play button of the game you pass as a parameter
     * @param gameName name of the game that you want to eliminate de play
     */
    public void deletePlayButtonFromGame (String gameName) {
        boolean trobat = false;
        int indexGameName = -1;

        for (int i = 0; i < gamesNames.size() && !trobat; i++) {
            if (gamesNames.get(i).equals(gameName)){
                trobat = true;
                indexGameName = i;
            }
        }

        JButton play = playButtons.get(indexGameName);
        play.setVisible(false);
    }
}
