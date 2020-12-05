import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PartialCredit {
    public PartialCredit() throws IOException {
        BufferedImage buff = getImageFromPath();
        for (int i = 0; i < buff.getWidth(); i++) {
            for (int j = 0; j < buff.getHeight() / 2; j++) {
                buff.setRGB(i, j, new Color(0, 0, 0, 127).getRGB());
            }
        }
        ImagePlus imgPlus = new ImagePlus("Split Image Partial Cred", buff);
        imgPlus.show();

    }

    private BufferedImage getImageFromPath() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG,JPEG,PNG,", "jpg", "png", "jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        String path = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
        }
        File file = new File(path);
        return ImageIO.read(file);
    }

    public static void main(String[] args) throws IOException {
        new PartialCredit();


    }
}