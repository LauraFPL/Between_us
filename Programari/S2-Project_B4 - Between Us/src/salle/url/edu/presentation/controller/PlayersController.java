package salle.url.edu.presentation.controller;

import salle.url.edu.business.PlayersManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import salle.url.edu.presentation.view.*;
import salle.url.edu.persistance.sql.SQLPlayerDAO;

/**
 * PlayersController class that implements ActionListener and KeyListener
 * Controls everything about the players in general
 */
public class PlayersController implements ActionListener, KeyListener {
    //LOGIN_____________________________________________________________________________________________________________
    public static final String LOGIN_ERROR = "The username or the password are incorrect";

    //REGISTER__________________________________________________________________________________________________________
    public static final String EMAIL_FORMAT_ERROR = "Incorrect format of the email. It mustn't contain ( ) or (') and it must contain an (@) and a (.) after it";
    public static final String PASSWORD_FORMAT_ERROR = "Incorrect format of the password, can't contain ('). It must have minimum 1 capital letter, 1 number and 8 character length";
    public static final String PASSWORD_CONFIRMATION_ERROR = "The password and the confirmation password don't coincide";
    public static final String USER_NAME_ALREADY_EXISTS = "This user name is already taken";
    public static final String EMAIL_ALREADY_EXISTS = "This email is already used";
    public static final String USER_NAME_NULL = "The user name can't be empty";
    public static final String USER_EQUAL_COMILLA = "The name, the password,... can't have a (')";

    //GRAPH
    public static final String EMPTY_GRAPH = "There is no record of player evolution";

    //ATRIBUTS__________________________________________________________________________________________________________
    private final PlayersManager playersManager;
    private final StartView startView;
    private final LoginView loginView;
    private final RegisterView registerView;
    private final AccountView accountView;
    private final SettingsView settingsView;
    private final MasterView masterView;
    private final PlayerEvolutionView playerEvolutionView;
    private final WarningView warningView;
    private final ConfirmationView confirmationViewPlayers;
    private GamesController gamesController;
    private boolean isWarningActive = false;
    private String userName;

    /**
     * Constructor of the class
     * @param masterView of type MaterView to show the different views
     */
    public PlayersController (MasterView masterView) {

        System.out.print("PlayerController > Loading views...");

        this.masterView = masterView;
        masterView.showWindow(true);

        //CONNEXIÓ AMB BUSSINESS
        this.playersManager = new PlayersManager(new SQLPlayerDAO());
        //INTERFICIE GRÀFICA

        //Start View
        this.startView = new StartView();
        startView.registerController(this);
        masterView.addView( startView, MasterView.START_VIEW);
        masterView.changeView(MasterView.START_VIEW);

        //Login
        this.loginView = new LoginView();
        masterView.addView(loginView, MasterView.LOGIN_VIEW);
        loginView.registerController(this);

        //registre
        this.registerView = new RegisterView();
        registerView.registerController(this);
        masterView.addView( registerView, MasterView.REGISTER_VIEW);

        //account view
        this.accountView = new AccountView();
        accountView.registerController(this);
        masterView.addView( accountView, MasterView.ACCOUNT_VIEW);

        //Settings
        this.settingsView = new SettingsView();
        settingsView.registerController(this);
        masterView.addView( settingsView, MasterView.SETTINGS_VIEW);

        //Warning
        this.warningView = new WarningView();
        warningView.registerController(this);
        //masterView.addView( warningView, MasterView.WARNING_VIEW);

        //Confirm
        this.confirmationViewPlayers = new ConfirmationView();
        this.confirmationViewPlayers.showWindow(true);
        this.confirmationViewPlayers.registerControllerPlayers(this);
        masterView.addView(confirmationViewPlayers, MasterView.CONFIRMATION_VIEW_PLAYERS);

        //PlayerEvolution
        this.playerEvolutionView = new PlayerEvolutionView();
        this.playerEvolutionView.showWindow(true);
        this.playerEvolutionView.registerController(this);
        masterView.addView(playerEvolutionView, MasterView.PLAYER_EVOLUTION_VIEW);

        System.out.println(" . . . . done");
    }

