public class Edge implements Comparable<Edge>{
    private double weight;
    private PixelVertex nodeA;
    private PixelVertex nodeB;
    public Edge(PixelVertex nodeA, PixelVertex nodeB){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        weight = calculateWeight();

    }

    // Generates a Weight Based on the Euclidean Color Difference for sRGB
    private double calculateWeight() {
       double redDiff = Math.pow(nodeB.getRedValue() - nodeA.getRedValue(), 2);
       double greenDiff = Math.pow(nodeB.getGreenValue() - nodeA.getRedValue(), 2);
       double blueDiff = Math.pow(nodeB.getBlueValue() - nodeA.getGreenValue(), 2);

       return Math.sqrt(redDiff + greenDiff + blueDiff);
    }
    public double getWeight() {
        return weight;
    }

    public PixelVertex getNodeA() {
        return nodeA;
    }

    public PixelVertex getNodeB() {
        return nodeB;
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
