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

    private int nreOfDifferentEntities = 5;
    private int nreOfNeededPredator = 3;
    Color[] colors = {Color.ORANGE, Color.YELLOW, Color.RED, Color.BLUE, Color.PURPLE, Color.GREEN, Color.GRAY};
    private final static Logger logger = LoggerFactory.getLogger(SimRPC.class);

    public SimRPC(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);

        //Initialize random color
        for (int i = 0; i < widthX; i++) {
            for (int j = 0; j < heightY; j++) {
                colorCell(i, j, colors[(int) (Math.random() * nreOfDifferentEntities)]);
            }
        }

        if (scaling < 1 || scaling % 2 != 0) {
            logger.error("scaling is wrong, setting to 1 by default");
        } else {
            setScaling(scaling);
        }
    }

    @Override
    public void simFrame() {
        
    }

    public void InitializeRandomColor() {
        for (int i = 0; i < widthX; i++) {
            for (int j = 0; j < heightY; j++) {
                colorCell(i, j, colors[(int) (Math.random() * nreOfDifferentEntities)]);
            }
        }
    }
    
    public void lookAround(int x, int y, Color color){
        int numberOfSpecific = 0;
        
    }
}
