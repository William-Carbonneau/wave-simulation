package edu.vanier.waveSim.models;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 * The abstract class CellularLogic defines the basic structure and behavior for
 * cellular automation logic. It serves as a foundation for specific cellular
 * automation implementations.
 *
 * @author William Carbonneau <2265724 at edu.vaniercollege.ca>
 */
public abstract class CellularLogic {

    /**The grid for the current frame*/
    protected float[][] current;
    /**The grid the the next frame to be modified in the loop*/
    protected float[][] nextFrame;

    /**The canvas to draw the simulation onto*/
    protected final Canvas operatingCanvas;

    /**The width of the simulation*/
    protected int widthX;
    /**The height of the simulation measured form the top first*/
    protected int heightY;

    /**Scaled value of width*/
    protected int scaledX;
    /**Scaled value of height*/
    protected int scaledY;
    
    /**Has the simulation been initialized? - Used for simulations with procedural starting conditions.*/
    protected boolean hasInitialized;
    // render variables
    /**Number of current frame when rendering*/
    protected int frameNumber = 0;
    /**Image instance to render into*/
    protected BufferedImage image;
    /**Frame limit for simulation tat is defined by GUI*/
    protected int frameLimit = Integer.MAX_VALUE; // default is pseudo-indefinite but not forever
    
    /**Is the simulation rendering frames to files?*/
    protected boolean renderFlag = false;

    /**The color of the background/dead cells*/
    protected Color backgroundColor = Color.WHITE;
    
    /**create default render path*/
    protected String renderPath = new File("").getAbsolutePath()+"/render"+System.currentTimeMillis();;

    /** Must be an even integer, should be regulated by a drop-down menu in the GUI*/
    protected int scaling = 1;

    /**
     * Create new simulation from a width and height
     *
     * @param operatingCanvas The canvas to draw the simulation on. Must be
     * accessible from calling thread.
     * @param widthX The width in pixels for the horizontal
     * @param heightY The height in pixels for the vertical, measured top to
     * bottom Must set scaling, but it is by default 1
     */
    public CellularLogic(Canvas operatingCanvas, int widthX, int heightY) {
        this.operatingCanvas = operatingCanvas;

        this.widthX = widthX;
        this.heightY = heightY;
        this.scaledX = widthX;
        this.scaledY = heightY;

        // make grid even width and height
        if (widthX % 2 != 0) {
            widthX -= 1;
        } else if (heightY % 2 != 0) {
            heightY -= 1;
        }

        this.current = new float[widthX][heightY];
        this.nextFrame = new float[widthX][heightY];
    }

    /**
     * Scale the height and width of the grid by the scaling factor given.
     *
     * @param scaling The scaling factor.
     */
    public void setScaling(int scaling) {
        this.scaling = scaling;
        // scale grid by scaling factor (since it is an even number, and the width/height are even, this will always be integers.)
        this.scaledX = widthX / this.scaling;
        this.scaledY = heightY / this.scaling;
        this.current = new float[widthX / this.scaling][heightY / this.scaling];
        this.nextFrame = new float[widthX / this.scaling][heightY / this.scaling];

    }
    
    /**
     * Set the width of the simulation
     * @param width type int in pixels
     */
    public void setWidth(int width){
        this.widthX = width;
        this.scaledX = width/this.scaling;
        this.current = new float[widthX / this.scaling][heightY / this.scaling];
        this.nextFrame = new float[widthX / this.scaling][heightY / this.scaling];
    }
    
    /**
     * Set the height of the simulation
     * @param height type int in pixels
     */
    public void setHeight(int height){
        this.heightY = height;
        this.scaledY = height/this.scaling;
        this.current = new float[widthX / this.scaling][heightY / this.scaling];
        this.nextFrame = new float[widthX / this.scaling][heightY / this.scaling];
    }

    /**
     * Get the scaled width of the simulation and return it.
     *
     * @return scaledX The scaled width of the simulation. It is an int
     * value.
     */
    public int getScaledX() {
        return scaledX;
    }

    /**
     * Get the scaled height of the simulation and return it.
     *
     * @return scaledY The scaled height of the simulation. It is an int
     * value.
     */
    public int getScaledY() {
        return scaledY;
    }

    /**
     * Return the boolean value if the simulation is set to render. 
     */
    public boolean getRenderFlag() {
        return renderFlag;
    }
    

    /**
     * Get the operating canvas of the simulation and return it.
     *
     * @return opertingCanavas The operating canvas of the simulation. It is a
     * Canvas object.
     */
    public Canvas getOperatingCanvas() {
        return operatingCanvas;
    }

    /**
     * Get the scaling factor of the simulation and return it.
     *
     * @return scaling The scaling factor of the simulation. It is an int
     * value.
     */
    public int getScaling() {
        return scaling;
    }

    /**
     * Get the value of frame limit for the current simulation
     * @return type int frame limit
     */
    public int getFrameLimit() {
        return frameLimit;
    }

    /**
     * Get the frame number of this simulation - used in the animation only
     * @return type int frame number
     */
    public int getFrameNumber() {
        return frameNumber;
    }

