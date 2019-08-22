package Edges;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SimpleEdgeDetection {
    public static JLabel run(String filename, int limit) {
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
                    int diff1 =  Math.abs(color1.getBlue() + color1.getGreen() + color1.getRed() + color1.getAlpha()
                            - color2.getGreen() -color2.getBlue() - color2.getRed() - color2.getAlpha());
                    // Compare the current pixel's color intensity to the pixel below.
                    int diff2 =  Math.abs(color1.getBlue() + color1.getGreen() + color1.getRed() + color1.getAlpha()
                            - color3.getGreen() -color3.getBlue() - color3.getRed() - color3.getAlpha());
                    if (diff1 > limit || diff2 > limit) outputImg.setRGB(i, j, white);
                    else outputImg.setRGB(i, j, black);
                }
            }
        }

        catch(IOException e){
            System.out.println(e);
        }
        Image resizedImg = resize(outputImg);
        return new JLabel(new ImageIcon(resizedImg));
    }

    private static Image resize(BufferedImage img) {
        if (img.getHeight() > 300 || img.getWidth() > 600) {
            return img.getScaledInstance(img.getWidth()/2, img.getHeight()/2, Image.SCALE_SMOOTH);
        }
        return img;
    }
}

