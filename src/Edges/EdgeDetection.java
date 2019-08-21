package Edges;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

public class EdgeDetection {
    public static void main (String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of args.");
        }
        String filename = args[0];
        int limit = Integer.parseInt(args[1]);
        SimpleEdgeDetection.run(filename, limit);
    }
}