/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.waveSim.models;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 2265724
 */
public class ConwayGameOfLifeLogic extends CellularLogic{
    
    private final static Logger logger = LoggerFactory.getLogger(SimLogicWave1.class);

    public ConwayGameOfLifeLogic(Canvas operatingCanvas, int widthX, int heightY) {
        super(operatingCanvas, widthX, heightY);
        // deal with scaling
        if (scaling < 1 || scaling % 2 != 0) {
            logger.error("scaling is wrong, setting to 1 by default");
        }else {
            setScaling(scaling);
        }
    }

    @Override
    public void simFrame() {
        int size;
        byte[] prev = new byte[size];
        byte[] next = new byte[size];
        for (int x =1; x<scaledX-1;x++){
            for (int y =1; y<scaledY-1;y++){
                
            }
        
        }
    
    }
    
    
    
}