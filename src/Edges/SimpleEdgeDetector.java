package Edges;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SimpleEdgeDetector {

    private String filename;
    private int limit;

    public SimpleEdgeDetector(String filename, int limit) {
        this.filename = filename;
        this.limit = limit;
    }

    public JLabel detectEdges() {
        BufferedImage img = null;
        BufferedImage outputImg = null;
        File f;

        try{
            f = new File(filename);
            img = ImageIO.read(f);
            int width = img.getWidth();
            int height = img.getHeight();
            outputImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i<width-1; i++) {
                for (int j=0; j<height-1; j++) {
                    Color color1 = new Color(img.getRGB(i, j));
                    Color color2 = new Color(img.getRGB(i+1, j));
                    Color color3 = new Color(img.getRGB(i, j+1));
                    int black=new Color(0,0,0).getRGB();
                    int white = new Color(255, 255, 255).getRGB();
                    // Compare the current pixel's color intensity to the pixel to the right.
                    int diff1 =  Math.abs(EdgeDetection.getIntensity(color1) - EdgeDetection.getIntensity(color2));
                    // Compare the current pixel's color intensity to the pixel below.
                    int diff2 =  Math.abs(EdgeDetection.getIntensity(color1) - EdgeDetection.getIntensity(color3));
                    if (Math.sqrt(Math.pow(diff1, 2)  + Math.pow(diff2, 2)) > limit) outputImg.setRGB(i, j, white);
                    else outputImg.setRGB(i, j, black);
                }
            }
        }

        catch(IOException e){
            System.out.println(e);
        }
        Image resizedImg = EdgeDetection.resize(outputImg);
        return new JLabel(new ImageIcon(resizedImg));
    }
}

