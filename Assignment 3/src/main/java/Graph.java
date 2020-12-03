import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Graph {


    BufferedImage img;
    ArrayList<Edge> edgeList;
    LinkedHashMap<Point2D.Double, LinkedList<Edge>> graph;
    public Graph(BufferedImage img) {
        this.img = img;
        graph = new LinkedHashMap<>();;
        edgeList = new ArrayList<>();
        int expectedEdges = (int) ((((img.getWidth()-1)*(img.getHeight())))+(((img.getHeight()-1)*(img.getWidth()))+(2.0*(img.getWidth()-1)*(img.getHeight()-1))));
        System.out.println("Vertices: " + img.getWidth() * img.getHeight());
        System.out.println("Expected Edges: " + expectedEdges);
        createEdgeList();

    }

    private void createEdgeList(){

    }



    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }
}