    //INTERFICIE GRÀFICA________________________________________________________________________________________________
    /**
     * Method that controls all the actions listeners sent from other classes
     * @param e of type ActionEvent that contains the action performed on the other classes
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        //START VIEW----------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(StartView.START_LEAVE_BUTTON_COMMAND)) { //Exit Program
            System.exit(0);

            return;
        }

        if (e.getActionCommand().equals(StartView.START_PLAY_BUTTON_COMMAND)) { // Start
            masterView.changeView(MasterView.LOGIN_VIEW);
            loginView.setJtextFieldEmpty();
            loginView.updateUI();

            return;
        }

        //LOGIN VIEW ---------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(LoginView.LOGIN_BACK_BUTTON_COMMAND)) { //Back
            masterView.changeView(MasterView.START_VIEW);

            return;
        }

        if (e.getActionCommand().equals(LoginView.LOGIN_START_BUTTON_COMMAND)) { //LOGIN CHECK
            checkLogin();

            return;
        }

        //REGISTER HERE
        if (e.getActionCommand().equals(LoginView.LOGIN_REGISTER_BUTTON_COMMAND)) { //GO TO REGISTER VIEW
            masterView.changeView(MasterView.REGISTER_VIEW);
            registerView.setJtextFieldEmpty();
            registerView.updateUI();
        }

        //REGISTER------------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(RegisterView.REGISTER_BACK_BUTTON_COMMAND)) { //BACK TO START VIEW
            masterView.changeView(MasterView.START_VIEW);

            return;
        }

        if (e.getActionCommand().equals(RegisterView.REGISTER_START_BUTTON_COMMAND)) { //CHECK REGISTER
            checkRegister();

            return;
        }

        if (e.getActionCommand().equals(RegisterView.REGISTER_LOGIN_BUTTON_COMMAND)) {//BACK TO LOGIN VIEW
            System.out.println("PlayerController > ActionEvent: Register, Back button pressed, heading back to Login");
            masterView.changeView(MasterView.LOGIN_VIEW);
        }

        //PLAYER_EVOLUTION----------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(PlayerEvolutionView.PLAYER_EVOLUTION_BACK_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.ACCOUNT_VIEW);
        }

        //ACCOUNT-------------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(AccountView.ACCOUNT_BACK)) {
            //RESET SOME VARIABLES after login out
            gamesController = null;
            userName = null;
            //tornem a la pestanya per iniciar sessió
            loginView.setJtextFieldEmpty();
            loginView.updateUI();
            masterView.changeView(MasterView.LOGIN_VIEW);
        } else if (e.getActionCommand().equals(AccountView.ACCOUNT_CONFIG)) {
            masterView.changeView(MasterView.SETTINGS_VIEW);
        } else if (e.getActionCommand().equals(AccountView.ACCOUNT_GAMES)) {
            masterView.changeView(MasterView.GAMES_MANAGER_VIEW);
        } else if (e.getActionCommand().equals(AccountView.ACCOUNT_STATS)) {
            if(playerEvolutionView.nullWins(playersManager.getWinsValue(userName))){
                isWarningActive = true;
                warningView.updateWarning(EMPTY_GRAPH);
                warningView.showWindow(true);

                if(e.getActionCommand().equals(WarningView.WARNING_ACCEPT)) {
                    masterView.changeView(MasterView.ACCOUNT_VIEW);
                }
            }else {
                playerEvolutionView.updateGraph(playersManager.getWinsValue(userName));
                playerEvolutionView.updateUI();
                masterView.changeView(MasterView.PLAYER_EVOLUTION_VIEW);
                masterView.changeView(MasterView.PLAYER_EVOLUTION_VIEW);
            }
        }

        //SETTINGS------------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(SettingsView.SETTINGS_BACK_BUTTON_COMMAND)) {
            masterView.changeView(MasterView.ACCOUNT_VIEW);
        } else if (e.getActionCommand().equals(SettingsView.SETTINGS_DELETE_BUTTON_COMMAND)) {
            confirmationViewPlayers.updateConfirmationPlayer(userName);
            confirmationViewPlayers.updateUI();
            masterView.changeView(MasterView.CONFIRMATION_VIEW_PLAYERS);


        }

        //WARNING_CLOSE-------------------------------------------------------------------------------------------------
        if(e.getActionCommand().equals(WarningView.WARNING_ACCEPT)){
            warningView.dispose();
            isWarningActive = false;
        }

        //CONFIRMATION--------------------------------------------------------------------------------------------------
        if (e.getActionCommand().equals(ConfirmationView.YES_BUTTON_PLAYERS)) {
            System.out.println("PlayerController > Confirmation to delete player, delete confirmed");
            boolean deleted = playersManager.deletePlayer(userName);

            //Info jugador es true de memòria
            gamesController = null;
            userName = null;
            masterView.changeView(MasterView.START_VIEW);
        } else if(e.getActionCommand().equals(ConfirmationView.NO_BUTTON_PLAYERS)){
            System.out.println("PlayerController > Confirmation to delete player, canceled");
            masterView.changeView(MasterView.SETTINGS_VIEW);
        }
    }

    //Mètodes de verificació--------------------------------------------------------------------------------------------
    /**
     * Method that verifies if the register has been completed successfully
     */
    private void checkRegister () {
        if (checkEmailFormat(registerView.getEmail())){ // Email té el format correcte
            if (checkPasswordFormat(registerView.getPassword())) { //La contrassenya també té el format correcte
                if (registerView.getPassword().equals(registerView.getConfirmedPassword())) { //La contrassenya és igual a la confirmació de contrassenya

                    if (registerView.getUserName().contains("'") || registerView.getUserName().contains("=")) {

                        if (warningView == null) return;
                        isWarningActive = true;
                        warningView.updateWarning(USER_EQUAL_COMILLA);
                        warningView.showWindow(true);

                        return;
                    }

                    Boolean checkUserName = playersManager.checkUserNamePlayer(registerView.getUserName());
                    if (checkUserName == null){

                        if (warningView == null) return;
                        isWarningActive = true;
                        warningView.updateWarning(USER_NAME_NULL);
                        warningView.showWindow(true);

                        return;
                    }

                    if (checkUserName) { //ja existeix el nom d'usuari i per tant no el volem ficar
                        //ERROR USERNAME EXISTS
                        if (warningView == null) return;
                        isWarningActive = true;
                        warningView.updateWarning(USER_NAME_ALREADY_EXISTS);
                        warningView.showWindow(true);

                        return;
                    }

                    if (playersManager.checkEmailPlayer(registerView.getEmail())) { //No existeix el nom d'usuari però si el email, i per tant no el volem ficar
                        //ERROR EMAIL EXISTS
                        if (warningView == null) return;
                        isWarningActive = true;
                        warningView.updateWarning(EMAIL_ALREADY_EXISTS);
                        warningView.showWindow(true);

                        return;
                    }

                    if (registerView.getUserName() != null && registerView.getPassword() != null && registerView.getEmail() != null) { // TOT CORRECTE
                        userName = registerView.getUserName();
                        playersManager.createPlayer(userName, registerView.getEmail(), registerView.getPassword());
                        //Entra a la seva conta
                        gamesController = new GamesController(masterView, userName);
                        //mostrem la seva conta
                        masterView.changeView(MasterView.ACCOUNT_VIEW);
                        System.out.println("PlayerController > Register completed!");

                    }
                } else { //La contrassenya introduida és diferent a la confirmació de contrassenya introduida
                    //ERROR PASSWORD MISMATCH
                    if (warningView == null) return;
                    isWarningActive = true;
                    warningView.updateWarning(PASSWORD_CONFIRMATION_ERROR);
                    warningView.showWindow(true);
                }
            } else { // La contrassenya té un format incorrecte
                //ERROR PASSWORD INCORRECT FORMAT
                if (warningView == null) return;
                isWarningActive = true;
                warningView.updateWarning(PASSWORD_FORMAT_ERROR);
                warningView.showWindow(true);
            }
        } else { // El mail té un format incorrecte
            //ERROR INCORRECT EMAIL FORMAT
            if (warningView == null) return;
            isWarningActive = true;
            warningView.updateWarning(EMAIL_FORMAT_ERROR);
            warningView.showWindow(true);
        }
    }

