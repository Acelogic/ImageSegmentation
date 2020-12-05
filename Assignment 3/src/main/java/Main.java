
import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Main {

    BufferedImage image;
    ArrayList<Edge> edgeList;
    ColorProcessor colorProc;
    int nrOfVertices;
    float k;
    double sigma;
    int minCompSize;
    DisjointSetForest forest;

    public Main() throws IOException {
        image = getImageFromPath();
        ImagePlus imgPlus = new ImagePlus("Original", image);
        edgeList = new ArrayList<>();
        colorProc = new ColorProcessor(image);
        nrOfVertices = image.getHeight() * image.getWidth();
        forest = new DisjointSetForest(nrOfVertices);
        k = 3200;
        sigma = 0.65;
        minCompSize = 50;
        System.out.println("Number of Vertices: " + nrOfVertices);
        generateEdgeGraph();
        segmentImage();
        imgPlus.show();
        new ImagePlus( "Post Segmented", coloredProcessedImage()).show();
        System.out.println("Displaying Image");
    }

    private BufferedImage getImageFromPath() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG,JPEG,PNG,", "jpg", "png", "jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        String path = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
        }
        File file = new File(path);
        return ImageIO.read(file);
    }

    public void generateEdgeGraph() {
        System.out.println("Generating Graph");
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                edgeList.addAll(new PixelVertex(x, y, colorProc).edgeList);
            }
        }
        Collections.sort(edgeList);
        System.out.println("Number of Edges Size: " + edgeList.size());
    }

    public void segmentImage() {
        System.out.println("Segmenting Image");
        forest.segmentGraph(edgeList, k);
        for (Edge e : edgeList) {
            int a = forest.find(e.getBeginNode1D());
            int b = forest.find(e.getEndNode1D());
            if ((a != b) && ((forest.sizeOfComponent(a) < minCompSize) || (forest.sizeOfComponent(b) < minCompSize))) {
                forest.join(a, b);
            }
        }
    }


    public ImageProcessor coloredProcessedImage() {
        System.out.println("Generating Color Processed Preview");
        ColorProcessor result = (ColorProcessor) colorProc.duplicate();
        int[][] randomColorPool = getRandom3ColorsAndCycle(image.getWidth() * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int component = forest.find(y * image.getWidth() + x);
                result.putPixel(x, y, randomColorPool[component]);
            }
        }
        result.blurGaussian(sigma);
        return result;
    }

    public int[][] getRandom3ColorsAndCycle(int num) {
        int[][] returnInt = new int[num][3];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < 3; j++) {
                returnInt[i][j] = (int) (Math.random() * 255);
            }
        }
        return returnInt;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Main m = new Main();

    }
}
