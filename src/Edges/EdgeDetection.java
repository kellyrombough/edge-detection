package Edges;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

public class EdgeDetection {
    public static void main (String[] args) {
        BufferedImage img = null;
        File f = null;

        try{
          f = new File("src/Edges/skyline.jpg");
          img = ImageIO.read(f);
          int width = img.getWidth();
          int height = img.getHeight();

          for (int i = 0; i<width-1; i++) {
              for (int j=0; j<height-1; j++) {
                  Color color1 = new Color(img.getRGB(i, j));
                  Color color2 = new Color(img.getRGB(i+1, j));
                  Color color3 = new Color(img.getRGB(i, j+1));
                  int black=new Color(0,0,0).getRGB();
                  int white = new Color(255, 255, 255).getRGB();
                  int diff1 =  Math.abs(color1.getBlue() + color1.getGreen() + color1.getRed() + color1.getAlpha()
                          - color2.getGreen() -color2.getBlue() - color2.getRed() - color2.getAlpha());
                  int diff2 =  Math.abs(color1.getBlue() + color1.getGreen() + color1.getRed() + color1.getAlpha()
                          - color3.getGreen() -color3.getBlue() - color3.getRed() - color3.getAlpha());
                  if (diff1 > 30 && diff2 > 30) img.setRGB(i, j, white);
                  else img.setRGB(i, j, black);
              }
          }
          ImageIO.write(img, "jpg", new File("image10.jpg"));
        }

        catch(IOException e){
            System.out.println(e);
        }
        SimpleEdgeDetection.run();
    }
}