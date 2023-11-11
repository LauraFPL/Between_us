package salle.url.edu.presentation.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * GraphDrawer class that extends JPanel
 * Draws the player evolution graph
 */

class GraphDrawer extends JPanel {
    private int startX = 120;
    private int startY = 50;
    private int endX = 800;
    private int endY = 500;
    private int unitX;
    private int unitY;
    private int prevX = startX;
    private int prevY = endY;
    private ArrayList<Boolean> numWins = new ArrayList<>();
    private ArrayList<Boolean> winsInfo = new ArrayList<>();
    private double percentage;
    private int x = 0;

    /**
     * Constructor of the class
     * @param numWins integer with the player's wins
     */
    public GraphDrawer (ArrayList<Boolean> numWins) {
        this.winsInfo = numWins;
        booleanToInt();
    }

    /**
     * Method that transforms true or false value for the win to 1 or 0
     * @return winsXGame
     */
    public ArrayList<Integer> booleanToInt () {
        ArrayList<Integer> winsXGame = new ArrayList<>();
        for (int i = 0; i < winsInfo.size(); i++) {
            if (winsInfo.get(i)) {
                winsXGame.add(1);
            } else {
                winsXGame.add(0);
            }
        }

        return winsXGame;
    }

    /**
     * Method that calculate the percentage depending on the number of games played
     **/
    public void calculatePercentage () {
        float sumaWins = 0;

        for (int i  = 0; i < booleanToInt().size(); i++) {
            sumaWins = sumaWins + booleanToInt().get(i);
        }
    }

    /**
     * Method that calculate the max percentage of wins the player has had
     **/
    public int calculateMaxPercentage () {
        int suma = 0;
        int maxPercent = 0;

        for (int i  = 0; i < booleanToInt().size(); i++) {
            suma = suma + booleanToInt().get(i);
            if (maxPercent < (100*suma/(i+1))) {
                maxPercent = 100*suma/(i+1);
            }
        }

        return maxPercent;
    }

    /**
     * Method that draws the graph with its axis and the corresponding function
     * @param g of type Graphics
     */
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (booleanToInt().size() != 0) {
            unitY = (endY - startY) / 10;
            unitX = (endX - startX) / booleanToInt().size();

            g2d.setColor(Color.WHITE);

            for (int i = startX; i <= endX; i += unitX * 2) {
                g2d.drawLine(i, startY, i, endY);
            }

            for (int i = startY; i <= endY; i += unitY) {
                g2d.drawLine(startX, i, endX, i);
            }

            g2d.setColor(Color.WHITE);
            g2d.drawLine(startX, startY, startX, endY);
            g2d.drawLine(startX, endY, endX, endY);
            g2d.setColor(Color.BLACK);

            float sumaWins = 0;
            int m = 100;

            for (int i = 0; i <= 10; i++) {
                String xLabel = m + "%";
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth2 = metrics.stringWidth(xLabel);
                g2d.setFont(new Font("Aclonica", Font.PLAIN, 15));
                if (i == 0) {
                    g2d.drawString(xLabel, startX - 50, startY);
                } else {
                    g2d.drawString(xLabel, startX - 50, startY += unitY);
                }

                m = m - 10;
            }

            for (int i = 0; i < booleanToInt().size(); i++) {
                sumaWins = sumaWins + booleanToInt().get(i);
                percentage = (float) (sumaWins / (i + 1)) * 10;
                System.out.println(percentage);
                String yLabel = i + "";
                FontMetrics metrics = g2d.getFontMetrics();

                int labelWidth = metrics.stringWidth(yLabel);

                g2d.setFont(new Font("Aclonica", Font.PLAIN, 15));
                x = booleanToInt().size() / 5;

                if (booleanToInt().size() < 5) {
                    g2d.drawString(yLabel, prevX, endY + 30);
                } else {
                    if (i % x == 0) {
                        g2d.drawString(yLabel, prevX, endY + 30);
                    }
                }

                System.out.println("la i :" + i);
                g2d.drawLine(prevX, prevY, prevX = prevX + (unitX), prevY = endY - (unitY * ((int) (percentage))));
            }
        }
    }
}