    /**
     * Method that verifies if the login has been completed successfully
     */
    private void checkLogin () {

        if (loginView.getUserName().contains("'") || loginView.getPassword().contains("'"))  {

            if (warningView == null) return;

            isWarningActive = true;
            warningView.updateWarning(USER_EQUAL_COMILLA);
            warningView.showWindow(true);
            return;
        }

        Boolean checkUserName = playersManager.checkUserNamePlayer(loginView.getUserName());
        if (checkUserName != null) {


            if (checkUserName) {//CHECK BBDD userName
                if (playersManager.checkPasswordFromPlayer(loginView.getUserName(), loginView.getPassword())) { //check BBDD Password
                    userName = loginView.getUserName();
                    //Entra a la seva conta
                    gamesController = new GamesController(masterView, userName);
                    System.out.println("PlayerController > Login sucessful!");

                    //mostrem la seva conta
                    masterView.changeView(MasterView.ACCOUNT_VIEW);
                } else {
                    //ERROR WRONG PASSWORD
                    if (warningView == null) return;

                    isWarningActive = true;
                    warningView.updateWarning(LOGIN_ERROR);
                    warningView.showWindow(true);
                }
            } else {
                //ERROR WRONG USERNAME
                if (warningView == null) return;

                isWarningActive = true;
                warningView.updateWarning(LOGIN_ERROR);
                warningView.showWindow(true);
            }

        }else {
            //ERROR empty username
            if (warningView == null) return;

            isWarningActive = true;
            warningView.updateWarning(LOGIN_ERROR);
            warningView.showWindow(true);
        }
    }

