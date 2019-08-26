package Edges;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetection {
    public static void main (String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of args.");
        }
        String filename = args[0];
        int limit = Integer.parseInt(args[1]);
        SimpleEdgeDetector simple = new SimpleEdgeDetector(filename, limit);
        SobelEdgeDetector sobel = new SobelEdgeDetector(filename, limit);
        JLabel img1 = simple.detectEdges();
        JLabel img2 = sobel.detectEdges();
        JLabel img3 = sobel.blurAndRun();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(img1);
        mainPanel.add(img2);
        mainPanel.add(img3);
        frame.add(mainPanel);
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    protected static Image resize(BufferedImage img) {
        if (img.getHeight() > 100 || img.getWidth() > 600) {
            return img.getScaledInstance(img.getWidth()/2, img.getHeight()/2, Image.SCALE_SMOOTH);
        }
        return img;
    }

    protected static int getIntensity(Color color) {
        return (color.getRed() + color.getBlue() + color.getGreen() + color.getAlpha())/4;
    }
}