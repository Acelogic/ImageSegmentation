
import ij.process.ColorProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedImage image = ImageIO.read(new File("test.jpg"));


        ArrayList<Edge> edgeList = new ArrayList<>();
        ColorProcessor c = new ColorProcessor(image);
/*
        for (int y = 0; y < image.getWidth(); y++) {
            for (int x = 0; x < image.getHeight(); x++) {

                Point2D.Double vertCordinates = new Point2D.Double(x, y);

                Color nodeAColor = c.getColor(x, y);

                if ((x < image.getWidth() - 1) && (y < image.getHeight() - 1)) {
                    Point2D.Double upperRightNeighbor = new Point2D.Double(x + 1, y + 1);
                    edgeList.add(new Edge(vertCordinates, upperRightNeighbor, nodeAColor, c.getColor(x + 1, y + 1)));

                }
                if ((x < image.getWidth() - 1) && (y > 0)) {
                    Point2D.Double lowerLeftNeighbor = new Point2D.Double(x + 1, y - 1);
                    edgeList.add(new Edge(vertCordinates,lowerLeftNeighbor, nodeAColor, c.getColor(x + 1, y -1 )));
                }

            }
        }
*/

        for (int y = 0; y < image.getWidth(); y++) {
            for (int x = 0; x < image.getHeight(); x++) {
                edgeList.addAll(new PixelVertex(x, y, c).edgeList);
            }
        }

        System.out.println(edgeList.size());

        Collections.sort(edgeList);

        System.out.println();

    }
}
