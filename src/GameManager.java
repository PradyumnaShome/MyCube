package src;

import java.util.Random;
import java.awt.Color;

/**
 * Manages the entire game state, including turns played, the colors of various cubes, grid dimensions, and game difficulty.
 */
public class GameManager {
    public static final int MAX_GRID_SIZE = 10; 
    public static final int NUM_COLOR_COMPONENTS = 3;
    public static final int NUM_RGB_VALUES = 256;
    private static final int STARTING_SMOOTH_AMOUNT = 35;

    private int gridDimension;
    private int timesClicked;
    private int[] targetCubeCoordinates;
    private Random r;

    // Normal, and variant colors currently being being used.
    private Color normalColor;
    private Color variantColor;
    
    // Amount the target cube's color differs from the rest. Gradually diminishes over time to make the game harder.
    private int smoothAmount;

    public GameManager() {
        r = new Random();
        gridDimension = 2;
        targetCubeCoordinates = new int[2];
        smoothAmount = STARTING_SMOOTH_AMOUNT;
        assignNewCubeColors();
    }

    /**
     * Assigns new colors. 
     * First, it randomly arrives at one color. 
     * Next, the variant color is synthesised by reducing a "smooth" value from R,G and B of the original color.
     */
    public void assignNewCubeColors() {
        int[] actualRgb = new int[3];
        int[] changedRgb = new int[3];

        for(int i = 0; i < NUM_COLOR_COMPONENTS; i++)
        {        
            actualRgb[i] = r.nextInt(NUM_RGB_VALUES);
            if(actualRgb[i] <= 50) {
                actualRgb[i] += r.nextInt(220);
            }     
            changedRgb[i] = actualRgb[i] - smoothAmount;
        }
        
        normalColor = new Color(actualRgb[0], actualRgb[1], actualRgb[2]);
        variantColor = new Color(changedRgb[0], changedRgb[1], changedRgb[2]);
    }

    public Color getNormalColor() {
        return normalColor;
    }

    public Color getVariantColor() {
        return variantColor;
    }

    /**
     * @returns A two element array containing the target cube's coordinates.
     */
    public int[] getTargetCubeCoordinates() {
        return targetCubeCoordinates;
    }

    public int getGridDimension() {
        return gridDimension;
    }

    /**
     * Returns true if game is over, false if not.
     */
    public boolean isGameOver() {
        return gridDimension >= MAX_GRID_SIZE;
    }

    /**
     * Based on player progress, makes the game harder by reducing the difference between the normal and variant colors.
     * It also increases the grid size, every other turn.
     */
    public void updateBoardSize() {
        if(timesClicked <= 5) {
            smoothAmount -= 4;   
        } else if(timesClicked > 5 && timesClicked <= 10) {
            smoothAmount -= 3;
        } else if(timesClicked > 10) {
            smoothAmount -= 2;
        }
        if(timesClicked % 2 == 0) {
            gridDimension++;
        }
    }

    public void incrementTimesClicked() {
        timesClicked++;
    }

    public int getTimesClicked() {
        return timesClicked;
    }

    public void setTimesClicked(int timesClicked) {
        this.timesClicked = timesClicked;
    }

    /**
     * Choose two random x and y-coordinates for the target cube.
     */
    public void shuffleTargetCubeCoordinates() {
        targetCubeCoordinates[0] = r.nextInt(gridDimension);
        targetCubeCoordinates[1] = r.nextInt(gridDimension);
    }
}