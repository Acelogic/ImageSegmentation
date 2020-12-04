import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Edge implements Comparable<Edge>{
    private double weight;
    private Point2D.Double nodeA;
    private Point2D.Double nodeB;
    private Color nodeAColor; 
    private Color nodeBColor;
    public Edge(Point2D.Double nodeA, Point2D.Double nodeB, Color nodeAColor, Color nodeBColor){
        this.nodeAColor = nodeAColor;
        this.nodeBColor = nodeBColor; 
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        weight = calculateWeight();

    }

    // Generates a Weight Based on the Euclidean Color Difference for sRGB
    private double calculateWeight() {
       double redDiff = Math.pow(nodeBColor.getRed() - nodeAColor.getRed(), 2);
       double greenDiff = Math.pow(nodeBColor.getGreen() - nodeAColor.getRed(), 2);
       double blueDiff = Math.pow(nodeBColor.getBlue() - nodeAColor.getGreen(), 2);

       return Math.sqrt(redDiff + greenDiff + blueDiff);
    }
    public double getWeight() {
        return weight;
    }



    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", NodeA=" + nodeA +
                ", NodeB=" + nodeB +
                '}';
    }
}
