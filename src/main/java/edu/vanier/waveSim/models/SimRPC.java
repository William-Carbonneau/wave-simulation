/**
 * https://github.com/Gugubo/Rock-Paper-Scissor-Cellular-Automaton/blob/master/Panel.java
 * https://github.com/topics/cellular-automata?l=java
 */
package edu.vanier.waveSim.models;

import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 2264570
 */
public class SimRPC extends CellularLogic {

    private int nreOfDifferentEntities = 3;
    private int nreOfNeededPredator = 1;
    private int nreOfRandomPredator = 1;
    private int delay = 330;
    Color[] colors = {Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE, Color.PURPLE, Color.GREEN, Color.GRAY, Color.HOTPINK};
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
        }
        
        this.nextFrame = this.current;

        for (int i = 0; i < scaledX; i++) {

            for (int j = 0; j < scaledY; j++) {

                int predatorStates = (nreOfDifferentEntities - 0) / 2; 
                int[] predators = new int[predatorStates];
                int[] predatorState = new int[predatorStates];
                int gesamtPredators = 0;

                //Count number if neighbour predators for each predator state
                for (int k = 0; k < predatorStates; k++) {
                    predatorState[k] = (int) ((current[i][j] +1 +k) % nreOfDifferentEntities);
                    predators[k] = lookAround(i, j, colors[(int) predatorState[k]]);
                    gesamtPredators += predators[k];
                }

                int randomMinimum = random.nextInt(nreOfRandomPredator);

                //If there are more neighbour predators than the threshold, change current cell to a random predator cell (weighted)
                if (gesamtPredators >= nreOfRandomPredator + randomMinimum) {
                    int r = random.nextInt(gesamtPredators);
                    int k = -1;
                    while (r >= 0) {
                        k++;
                        r -= predators[k];
                    }
                    this.nextFrame[i][j] = predatorState[k];
                    colorCell(i, j, colors[predatorState[k]]);
                }

            }

        }

        this.current = this.nextFrame;

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
