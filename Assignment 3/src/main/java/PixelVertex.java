import ij.process.ColorProcessor;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class PixelVertex {


    private int x;
    private int y;
    private int redValue;
    private int blueValue;
    private int greenValue;
    private Color color;

    private Point2D.Double vertCordinates;

    private Point2D.Double upperNeighbor;
    private Point2D.Double lowerNeighbor;
    private Point2D.Double leftNeighbor;
    private Point2D.Double rightNeighbor;


    private Point2D.Double upperRightCornerNeighbor;
    private Point2D.Double lowerRightCornerNeighbor;
    private Point2D.Double upperLeftCornerNeighbor;
    private Point2D.Double lowerLeftCornerNeighbor;
     private ColorProcessor proc;

    BufferedImage image;
    LinkedList<Edge> edgeList;


    public PixelVertex(int x, int y,  ColorProcessor proc) {
        this.x = x;
        this.y = y;
        this.vertCordinates = new Point2D.Double(x, y);
        this.proc = proc;
        this.image = proc.getBufferedImage();
        this.color = new ColorProcessor(image).getColor(x, y);
        redValue = color.getRed();
        greenValue = color.getGreen();
        blueValue = color.getBlue();
        edgeList = new LinkedList<>();
        determineNeighbors();
    }

    // Implementing N8 Neighborhood System
    private void determineNeighbors() {
        boolean xPlusOneIsNotOutOfBounds = ((x + 1) > 0 && (x + 1) <= image.getWidth() - 1);
        boolean xMinusOneIsNotOutOfBounds = ((x - 1) >= 0 && (x - 1) <= image.getWidth() - 1);

        boolean yPlusOneIsNotOutOfBounds = ((y + 1) > 0 && (y + 1) <= image.getHeight() - 1);
        boolean yMinusOneIsNotOutOfBounds = ((y - 1) >= 0 && (y - 1) <= image.getHeight() - 1);


        if (xPlusOneIsNotOutOfBounds) {
            rightNeighbor = new Point2D.Double(x + 1, y);
            edgeList.add(new Edge(this.vertCordinates, rightNeighbor, this.color, proc.getColor(x+1, y)));

        }


        if (xMinusOneIsNotOutOfBounds) {
            leftNeighbor = new Point2D.Double(x - 1, y);
            edgeList.add(new Edge(this.vertCordinates, leftNeighbor, this.color, proc.getColor(x-1, y)));
        }


        if (yPlusOneIsNotOutOfBounds) {
            upperNeighbor = new Point2D.Double(x, y + 1);
            edgeList.add(new Edge(this.vertCordinates, upperNeighbor, this.color, proc.getColor(x, y+1)));
        }


        if (yMinusOneIsNotOutOfBounds) {
            lowerNeighbor = new Point2D.Double(x, y - 1);
            edgeList.add(new Edge(this.vertCordinates, lowerNeighbor, this.color,proc.getColor(x, y-1)));

        }

        if ((xMinusOneIsNotOutOfBounds) && (yPlusOneIsNotOutOfBounds)) {
            upperLeftCornerNeighbor = new Point2D.Double(x - 1, y + 1);
            edgeList.add(new Edge(this.vertCordinates, lowerNeighbor, this.color,proc.getColor(x-1, y+1)));

        }


        if ((xPlusOneIsNotOutOfBounds) && (yPlusOneIsNotOutOfBounds)) {
            upperRightCornerNeighbor = new Point2D.Double(x + 1, y + 1);
            edgeList.add(new Edge(this.vertCordinates, lowerNeighbor, this.color,proc.getColor(x+1, y+1)));

        }


        if ((xMinusOneIsNotOutOfBounds) && (yMinusOneIsNotOutOfBounds)) {
            lowerLeftCornerNeighbor = new Point2D.Double(x - 1, y - 1);
            edgeList.add(new Edge(this.vertCordinates, lowerNeighbor, this.color,proc.getColor(x-1, y-1)));

        }


        if ((xPlusOneIsNotOutOfBounds) && (yMinusOneIsNotOutOfBounds)) {
            lowerRightCornerNeighbor = new Point2D.Double(x + 1, y - 1);
            edgeList.add(new Edge(this.vertCordinates, lowerNeighbor, this.color,proc.getColor(x+1, y-1)));

        }

    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + " " + color.toString().substring(14);
    }
}
