/**
 * https://github.com/Gugubo/Rock-Paper-Scissor-Cellular-Automaton/blob/master/Panel.java
 * https://github.com/topics/cellular-automata?l=java
 */
package edu.vanier.waveSim.models;

import java.util.Random;
import java.util.logging.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 2264570
 */
public class SimRPC extends CellularLogic {

    private int frameNumber = 0;
    private int nreOfDifferentEntities = 3;
    private int nreOfNeededPredator = 1;
    private int nreOfRandomPredator = 1;
    Color[] colors = {Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE, Color.PURPLE, Color.GREEN, Color.GRAY, Color.HOTPINK};
    Color[] predatorsList = new Color[colors.length * 2];
    private final static Logger logger = LoggerFactory.getLogger(SimRPC.class);
    private Random random = new Random();

    public SimRPC(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);
        // simFrame();
        if (scaling < 1 || scaling % 2 != 0) {
            logger.info("scaling is not set, setting to 1 by default");
        } else {
            setScaling(scaling);
        }
    }

    public void InitializeRandomColor() {
        for (int i = 0; i < scaledX; i++) {
            for (int j = 0; j < scaledY; j++) {
                int color = (random.nextInt(nreOfDifferentEntities));
                current[i][j] = color;
                colorCell(i, j, colors[color]);
            }
        }
    }

    /*
    To Modify for my 
    verify scaled x and y
     */
    @Override
    public void simFrame() {

        if (hasInitialized == false) {
            InitializeRandomColor();
            hasInitialized = true;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; i < nreOfDifferentEntities; i++) {
                    predatorsList[j] = colors[j];
                }
            }
        }

        this.nextFrame = this.current;

        for (int i = 0; i < scaledX; i++) {

            for (int j = 0; j < scaledY; j++) {

                int predatorStates = (nreOfDifferentEntities - 0) / 2;
                System.out.println("Predator n: " + predatorStates);
                int[] predators = new int[predatorStates];
                int nearPredators = 0;

                //Count number if neighbour predators for each predator state
                for (int k = 0; k < predatorStates; k++) {
                    predators[k] = lookAround(i, j, predatorsList[k + 1]);
                    nearPredators += predators[k];
                }

                int randomMinimum = random.nextInt(nreOfRandomPredator);

                //If there are more neighbour predators than the threshold, change current cell to a random predator cell (weighted)
                if (nearPredators >= nreOfNeededPredator + randomMinimum && nearPredators > 0) {
                    int r = random.nextInt(nearPredators);
                    int k = -1;
                    while (r >= 0) {
                        k++;
                        r -= predators[k];
                    }
                    if (k < nreOfDifferentEntities) {
                        this.nextFrame[i][j] = (k + 1);
                    } else if (k >= nreOfDifferentEntities) {
                        this.nextFrame[i][j] = (k + 1 - nreOfDifferentEntities);
                    }
                    colorCell(i, j, predatorsList[k + 1]);
                }

            }
        }
        this.current = this.nextFrame;
        System.out.println("Frame " + frameNumber++);

    }

    public int lookAround(int x, int y, Color color) {
        int c = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 || j != 1) {
                    //Catches all the index that will be out of bound and ignore them
                    try {
                        if (colors[(int) (getCellState(x + j - 1, y + i - 1) * nreOfDifferentEntities)] == color) {
                            c++;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }
        return c;
    }

    public float getCellState(int x, int y) {
        return current[x][y];
    }

}
