import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PartialCredit {
    public PartialCredit() throws IOException {
        BufferedImage buff = ImageIO.read(new File("pepetest.jpg"));
        for (int i = 0; i < buff.getWidth(); i++) {
            for (int j = 0; j < buff.getHeight()/2; j++) {
                buff.setRGB(i, j, new Color(0, 0, 0, 127).getRGB());
            }
        }
        ImagePlus imgPlus = new ImagePlus("Test" , buff);
        imgPlus.show();

    }
    public static void main(String[] args) throws IOException {
        new PartialCredit();


    }
}