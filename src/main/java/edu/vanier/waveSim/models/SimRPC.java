package edu.vanier.waveSim.models;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simulation for Rock-Paper-Scissors in which cells eat each other in sequence
 * Rules are sequential as in paper->rock, rock->scissors, scissors->paper
 * Loosely based on:
 * Gugubo. (2018, November 1)
 * Build software better, together. GitHub. (n.d.).
 * @author 2264570 - Dmitrii
 */
public class SimRPC extends CellularLogic {
    
    /**Number of predators required for cell to be eaten*/
    private int nreOfNeededPredator = 2;
    /**List of colors*/
    Color[] colors = {Color.RED, Color.BLUE, Color.GREEN};
    private final static Logger logger = LoggerFactory.getLogger(SimRPC.class);
    /**Instance of Perlin Noise for initial conditions*/
    private PerlinNoise perlin = new PerlinNoise();

    /**
     * Constructor to initialize the simulation
     * @param operatingCanvas type Canvas (javaFX)
     * @param widthX type int height of grid in pixels
     * @param heightY type int height of grid in pixels
     * @param scaling type int scaling of grid from GUI
     */
    public SimRPC(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);
        if (scaling < 1 || scaling % 2 != 0) {
            logger.info("scaling is not set, setting to 1 by default");
        } else {
            setScaling(scaling);
        }
    }

    /**
     * Initialize the simulation for the very first frame.
     * uses the Perlin noise for a more interesting starting condition.
     */
    public void InitializeRandomColor() {
        for (int i = 0; i < scaledX; i++) {
            for (int j = 0; j < scaledY; j++) {
                int color;
                double value = perlin.noise(i, j);
                if (value >= -1 && value < -0.3) {
                    color = 0;
                } else if (value >= -0.3 && value < 0.3) {
                    color = 1;
                } else if (value >= 0.3 && value <= 1) {
                    color = 2;
                } else {
                    color = 0;
                }
                current[i][j] = color;
            }
        }
        nextFrame = current;
    }

    /*
    * Implementation of simulation called at every frame
    */
    @Override
    public void simFrame() {

        if (hasInitialized == false) {
            InitializeRandomColor();
            System.out.println("Initialized");
            hasInitialized = true;
        } else {
            
            for (int i = 0; i < scaledX; i++) {
                for (int j = 0; j < scaledY; j++) {
                    devouredOrNot(i, j);
                }
            }
            float[][] temp = this.current;
            this.current = this.nextFrame;
            this.nextFrame = temp;

        }

        paintTheCanvas(scaledX, scaledY);
    }

    /**
     * Look around the neighbors cell for predators. If the numbers of predators found
     * is bigger or equal than the required threshold, the cell would be "devoured"
     * and become the predator.
     * @param x The horizontal coordinate of the cell
     * @param y The vertical coordinate of the cell
     */
    public void devouredOrNot(int x, int y) {
 
        int predators = 0;

        //Look around each neighbors cell for predators.
    try{
        float myPredator = ((current[x][y] + 1) % 3);
        if(current[x-1][y-1]==myPredator){
            predators++;
        }
        if(current[x-1][y]==myPredator){
            predators++;
        }
        if(current[x-1][y+1]==myPredator){
            predators++;
        }
        if(current[x][y-1]==myPredator){
            predators++;
        }
        if(current[x][y+1]==myPredator){
            predators++;
        }
        if(current[x+1][y-1]==myPredator){
            predators++;
        }
        if(current[x+1][y]==myPredator){
            predators++;
        }
        if(current[x+1][y+1]==myPredator){
            predators++;
        }
        //If it goes out of bounds for the corner of the grid. Ignore it.
    }catch(ArrayIndexOutOfBoundsException e){
    }
    
        //Red -> 0, Blue -> 1, Green -> 2
        //Change the cell to its predator if the number of neighbors predators
        //exceed or equal the threshold.
        switch ((int) current[x][y]) {
            case 0 -> {
                if (predators >= nreOfNeededPredator) {
                    this.nextFrame[x][y] = 1;
                }
            }
            case 1 -> {
                if (predators >=nreOfNeededPredator) {
                    this.nextFrame[x][y] = 2;
                }
            }
            case 2 -> {
                if (predators >= nreOfNeededPredator) {
                    this.nextFrame[x][y] = 0;
                }
            }
            default -> {
            }
        }

    }

    /**color the canvas based on the values in the grid*/
    public void paintTheCanvas(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                colorCell(i, j, colors[(int) (current[i][j])]);
            }
        }
    }

    /** check indices in the grid around the given point*/
    public int lookAround(int x, int y, int predatorInt) {
        int c = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 || j != 1) {
                    //Catches all the index that will be out of bound and ignore them
                    try {
                        if (current[x + j - 1][y + i - 1] == predatorInt) {
                            c++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }

                }
            }
        }

        return c;

    }
}
