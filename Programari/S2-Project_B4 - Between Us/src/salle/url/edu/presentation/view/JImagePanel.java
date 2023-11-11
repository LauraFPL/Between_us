package salle.url.edu.presentation.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * JImagePanel class that extends JPanel
 */
public class JImagePanel extends JPanel {
    private BufferedImage image;
    private int width;
    private int height;

    /**
     * Constructors of the class
     * @param width integer that saves the width
     * @param height integer that saves the height
     * @param image of type BufferedImage that contains the image
     */
    public JImagePanel (int width, int height, BufferedImage image) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        this.image = resize(image, this.width, this.height);
        setOpaque(false);
    }

    /**
     * Constructor of the class
     * @param path of type string that contains the path of an image
     */
    public JImagePanel (String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            // Not properly managed, sorry!
            e.printStackTrace();
            System.out.println("Error! You couldn't open: " + path);
        }
    }

    /**
     * Method that paints the wished component
     * @param g of type Graphics
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Getter that returns the image
     * @return image of type BufferedImage
     */
    public BufferedImage getImage () {
        return image;
    }

    /**
     * Method that resizes an image
     * @param img of type BufferedImage is the image that has to be resized
     * @param width of type int is the expected width of the resized image
     * @param height of type int is the expected height of the resized image
     * @return resized, is the image resized
     */
    private BufferedImage resize (BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
