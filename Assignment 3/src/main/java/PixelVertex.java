import ij.process.ColorProcessor;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class PixelVertex {


    private int x, y;
    private Color color;
    private Point2D.Double vertCordinates;
    private ColorProcessor proc;
    private static BufferedImage image;
    private ArrayList<Edge> edgeList;

    public PixelVertex(int x, int y, ColorProcessor proc) {
        this.x = x;
        this.y = y;
        this.vertCordinates = new Point2D.Double(x, y);
        this.proc = proc;
        if (image == null) {
            image = proc.getBufferedImage();
        }
        this.color = proc.getColor(x, y);
        edgeList = new ArrayList<>();
        determineNeighbors();
    }

    // Implementing N4 Neighborhood System
    private void determineNeighbors() {
        if (x < image.getWidth() - 1) {
            Point2D.Double rightNeighbor = new Point2D.Double(x + 1, y);
            edgeList.add(new Edge(this.vertCordinates, rightNeighbor, this.color, proc.getColor(x + 1, y), image));
        }
        if (y < image.getHeight() - 1) {
            Point2D.Double upperNeighbor = new Point2D.Double(x, y + 1);
            edgeList.add(new Edge(this.vertCordinates, upperNeighbor, this.color, proc.getColor(x, y + 1), image));
        }
        if ((x < image.getWidth() - 1) && (y < image.getHeight() - 1)) {
            Point2D.Double upperRightCornerNeighbor = new Point2D.Double(x + 1, y + 1);
            edgeList.add(new Edge(this.vertCordinates, upperRightCornerNeighbor, this.color, proc.getColor(x + 1, y + 1), image));
        }
        if ((x < image.getWidth() - 1) && (y > 0)) {
            Point2D.Double lowerRightCornerNeighbor = new Point2D.Double(x + 1, y - 1);
            edgeList.add(new Edge(this.vertCordinates, lowerRightCornerNeighbor, this.color, proc.getColor(x + 1, y - 1), image));
        }
    }


    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + " " + color.toString().substring(14);
    }
}
