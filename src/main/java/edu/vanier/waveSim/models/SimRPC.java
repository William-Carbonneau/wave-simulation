/**
 * https://github.com/Gugubo/Rock-Paper-Scissor-Cellular-Automaton/blob/master/Panel.java
 * https://github.com/topics/cellular-automata?l=java
 */
package edu.vanier.waveSim.models;

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
    private int nreOfNeededPredator = 3;
    private int nreOfRandomPredator = 3;
    private int delay = 330;
    Color[] colors = {Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE, Color.PURPLE, Color.GREEN, Color.GRAY, Color.HOTPINK};
    private final static Logger logger = LoggerFactory.getLogger(SimRPC.class);

    public SimRPC(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);
        InitializeRandomColor();
       // simFrame();
        if (scaling < 1 || scaling % 2 != 0) {
            logger.error("scaling is wrong, setting to 1 by default");
        } else {
            setScaling(scaling);
        }
    }

    public void InitializeRandomColor() {
        for (int i = 0; i < scaledX; i++) {
            for (int j = 0; j < scaledY; j++) {
                int color = (int)Math.round((Math.random()*nreOfDifferentEntities));
                current[i][j] = color;
                //System.out.println(colors[color].getGreen());
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
        System.out.println(scaledX);
        //int[][] newCells = cells;
        this.nextFrame = this.current;

        for (int i = 0; i < scaledX; i++) {
            
            for (int j = 0; j < scaledY; j++) {

                int predatorStates = (nreOfDifferentEntities - 0) / 2; 
                int[] predators = new int[predatorStates];
                int[] predatorState = new int[predatorStates];
                int gesamtPredators = 0;

                //Count number if neighbour predators for each predator state
                for (int k = 0; k < predatorStates; k++) {
                    predatorState[k] = (int) ((current[i][j] + 1 + k) % nreOfDifferentEntities);
                    predators[k] = lookAround(i, j, colors[(int) predatorState[k]]);
                    gesamtPredators += predators[k];
                }

                int randomMinimum = (int) (Math.random() * nreOfRandomPredator);

                //If there are more neighbour predators than the threshold, change current cell to a random predator cell (weighted)
                if (gesamtPredators >= nreOfRandomPredator + randomMinimum) {
                    System.out.println("Consumned");
                    int r = (int) (Math.random() * gesamtPredators);
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
                    try{
                    if (colors[(int)(getCellState(x + j - 1, y + i - 1)*nreOfDifferentEntities)] == color) {
                        c++;
                        System.out.println("lookAround");
                    }
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                    }
                }
            }
        } 
    return c ;
    }

public float getCellState(int x, int y) {
        return current[x][y];
    }
}
