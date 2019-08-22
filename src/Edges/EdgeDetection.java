package Edges;

import javax.swing.*;
import java.awt.*;

public class EdgeDetection {
    public static void main (String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of args.");
        }
        String filename = args[0];
        int limit = Integer.parseInt(args[1]);
        JLabel img1 = SimpleEdgeDetection.run(filename, limit);
        JLabel img2 = SobelEdgeDetection.run(filename, limit);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout(1, 1));
      //  mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(img1, BorderLayout.NORTH);
        mainPanel.add(img2, BorderLayout.SOUTH);

        JScrollPane jScrollPane = new JScrollPane(mainPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(jScrollPane);


        frame.getContentPane().add(mainPanel);
        frame.setSize(500, 800);
        frame.setVisible(true);
    }
}