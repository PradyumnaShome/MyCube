// My implementation of the Kuku Kube eyesight-testing game that was popular in 2015.
// This app implements all of the gameplay except the countdown timer.
// Pradyumna Shome
// May 2015

package src;

import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.*;

public class MyCube implements MouseListener
{
    private static JFrame frame;
    private static JPanel panel;
    
    private static MyCube app;
    
    private static GameManager manager;
    
    private static JButton[][] cubes;

    public static void main(String[] args)
    {
        manager = new GameManager();
        frame = new JFrame("MyCube");
        
        int gridDimension = manager.getGridDimension();
        panel = new JPanel(new GridLayout(gridDimension, gridDimension));
        
        app = new MyCube();
        frame.setContentPane(panel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
        
        cubes = constructUi();
    }

    /**
     * Removes all of the cubes from the game board.
     */
    private static void clearBoard(JButton[][] cubes) {
        int currentGridDimension = manager.getGridDimension();
        for(int i = 0; i < currentGridDimension; i++) {
            for(int j = 0; j < currentGridDimension; j++) {   
                panel.remove(cubes[i][j]);
                cubes[i][j] = null;                             
            }
        }
    }

    /**
     * Populates the game board (i.e. the global JButton[][]) with cubes.
     * Assigns colors based on the current state of GameManager, as well as adds MouseListeners.
     */
    private static void populateGameBoard(JButton[][] cubes) {
        int currentGridDimension = manager.getGridDimension();
        for(int i = 0; i < currentGridDimension; i++) {
            for(int j = 0;j < currentGridDimension; j++) {
                cubes[i][j] = new JButton(i + "," + j);
                cubes[i][j].addMouseListener(app);
                cubes[i][j].setBackground(manager.getNormalColor());
                panel.add(cubes[i][j]);
            }
        }
    }

    /**
     * Returns the cube with the variant color.
     */
    private static JButton getTargetCube(JButton[][] cubes) {
        int[] targetCubeCoordinates = manager.getTargetCubeCoordinates();
        return cubes[targetCubeCoordinates[0]][targetCubeCoordinates[1]];
    }

    private static void printTargetCubeCoordinates(JButton[][] cubes) {
        System.out.println(getTargetCube(cubes).getText());
    }

    /**
     * Initializes the UI with cubes, attached mouse listeners etc., and returns the grid of cubes.
     */
    private static JButton[][] constructUi()
    {   
        int currentGridDimension = manager.getGridDimension();

        JButton[][] cubes = new JButton[currentGridDimension][currentGridDimension];
        
        // Create a new GridLayout to place cubes in.
        panel.setLayout(new GridLayout(currentGridDimension,currentGridDimension));
        
        manager.assignNewCubeColors();
        
        populateGameBoard(cubes);
    
        // Choose a new target cube.
        manager.shuffleTargetCubeCoordinates();
        
        // Subtly change the color of target cube.
        getTargetCube(cubes).setBackground(manager.getVariantColor());

        // Force refresh UI, to ensure butts are re-rendered correctly.
        SwingUtilities.updateComponentTreeUI(frame);

        // For debugging purposes, and a way to cheat ;)
        printTargetCubeCoordinates(cubes);

        return cubes;
    }

    /**
     * Update the UI based on the number of clicks, as well as the correctness of clicks.
     */
    private static void updateBoard() {

        clearBoard(cubes);

        if(manager.isGameOver()) {
            JOptionPane.showConfirmDialog(frame, "Game over!");
            return;
        }


        manager.updateBoardSize();
        
        cubes = constructUi();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        JButton targetCube = getTargetCube(cubes); 
        if(e.getSource() == targetCube)
        {
            manager.incrementTimesClicked();
            updateBoard();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}