    /**
     * Return the value of hasInitialized
     * @return type boolean
     */
    public boolean isHasInitialized() {
        return hasInitialized;
    }

    /**
     * Set the width of the simulation to the specified width.
     *
     * @param widthX The specified width of the simulation. It is an int
     * value.
     */
    public void setWidthX(int widthX) {
        this.widthX = widthX;
    }

    /**
     * Set the frame number parameter - used within the animation only to calculate frame limit
     * @param frameNumber
     */
    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    /**
     * Set the frame limit for rendering this simulation
     * @param frameLimit
     */
    public void setFrameLimit(int frameLimit) {
        this.frameLimit = frameLimit;
    }
    
    

    /**
     * Set the directory path to render into - should only ever be set by its associated function
     * @param renderPath type String file path
     */
    public void setRenderPath(String renderPath) {
        this.renderPath = renderPath;
    }

    /**
     * Set the value of the render parameter, true makes the simulation begin rendering mode
     * @param renderFlag, false for no render, true for render mode
     */
    public void setRenderFlag(boolean renderFlag) {
        this.renderFlag = renderFlag;
    }

    /**
     * Set the height of the simulation to the specified height.
     *
     * @param heightY The specified height of the simulation. It is an int
     * value.
     */
    public void setHeightY(int heightY) {
        this.heightY = heightY;
    }

    public boolean getHasInitialized() {
        return hasInitialized;
    }

    public void setHasInitialized(boolean hasInitialized) {
        this.hasInitialized = hasInitialized;
    }

    /**
     * Get the width of the simulation and return it.
     *
     * @return widthX The width of the simulation. It is an int value.
     */
    public int getWidthX() {
        return widthX;
    }

    /**
     * Get the height of the simulation and return it.
     *
     * @return heightY The height of the simulation. It is an int value.
     */
    public int getHeightY() {
        return heightY;
    }

    /**
     * Get the background color of the simulation and return it.
     *
     * @return backgroundColor The background color of the simulation. It is a
     * color object.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Set the background color of the simulation to the specified background
     * color.
     *
     * @param backgroundColor The specified color that the background of the
     * simulation. It is a color object.
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Set a point in the entered vertical and horizontal position.
     *
     * @param x horizontal position in pixels on the canvas
     * @param y vertical position in pixels on the canvas
     */
    public void setPoint(int x, int y) {
        this.current[x / this.scaling][y / this.scaling] = 255;
    }

    /**
     * Removes a point from the simulation array if it is maximum 255 (removes
     * only origination points)
     *
     * @param x horizontal position in pixels on the canvas
     * @param y vertical position in pixels on the canvas
     * @return true if point was removed from the array, false otherwise
     */
    public boolean removePoint(int x, int y) {
        if (this.current[x / this.scaling][y / this.scaling] == 255) {
            this.current[x / this.scaling][y / this.scaling] = 0;
            return true;
        }
        return false;
    }
    
    /**
     * Create a new Buffered Image in which to render a new image into. Follows the scaled grid size.
     * THis sets the new buffer to the image instance variable 
     */
    public void createRenderContext() {
        image = new BufferedImage(scaledX,scaledY, BufferedImage.TYPE_INT_RGB);
        
    }
    
    /**
     * Save a new frame as a bitmap .bmp file based on renderPath
     * @throws java.io.IOException
     */
    public void saveFrame() throws IOException {
        File imgOut = new File(renderPath+"/"+frameNumber+".bmp");
        ImageIO.write(image, "BMP", imgOut);
    }
    
    /**
     * Set the color of a single cell in the canvas from array coordinates using
     * scale factor.
     *
     * @param xPosInArray The x position from top right in units of scaled
     * pixels int the numerical grid
     * @param yPosInArray The y position from top right in units of scaled
     * pixels int the numerical grid
     * @param color The color of the pixel using javaFX Color object
     */
    public void colorCell(int xPosInArray, int yPosInArray, Color color) {
        GraphicsContext Graphics = this.operatingCanvas.getGraphicsContext2D();
        Graphics.setFill(color);
        Graphics.fillRect(xPosInArray * this.scaling, yPosInArray * this.scaling, this.scaling, this.scaling);
        if (renderFlag) {
            // multiply by 255 because the colors return a double from 0-1
            int r = Double.valueOf(color.getRed()*255).intValue();
            int g = Double.valueOf(color.getGreen()*255).intValue();
            int b = Double.valueOf(color.getBlue()*255).intValue();
            int pixColor = new java.awt.Color(r,g,b).getRGB();
            image.setRGB(xPosInArray,yPosInArray, pixColor);
        }
    }

    /**
     * Clear the grid of simulation by resetting all grid cells to the background color.
     */
    public void clearScreen() {
        // get canvas grphics context and clear it
        GraphicsContext Graphics = this.operatingCanvas.getGraphicsContext2D();
        Graphics.setFill(backgroundColor);
        Graphics.fillRect(0, 0, widthX, heightY);
    }

    /**
     * The simulation logic that must be implemented by the extending class.
     * Must set pixel colors using colorCell, and modify the current and
     * nextFrame grids accordingly. Use the scaledX and scaledY properties to
     * simulate
     */
    public abstract void simFrame();
}
