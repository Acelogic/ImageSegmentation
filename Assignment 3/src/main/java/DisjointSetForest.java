import java.util.ArrayList;

public class DisjointSetForest {

    class SetNode {
        private int rank;
        private int parent;
        private int size;

        public SetNode(int rank, int parent, int size) {
            this.rank = rank;
            this.parent = parent;
            this.size = size;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private ArrayList<SetNode> elements;
    int nrOfVertices;

    public DisjointSetForest(int sizeOfForest) {
        this.nrOfVertices = sizeOfForest;
        this.elements = new ArrayList<>(sizeOfForest);

        // Populating all the SetNode elements with initial values
        for (int i = 0; i < sizeOfForest; i++) {
            elements.add(new SetNode(0, i, 1));
        }
    }

    // Implementation is mostly followed from wikipedia and the pseudocode
    // presented there
    public int find(int x) {
        int y = x;
        while (y != elements.get(y).getParent()) {
            y = elements.get(y).getParent();
        }
        elements.get(x).setParent(y);
        return y;
    }

    public void join(int x, int y) {
        // if the ranks of the setNode's y associated rank is less than the x
        if (elements.get(x).getRank() > elements.get(y).getRank()) {

            // Consider SetNode's parent value to point to the value of the greater one which
            // is x
            elements.get(y).setParent(x);

            // The new size of the x element is the sum of itself and the lesser one
            elements.get(x).setSize((elements.get(x).getSize()) + (elements.get(y).getSize()));
        } else {

            // Opposite case for y
            // Consider SetNode's parent value to point to the value of the greater one which
            // is y
            elements.get(x).setParent(y);

            // The new size of the x element is the sum of itself and the lesser one
            elements.get(y).setSize((elements.get(x).getSize()) + (elements.get(y).getSize()));
            if (elements.get(x).rank == elements.get(y).getRank()) {
                elements.get(y).setRank(elements.get(y).getRank() + 1);
            }
        }
    }


    public void segmentGraph(ArrayList<Edge> edgeList, float k) {
        ArrayList<Float> thresholds = new ArrayList<>(nrOfVertices);
        for (int i = 0; i < nrOfVertices; i++) {
            thresholds.add(k);
        }

        for (Edge e : edgeList) {
            int a = find(e.getBeginNode1D());
            int b = find(e.getEndNode1D());

            if (a != b) {
                if (e.getWeight() <= thresholds.get(a) && e.getWeight() <= thresholds.get(b)) {
                    join(a, b);
                    thresholds.set(a, (float) (e.getWeight() + k / sizeOfComponent(a)));
                }
            }

        }

    }

    public int sizeOfComponent(int x) {
        return elements.get(x).size;
    }

    public ArrayList<SetNode> getElements() {
        return elements;
    }


}
