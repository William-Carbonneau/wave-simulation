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
 * Langton's ant is a cellular automaton where an "ant" walks in the grid
 * depending on the color/value of the cell, it turns right or left. White cells
 * makes the ant turn right, while black cells makes the ant turn left.
 *
 * @author 2264570 - Dmitrii Cazacu
 */
public class SimAnt extends CellularLogic {

    private final static Logger logger = LoggerFactory.getLogger(SimAnt.class);
    /**
     * Horizontal and Vertical position of the ant
     */
    int xPosition, yPosition;
    Random random = new Random();
    /**
     * Direction that the ant faces. 0 = Up, 1 = Right, 2 = Down, 3 = Left
     */
    int direction = 0;

    /**
     * Constructor to initialize the simulation
     *
     * @param operatingCanvas type Canvas (javaFX)
     * @param widthX type int height of grid in pixels
     * @param heightY type int height of grid in pixels
     * @param scaling type int scaling of grid from GUI
     */
    public SimAnt(Canvas operatingCanvas, int widthX, int heightY, int scaling) {
        super(operatingCanvas, widthX, heightY);
        if (scaling < 1 || scaling % 2 != 0) {
            logger.info("scaling is not set, setting to 1 by default");
        } else {
            setScaling(scaling);
        }

    }

    /*
    * Implementation of simulation called at every frame
     */
    @Override
    public void simFrame() {
        if (hasInitialized == false) {
            xPosition = random.nextInt(0, scaledX);
            yPosition = random.nextInt(0, scaledY);
            hasInitialized = true;
            System.out.println(scaledX + ", " + scaledY);
        }
        antMoves();
    }

    /*
     * Looks at the direction that the ant is facing and the cell on which it is on.
     * Using this information, it will move in a certain direction. Then, color the cell
     * on which it has previously been. Zero, a white cell, is right. One, a black cell, is left.
     */
    public void antMoves() {
        if (direction == 0) {
            if (current[xPosition][yPosition] == 0) {
                direction = 1;
                colorAntCell(xPosition, yPosition);
                if (xPosition < scaledX - 1) {
                    xPosition++;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                direction = 3;
                colorAntCell(xPosition, yPosition);
                if (xPosition > 0) {
                    xPosition--;
                    return;
                }
            }
        } else if (direction == 1) {
            if (current[xPosition][yPosition] == 0) {
                direction = 2;
                colorAntCell(xPosition, yPosition);
                if (yPosition < scaledY - 1) {
                    yPosition++;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                direction = 0;
                colorAntCell(xPosition, yPosition);
                if (yPosition > 0) {
                    yPosition--;
                    return;
                }
            }
        } else if (direction == 2) {
            if (current[xPosition][yPosition] == 0) {
                direction = 3;
                colorAntCell(xPosition, yPosition);
                if (xPosition > 0) {
                    xPosition--;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                direction = 1;
                colorAntCell(xPosition, yPosition);
                if (xPosition < scaledX - 1) {
                    xPosition++;
                    return;
                }
            }
        } else if (direction == 3) {
            if (current[xPosition][yPosition] == 0) {
                direction = 0;
                colorAntCell(xPosition, yPosition);
                if (yPosition > 0) {
                    yPosition--;
                    return;
                }
            } else if (current[xPosition][yPosition] == 1) {
                direction = 2;
                colorAntCell(xPosition, yPosition);
                if (yPosition < scaledY - 1) {
                    yPosition++;
                    return;
                }
            }
        }
    }

    /**
     * Color and change the value of the cell on which the ant was on depending
     * on the current color of it.
     *
     * @param x The horizontal coordinate of the cell
     * @param y The vertical coordinate of the cell
     */
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
