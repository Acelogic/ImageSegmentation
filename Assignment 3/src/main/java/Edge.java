import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


public class Edge implements Comparable<Edge> {
    private double weight;
    private Point2D.Double beginNode;
    private Point2D.Double endNode;
    private Color beginNodeColor;
    private Color endNodeColor;
    private BufferedImage image;

    public Edge(Point2D.Double beginNode, Point2D.Double endNode, Color beingNodeColor, Color endNodeColor, BufferedImage img) {
        this.beginNodeColor = beingNodeColor;
        this.endNodeColor = endNodeColor;
        this.beginNode = beginNode;
        this.endNode = endNode;
        this.image = img;
        weight = calculateWeight();

    }

    // Generates a Weight Based on the Euclidean Color Difference for sRGB
    private double calculateWeight() {
        double redDiff = Math.pow(endNodeColor.getRed() - beginNodeColor.getRed(), 2);
        double greenDiff = Math.pow(endNodeColor.getGreen() - beginNodeColor.getRed(), 2);
        double blueDiff = Math.pow(endNodeColor.getBlue() - beginNodeColor.getGreen(), 2);

        return Math.sqrt(redDiff + greenDiff + blueDiff);
    }

    public double getWeight() {
        return weight;
    }


    public int getBeginNode1D() {
        return (((int) beginNode.getY() * image.getWidth()) + ((int) +beginNode.getX()));
    }

    public int getEndNode1D() {
        return (((int) endNode.getY() * image.getWidth()) + ((int) +endNode.getX()));
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", NodeA=" + beginNode +
                ", NodeB=" + endNode +
                '}';
    }
}