    /**
     * Method that checks if the mail format is correct
     * @param email The email we want to check
     * @return true if the mail format is correct
     *         false if the mail is incorrect
     */
    private boolean checkEmailFormat (String email) {
        boolean format = false;
        int total_arrova = 0;

        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@'){
                total_arrova++;
            }
        }

        if (total_arrova == 1 && !email.contains(" ") && !email.contains("'")) {
            String[] dividedMail = email.split("@");
            if (dividedMail[1].contains(".")) {
                format = true;
            }
        }

        return format;
    }

    /**
     * Method that checks if the password format is correct
     *      * @param email The email we want to check
     *      * @return true if the mail format is correct
     *      *         false if the mail is incorrect
     */
    public boolean checkPasswordFormat (String password) {
        int mayus = 0;
        int min = 0;

        if (password.length() < 8) {
            //System.out.println("Must be 8 in length");

            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            //System.out.println("Must contain numbers");

            return false;
        }

        if (password.contains("'")) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            if(Character.isUpperCase(password.charAt(i))){
                mayus++;
            }

            if(Character.isLowerCase(password.charAt(i))){
                min++;
            }
        }

        if(mayus < 1 || min < 1){
            //System.out.println("Must contain uppercase and lowercase letters");

            return false;
        }

        return true;
    }

    /**
     * Method that listens for KeyTyped events
     * It is currently unused
     * @param e the event to be processed
     */
    @Override
    public void keyTyped (KeyEvent e) {

    }

    /**
     * Method that listens for KeyPressed events.
     * It is used for enable the ability to press ENTER key to login or do other actions
     * @param e the event to be processed
     */
    @Override
    public void keyPressed (KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER){
            if (isWarningActive) {
                warningView.dispose();
                isWarningActive = false;

                return;
            }

            if (masterView.getCurrentView().equals(MasterView.LOGIN_VIEW)){
                checkLogin();

                return;
            }

            if (masterView.getCurrentView().equals(MasterView.REGISTER_VIEW)){
                checkRegister();
            }
        }
    }

    /**
     * Method that listens for KeyReleased events
     * It is currently unused
     * @param e the event to be processed
     */
    @Override
    public void keyReleased (KeyEvent e) {

    }
}
