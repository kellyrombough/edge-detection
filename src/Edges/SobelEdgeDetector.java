package Edges;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SobelEdgeDetector {

    private String filename;
    private int limit;

    public SobelEdgeDetector(String filename, int limit) {
        this.filename = filename;
        this.limit = limit;
    }

    // This method applies a Gaussian blur filter to limit noise, then calls the Sobel edge detection method.
    public JLabel blurAndRun() {
        float[][] gaussianDist = {{0.0625f, 0.125f, 0.0625f}, {0.125f, 0.25f, 0.125f}, {0.0625f, 0.125f, 0.0625f}};
        Color[][] pixels = new Color[3][3];
        BufferedImage img = null;
        BufferedImage outputImg = null;
        File f = null;

        try{
            f = new File(filename);
            img = ImageIO.read(f);
            int width = img.getWidth();
            int height = img.getHeight();
            outputImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

            for (int i = 1; i<width-1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    pixels[0][0] = new Color(img.getRGB(i - 1, j - 1));
                    pixels[0][1] = new Color(img.getRGB(i, j - 1));
                    pixels[0][2] = new Color(img.getRGB(i + 1, j - 1));
                    pixels[1][0] = new Color(img.getRGB(i - 1, j));
                    pixels[1][1] = new Color(img.getRGB(i, j));
                    pixels[1][2] = new Color(img.getRGB(i + 1, j));
                    pixels[2][0] = new Color(img.getRGB(i - 1, j + 1));
                    pixels[2][1] = new Color(img.getRGB(i, j + 1));
                    pixels[2][2] = new Color(img.getRGB(i + 1, j + 1));

                    int red = 0;
                    int green = 0;
                    int blue = 0;
                    for (int a = 0; a < 3; a++) {
                        for (int k = 0; k < 3; k++) {
                            red += (pixels[a][k].getRed()) * gaussianDist[a][k];
                            green +=(pixels[a][k].getGreen()) * gaussianDist[a][k];
                            blue += (pixels[a][k].getBlue()) * gaussianDist[a][k];
                        }
                    }
                    outputImg.setRGB(i, j, new Color(red, green, blue).getRGB());
                }
            }
            ImageIO.write(outputImg, "jpg", new File("output.jpg"));
        }

        catch(IOException e){
            System.out.println(e);
        }
        filename = "output.jpg";
        return detectEdges();
    }

    /*
      This is an implementation of the Sobel edge detection method. It decides whether or not a pixel is on an edge
      by approximating the x and y gradient at each pixel.
     */
    public JLabel detectEdges() {
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
                            xGradient += (xMask[a][k] * EdgeDetection.getIntensity(pixels[a][k]));
                            yGradient += (yMask[a][k] * EdgeDetection.getIntensity(pixels[a][k]));
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
        Image resizedImg = EdgeDetection.resize(outputImg);
        return new JLabel(new ImageIcon(resizedImg));
    }
}
