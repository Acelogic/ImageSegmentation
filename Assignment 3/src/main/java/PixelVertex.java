import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

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

    private Point2D.Double rightNeighbor;
    private Point2D.Double upperRightCornerNeighbor;
    private Point2D.Double lowerRightCornerNeighbor;

    private Point2D.Double leftNeighbor;
    private Point2D.Double upperLeftCornerNeighbor;
    private Point2D.Double lowerLeftCornerNeighbor;

    private Point2D.Double upperNeighbor;
    private Point2D.Double lowerNeighbor;
    BufferedImage image;
    LinkedList<Edge> edgeList;


    public PixelVertex(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;

        this.image = image;
        int rgb = image.getRGB(x,y);
        this.color = new ColorProcessor(image).getColor(x,y);
        redValue = color.getRed();
        greenValue = color.getGreen();
        blueValue = color.getBlue();
        edgeList = new LinkedList<>();
        determineNeighbors();
    }

    public PixelVertex(int x, int y, BufferedImage image,  boolean doNotDiscoverNeighbors){
        this.x = x;
        this.y = y;
        this.image = image;
        this.color = new ColorProcessor(image).getColor(x,y);
        redValue = color.getRed();
        greenValue = color.getGreen();
        blueValue = color.getBlue();
        if(!doNotDiscoverNeighbors) {
            determineNeighbors();
        }
    }




    // Best way to think of this is the pixel as the origin on a Cartesian plane
    private void determineNeighbors() {
        if (x + 1 >= 0) {
            rightNeighbor = new Point2D.Double(x + 1, y);
            edgeList.add(new Edge( this , new PixelVertex(x+1, y, image, true )));
        }


        if (x - 1 >= 0) {
            leftNeighbor = new Point2D.Double(x - 1, y);
            edgeList.add(new Edge( this , new PixelVertex(x - 1, y, image, true)));
        }


        if (y + 1 >= 0) {
            upperNeighbor = new Point2D.Double(x, y + 1);
            edgeList.add(new Edge( this , new PixelVertex(x, y + 1, image, true)));
        }


        if (y - 1 >= 0) {
            lowerNeighbor = new Point2D.Double(x, y - 1);
            edgeList.add(new Edge( this , new PixelVertex(x, y - 1, image, true)));
        }


        if (x - 1 >= 0 && y + 1 >= 0) {
            upperLeftCornerNeighbor = new Point2D.Double(x - 1, y + 1);
            edgeList.add(new Edge( this , new PixelVertex(x - 1, y + 1, image, true)));
        }


        if (x + 1 >= 0 && y + 1 >= 0) {
            upperRightCornerNeighbor = new Point2D.Double(x + 1, y + 1);
            edgeList.add(new Edge( this , new PixelVertex(x + 1, y + 1, image)));
        }


        if (x - 1 >= 0 && y - 1 >= 0) {
            lowerLeftCornerNeighbor = new Point2D.Double(x - 1, y - 1);
            edgeList.add(new Edge( this , new PixelVertex(x - 1, y - 1, image)));
        }


        if (x + 1 >= 0 && y - 1 >= 0) {
            lowerRightCornerNeighbor = new Point2D.Double(x + 1, y - 1);
            edgeList.add(new Edge( this , new PixelVertex(x + 1, y - 1, image)));
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point2D.Double getRightNeighbor() {
        return rightNeighbor;
    }

    public Point2D.Double getLeftNeighbor() {
        return leftNeighbor;
    }

    public Point2D.Double getUpperNeighbor() {
        return upperNeighbor;
    }

    public Point2D.Double getLowerNeighbor() {
        return lowerNeighbor;
    }

    public Point2D.Double getUpperRightCornerNeighbor() {
        return upperRightCornerNeighbor;
    }

    public Point2D.Double getLowerRightCornerNeighbor() {
        return lowerRightCornerNeighbor;
    }

    public Point2D.Double getUpperLeftCornerNeighbor() {
        return upperLeftCornerNeighbor;
    }

    public Point2D.Double getLowerLeftCornerNeighbor() {
        return lowerLeftCornerNeighbor;
    }

    public LinkedList<Edge> getEdgeList() {
        return edgeList;
    }

    public int getRedValue() {
        return redValue;
    }

    public int getBlueValue() {
        return blueValue;
    }

    public int getGreenValue() {
        return greenValue;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + " " + color.toString().substring(14);
    }
}
