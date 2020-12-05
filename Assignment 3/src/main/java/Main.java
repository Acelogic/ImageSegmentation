
import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    BufferedImage image;
    ArrayList<Edge> edgeList;
    ColorProcessor colorProcPost;
    int nrOfVertices;
    float thresholdKValue;
    double GaussBlurSigmaFactor;
    int minCompSize;
    DisjointSetForest forest;

    public Main() throws IOException {
        thresholdKValue = 1500;
        GaussBlurSigmaFactor = 1;
        minCompSize = 5;

        // Flower Image k = 1500 , blur = 1 , minComp = 5

        // Blurring the original image before the segmentation
        BufferedImage originalImage = getImageFromPath();
        ColorProcessor before = new ColorProcessor(originalImage);
        before.blurGaussian(GaussBlurSigmaFactor);
        ImagePlus imgPlus = new ImagePlus("Original", before);
        // Exporting the result of the blurred image back to a buffered image
        image = before.getBufferedImage();


        edgeList = new ArrayList<>();
        nrOfVertices = image.getHeight() * image.getWidth();

        // Blurred buffered Image is set to be processed
        colorProcPost = new ColorProcessor(image);
        forest = new DisjointSetForest(nrOfVertices);


        System.out.println(ANSI_YELLOW + "Number of Vertices: " + ANSI_RESET + nrOfVertices);
        generateEdgeGraph();
        segmentImage();
        imgPlus.show();
        new ImagePlus("Post Segmented", coloredProcessedImage()).show();
        System.out.println(ANSI_RED + "Displaying Post Processed Image");
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
        System.out.println(ANSI_YELLOW + "Generating Graph" + ANSI_RESET);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                edgeList.addAll(new PixelVertex(x, y, colorProcPost).getEdgeList());
            }
        }
        Collections.sort(edgeList);
        System.out.println(ANSI_YELLOW + "Number of Edges Size: " + ANSI_RESET + edgeList.size());
    }

    public void segmentImage() {
        System.out.println("\n"+ANSI_RED + "Segmenting Image" + ANSI_RESET);
        forest.segmentGraph(edgeList, thresholdKValue);
        for (Edge e : edgeList) {
            int a = forest.find(e.getBeginNode1D());
            int b = forest.find(e.getEndNode1D());
            if ((a != b) && ((forest.sizeOfComponent(a) < minCompSize) || (forest.sizeOfComponent(b) < minCompSize))) {
                forest.join(a, b);
            }
        }
    }


    public ImageProcessor coloredProcessedImage() {
        System.out.println(ANSI_RED + "Generating Color Processed Preview" + ANSI_RESET);
        ColorProcessor result = (ColorProcessor) colorProcPost.duplicate();
        int[][] randomColorPool = getRandom3ColorsAndCycle(image.getWidth() * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int component = forest.find( x + y * image.getWidth());
                result.putPixel(x, y, randomColorPool[component]);
            }
        }
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
