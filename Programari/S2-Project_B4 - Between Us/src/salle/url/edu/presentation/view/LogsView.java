package salle.url.edu.presentation.view;

import salle.url.edu.business.*;
import salle.url.edu.business.Character;
import salle.url.edu.presentation.controller.CurrentGameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * LogsView class that extends JPanel
 * It is shown when a user in the middle of a game wants to consult it
 * Only showed in specific rooms of the map
 */
public class LogsView extends JPanel {
    public static final String LOGS_EXIT_BUTTON_COMMAND = "l_back_command";

    //ATRIBUTS REGISTER
    private final JPanel principal;
    private JPanel rectangle;
    private JButton exitButton;
    private JTable table;

    protected ArrayList<String> values;

    /**
     * Constructor, initializes panels
     */
    public LogsView () {
        setSize(1920,1080);
        setLayout(new BorderLayout());

        principal = new JImagePanel("files/Assets/logsView/fondoTableros.png");
        principal.setLayout(new BorderLayout());

        JPanel north = drawNorth();
        JPanel center = drawCenter();

        //NORTH---------------------------------------------------------------------------------------------------------
        principal.add(north,BorderLayout.NORTH);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);

        table = new JTable();

        this.add(principal, BorderLayout.CENTER);
    }

    /**
     * Method that makes the tab visible according to the parameter
     * @param visible boolean that tells to show or not show the tab
     */
    public void showWindow (boolean visible) {setVisible(visible);}

    /**
     *  It simply draws all what is going to be in the panel and adds it to the view
     */
    public void drawAll () {
        JPanel north = drawNorth();
        JPanel center = drawCenter();

        //NORTH---------------------------------------------------------------------------------------------------------
        principal.add(north,BorderLayout.NORTH);

        //CENTER--------------------------------------------------------------------------------------------------------
        principal.add(center,BorderLayout.CENTER);
    }

    /**
     * It draws all what is inside the north of the tab
     * @return JPanel of what we need to put in the west of the border layout
     */
    public JPanel drawNorth () {
        JPanel north = new JPanel();
        north.setOpaque(false);

        north.setLayout(new BorderLayout());

        //EXIT__________________________________________________________________________________________________________
        try {
            ImageIcon backImg = new ImageIcon(ImageIO.read(new File("files/Assets/logsView/cruzExit.png")).getScaledInstance(100,100,0) );
            this.exitButton = new JButton(backImg);
            this.exitButton.setPreferredSize(new Dimension(100,100));
            this.exitButton.setBackground(Color.WHITE);
            this.exitButton.setOpaque(false);
            this.exitButton.setBorderPainted(false);
            north.add(this.exitButton, BorderLayout.EAST);
        } catch (IOException e) {
            e.printStackTrace();
        }

        north.setBorder(BorderFactory.createEmptyBorder(50,0,0,60));

        return north;
    }

    /**
     * It draws all what is inside the center of the tab
     * @return JPanel of what we need to put in the center of the border layout
     */
    public JPanel drawCenter () {
        rectangle = new JPanel(new BorderLayout());
        rectangle.setBorder(BorderFactory.createEmptyBorder(10,70,170,120));
        rectangle.setOpaque(false);

        //TABLE TITLES__________________________________________________________________________________________________
        JPanel titles = new JPanel(new BorderLayout());
        titles.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        titles.setOpaque(false);

        //CREWMATE______________________________________________________________________________________________________
        JLabel crewTitle = new JLabel("CREWMATE");
        crewTitle.setFont(new Font("Aclonica", Font.BOLD, 60));
        crewTitle.setForeground(Color.WHITE);
        crewTitle.setOpaque(false);
        crewTitle.setBorder(BorderFactory.createEmptyBorder(10,210,0,0));

        titles.add(crewTitle, BorderLayout.WEST);

        //ROOM__________________________________________________________________________________________________________
        JLabel roomTitle = new JLabel("ROOM");
        roomTitle.setFont(new Font("Aclonica", Font.BOLD, 60));
        roomTitle.setForeground(Color.WHITE);
        roomTitle.setOpaque(false);
        roomTitle.setBorder(BorderFactory.createEmptyBorder(10,215,0,0));

        titles.add(roomTitle, BorderLayout.CENTER);

        //INSTANT_______________________________________________________________________________________________________
        JLabel instantTitle = new JLabel("INSTANT");
        instantTitle.setFont(new Font("Aclonica", Font.BOLD, 60));
        instantTitle.setForeground(Color.WHITE);
        instantTitle.setOpaque(false);
        instantTitle.setBorder(BorderFactory.createEmptyBorder(10,170,0,170));

        titles.add(instantTitle, BorderLayout.EAST);

        rectangle.add(titles, BorderLayout.NORTH);

        return rectangle;
    }

    /**
     * Method that updates the logs table
     * @param characters of type ArrayList contains the characters playing the game
     * @param gameLogs pof type ArrayList contains the different game logs throughout the game
     */
    public void updateLogsView (ArrayList<Character> characters, ArrayList<GameLogs> gameLogs, CurrentGameController controller) {
        //TOTALROWS_________________________________________________________________________________________________________
        JPanel totalRows = new JPanel(new BorderLayout());
        totalRows.setOpaque(false);
        totalRows.setBorder(BorderFactory.createEmptyBorder(0,133,0,72));

        table = new JTable();
        table.setOpaque(false);

        CustomTableModel model = new CustomTableModel();

        int auxRow = 0;

        for (GameLogs gameLog: gameLogs) {
            String suspicionRole = "";

            for (int i = 0; i < characters.size(); i++) {
                if (characters.get(i) instanceof NPC && gameLogs.get(auxRow).getColorNPC().equals(characters.get(i).getColorString())) {
                    suspicionRole = ((NPC) characters.get(i)).getSuspicionRoleString();
                }
            }
            model.addData(auxRow, gameLog.getColorNPC(), suspicionRole ,gameLog.getRoomName(),gameLog.getInstant());

            auxRow++;
        }

        table.setModel(model);

        table.getTableHeader().setPreferredSize(new Dimension(0,0));
        table.setRowHeight(76);

        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);

        table.setFont(new Font("Aclonica", Font.BOLD, 40));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {


            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column)
            {
                CustomTableModel model = (CustomTableModel) table.getModel();

                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(model.getColorAt(row));
                return c;
            }
        });

        ((CustomTableModel)table.getModel()).fireTableDataChanged();
        JScrollPane scroll = new JScrollPane(table);//, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        table.setFocusable(true);
        scroll.setFocusable(true);
        scroll.setEnabled(true);
        table.setEnabled(true);

        totalRows.add(scroll);

        principal.removeAll();
        drawAll();
        rectangle.add(totalRows, BorderLayout.CENTER);

        registerController(controller);
    }

    /**
     * Method that connects the listeners with the controller
     * @param controller CurrentGameController
     */
    public void registerController (CurrentGameController controller) {
        if (exitButton.getActionListeners().length == 0) {
            this.exitButton.setActionCommand(LOGS_EXIT_BUTTON_COMMAND);
            this.exitButton.addActionListener(controller);
        }
        table.getSelectionModel().addListSelectionListener(controller);
    }

    /**
     * Gets the name of the npc from the specified row
     * @param row int
     * @return String
     */
    public String getNpcFromRow (int row) {
        String cellValue = (String) table.getModel().getValueAt(row, 0);
        if (cellValue == null) return null;
        return cellValue.substring(0,cellValue.indexOf(" "));
    }

    /**
     * Used after one row is selected to reset the table
     */
    public void updateAfterEvent () {
        updateUI();
        CustomTableModel model = ((CustomTableModel)table.getModel());
        model.refresh();
    }

    /**
     * Inner class CustomTableModel extends AbstractTableModel
     * It is used for the JTable, implements its model and defines the structure of the Table
     * Also has some custom methods to improve the Jtable
     */
    static class CustomTableModel extends AbstractTableModel {
        private String[] columnNames = {"NPC", "Room", "Instant"};
        private Vector<Object[]> data = new Vector<>();
        private ArrayList<Color> rowColors = new ArrayList<>();

        /**
         * Adds a row to the data
         * @param row int row where you add the data
         * @param npcName String of the npcName
         * @param suspicious String
         * @param roomName String
         * @param instant int time in seconds
         */
        public void addData (int row, String npcName, String suspicious, String roomName, int instant) {
            data.add(new Object[]{npcName + " (" + suspicious + ")", roomName, Integer.toString(instant)});
            rowColors.add(row, CharacterColors.getCharacterColorFromString(npcName).getColor());
            fireTableDataChanged();
        }

        /**
         * Gets the number of columns
         * @return int
         */
        public int getColumnCount () {
            return columnNames.length;
        }

        /**
         * Get the number of rows
         * @return int
         */
        public int getRowCount () {
            return data.size();
        }

        /**
         * Gets the color of a row
         * @param row int
         * @return Color
         */
        public Color getColorAt (int row) {
            return rowColors.get(row);
        }

        /**
         * Gets the name of the columns
         * @param col  the column being queried
         * @return String
         */
        public String getColumnName (int col) {
            return columnNames[col];
        }

        /**
         * Gets the value at a specified position
         * @param row        the row whose value is to be queried
         * @param col     the column whose value is to be queried
         * @return Objet
         */
        public Object getValueAt (int row, int col) {
            if (data.size() == 0) return null;
            fireTableCellUpdated(row, col);

            return data.get(row)[col];
        }

        /**
         * Methods that disable the ability to edit cells
         * @param row  the row being queried
         * @param col the column being queried
         * @return false
         */
        public boolean isCellEditable (int row, int col) {
            return false;
        }

        /**
         * Sets the value of a cell in a specific position
         * @param value   value to assign to cell
         * @param row   row of cell
         * @param col  column of cell
         */
        public void setValueAt (Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }

        /**
         * Resets the model
         */
        public void refresh () {
            data.clear();
            rowColors.clear();
            fireTableDataChanged();
        }
    }
}

