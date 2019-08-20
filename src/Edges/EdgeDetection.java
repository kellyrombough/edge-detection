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

        //read image
        try{
          f = new File("src/Edges/shapes.jpeg");
          img = ImageIO.read(f);
          int width = img.getWidth();
          int height = img.getHeight();

          for (int i = 0; i<width-1; i++) {
              for (int j=0; j<height-1; j++) {
                  Color color1 = new Color(img.getRGB(i, j));
                  Color color2 = new Color(img.getRGB(i+1, j));
                  int black=new Color(0,0,0).getRGB();
                  int white = new Color(255, 255, 255).getRGB();
                  if (color1.getBlue() != 255 ^ color2.getBlue() != 255) img.setRGB(i, j, black);
                  else img.setRGB(i, j, white);
              }
          }
          ImageIO.write(img, "jpg", new File("image.jpg"));
        }

        catch(IOException e){
            System.out.println(e);
        }
        SimpleEdgeDetection.run();
    }
}