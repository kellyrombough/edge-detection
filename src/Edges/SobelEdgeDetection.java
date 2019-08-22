package Edges;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SobelEdgeDetection {
    public static JLabel run(String filename, int limit) {
        int[][] xMask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] yMask = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        Color[][] pixels = new Color[3][3];
        BufferedImage img = null;
        BufferedImage outputImg = null;
        File f = null;
        int black=new Color(0,0,0).getRGB();
        int white = new Color(255, 255, 255).getRGB();

        try{
            f = new File(filename);
            img = ImageIO.read(f);
            int width = img.getWidth();
            int height = img.getHeight();
            outputImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

            for (int i = 1; i<width-1; i++) {
                for (int j=1; j<height-1; j++) {
                    pixels[0][0] = new Color(img.getRGB(i-1, j-1));
                    pixels[0][1] = new Color(img.getRGB(i, j-1));
                    pixels[0][2] = new Color(img.getRGB(i+1, j-1));
                    pixels[1][0] = new Color(img.getRGB(i-1, j));
                    pixels[1][1] = new Color(img.getRGB(i, j));
                    pixels[1][2] = new Color(img.getRGB(i+1, j));
                    pixels[2][0] = new Color(img.getRGB(i-1, j+1));
                    pixels[2][1] = new Color(img.getRGB(i, j+1));
                    pixels[2][2] = new Color(img.getRGB(i+1, j+1));

                    int xGradient = 0;
                    int yGradient = 0;
                    for (int a=0; a<3; a++) {
                        for (int k=0; k<3; k++) {
                            xGradient += (xMask[a][k] * getIntensity(pixels[a][k]));
                            yGradient += (yMask[a][k] * getIntensity(pixels[a][k]));
                        }
                    }
                    if (Math.sqrt(Math.pow(xGradient, 2) + Math.pow(yGradient, 2)) > limit*4) {
                        outputImg.setRGB(i, j, white);
                    }
                    else {
                        outputImg.setRGB(i, j, black);
                    }
                }
            }
        }

        catch(IOException e){
            System.out.println(e);
        }
        Image resizedImg = resize(outputImg);
        return new JLabel(new ImageIcon(resizedImg));
    }

    private static int getIntensity(Color color) {
        return color.getRed() + color.getBlue() + color.getGreen() + color.getAlpha();
    }
    private static Image resize(BufferedImage img) {
        if (img.getHeight() > 100 || img.getWidth() > 600) {
            return img.getScaledInstance(img.getWidth()/2, img.getHeight()/2, Image.SCALE_SMOOTH);
        }
        return img;
    }
}
