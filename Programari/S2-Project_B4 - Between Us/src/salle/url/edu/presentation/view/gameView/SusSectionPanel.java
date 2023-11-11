package salle.url.edu.presentation.view.gameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SusSectionPanel that extends JPanel
 * Shows each section: SUS, NOT SUS, UNKNOWN
 */
public class SusSectionPanel extends JPanel {
   private JLabel name;
   private JPanel container;
   private ArrayList<SusCardPanel> sectionList;

    /**
     * Constructor of the class
     * @param name of type string contains tha name of the player
     */
   public SusSectionPanel (String name) {
       setLayout(new BorderLayout(0,0));
       setOpaque(false);
       setBackground(new Color(0,0,0));

       this.name = new JLabel(name);
       this.name.setFont(GameView.aclonica60);
       this.name.setOpaque(true);
       this.name.setBackground(new Color(200,200,200));
       this.name.setHorizontalAlignment(JLabel.CENTER);
       this.name.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

       container = new JPanel();
       container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

       container.setOpaque(true);
       container.setBackground(new Color(50,50,50,90));

       setPreferredSize(new Dimension(500,80));
       setMinimumSize(new Dimension(500,150));
       setMaximumSize(new Dimension(500,600));
       sectionList = new ArrayList<>();

       add(this.name, BorderLayout.NORTH);

       for (SusCardPanel panel: sectionList ) {
           this.container.add(panel, CENTER_ALIGNMENT);
       }

       add(this.container, BorderLayout.CENTER);
   }

    /**
     * Methdo that adds a character to a diferent section of the panel
     * @param color of type Color is the color of the moved character
     * @param name of type string that contains the name of the character
     */
   public void addSuspicious (Color color, String name) {
       try {
           sectionList.add(new SusCardPanel(color, name));
       } catch (IOException e) {
           e.printStackTrace();
       }

       updateView();
   }

    /**
     * Method that resets the roles of the card panel
     */
   public void resetSuspitious () {
       sectionList.clear();
       container.removeAll();
   }

    /**
     * Method that updates the view of the sus card panel
     */
   private void updateView () {
       for (SusCardPanel panel: sectionList ) {
           this.container.add(panel);
       }

       add(this.container, BorderLayout.CENTER);
       setPreferredSize(new Dimension(500, sectionList.size()*60+ 90));
   }

    /**
     * Method that registers the action listeners of the view
     * @param controller of type ActionListener contains the action listeners that happened
     */
    public void registerController (ActionListener controller) {
        for (SusCardPanel panel: sectionList) {
            panel.registerController(controller);
        }
    }
}
