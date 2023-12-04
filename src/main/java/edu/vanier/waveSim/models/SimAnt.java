/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.waveSim.models;

import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Unkno
 */
public class SimAnt extends CellularLogic {

    private final static Logger logger = LoggerFactory.getLogger(SimAnt.class);
    int xPosition, yPosition;
    Random random = new Random();
    int direction = 0;

    public SimAnt(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);
        if (scaling < 1 || scaling % 2 != 0) {
            logger.info("scaling is not set, setting to 1 by default");
        } else {
            setScaling(scaling);
        }

    }

    @Override
    public void simFrame() {
        if (hasInitialized == false) {
            xPosition = random.nextInt(0, scaledX);
            yPosition = random.nextInt(0, scaledY);
            hasInitialized = true;
            System.out.println(scaledX+ ", "+ scaledY);
        }
        antMoves();
    }

    public void antMoves() {
        if (direction == 0) {
            direction = 1;
            if (current[xPosition][yPosition] == 0) {
                colorAntCell(xPosition, yPosition);
                if (xPosition < scaledX-1) {
                    xPosition++;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                colorAntCell(xPosition, yPosition);
                if (xPosition > 0) {
                    xPosition--;
                    return;
                }
            }
        }
        else if (direction == 1) {
            direction = 2;
            if (current[xPosition][yPosition] == 0) {
                colorAntCell(xPosition, yPosition);
                if (yPosition < scaledY-1) {
                    yPosition++;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                colorAntCell(xPosition, yPosition);
                if (yPosition > 0) {
                    yPosition--;
                    return;
                }
            }
        }
        else if (direction == 2) {
            direction = 3;
            if (current[xPosition][yPosition] == 0) {
                colorAntCell(xPosition, yPosition);
                if (xPosition > 0) {
                    xPosition--;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                colorAntCell(xPosition, yPosition);
                if (xPosition < scaledX-1) {
                    xPosition++;
                    return;
                }
            }
        }
        else if (direction == 3) {
            direction = 0;
            if (current[xPosition][yPosition] == 0) {
                colorAntCell(xPosition, yPosition);
                if (yPosition > 0) {
                    yPosition--;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                colorAntCell(xPosition, yPosition);
                if (yPosition < scaledY-1) {
                    yPosition++;
                    return;
                }
            }
        }
    }

    public void colorAntCell(int x, int y) {
        if (current[x][y] == 0) {
            current[x][y] = 1;
            colorCell(x, y, Color.BLACK);
        } else if (current[x][y] == 1) {
            current[x][y] = 0;
            colorCell(x, y, Color.WHITE);
        }
    }


    
    @Override
    public void setPoint(int x, int y) {
    }

    @Override
    public boolean removePoint(int x, int y) {
        return false;
    }
}
