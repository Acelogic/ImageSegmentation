import ij.ImagePlus;
import ij.process.ColorProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage buff = ImageIO.read(new File("pepetest.jpg"));



        ArrayList<PixelVertex> arr = new ArrayList<>();
        for (int y = 0; y < buff.getHeight(); y++) {
            for (int x = 0; x < buff.getWidth() ; x++) {
                arr.add(new PixelVertex(x,y, buff));
            }
        }



        int numOfEdges = 0;
        for (PixelVertex p : arr) {
            numOfEdges =+ p.getEdgeList().size();
        }

        System.out.println("Number of Edges Calulated:" + numOfEdges);
       // Graph g = new Graph(imgPlus.getBufferedImage());

    }
}
