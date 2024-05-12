package Tanks;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * Represents the terrain in the tank game, including the graphical representation,
 * terrain obstacles, and specific locations for trees and tanks.
 * This class manages the loading and rendering of different terrains based on the level.
 */
public class Terrain {

    private PApplet parent; // The parent PApplet instance for drawing
    private PImage treeImage; // Image of the tree used in the terrain
    private App app; // Reference to the main application
    private char[][] terrainGrid; // Grid representing the terrain map

    // Static lists to store dynamic elements and their positions on the terrain
    public static ArrayList<Float> heights = new ArrayList<>(); // ArrayList storing the heights of characters read from the text file.
    public static ArrayList<Float> treeX = new ArrayList<>(); // ArrayList storing the X co ordinates of trees read from the text file.
    public static ArrayList<Integer> tankAX = new ArrayList<>(); // ArrayList storing the X co ordinates for tank A.
    public static ArrayList<Integer> tankAY = new ArrayList<>(); // ArrayList storing the Y co ordinates for tank A.
    public static ArrayList<Integer> tankBX = new ArrayList<>(); // ArrayList storing the X co ordinates for tank B.
    public static ArrayList<Integer> tankBY = new ArrayList<>(); // ArrayList storing the Y co ordinates for tank B.
    public static ArrayList<Integer> tankCX = new ArrayList<>(); // ArrayList storing the X co ordinates for tank C.
    public static ArrayList<Integer> tankCY = new ArrayList<>(); // ArrayList storing the Y co ordinates for tank C.
    public static ArrayList<Integer> tankDX = new ArrayList<>(); // ArrayList storing the X co ordinates for tank D.
    public static ArrayList<Integer> tankDY = new ArrayList<>(); // ArrayList storing the Y co ordinates for tank D.

    /**
     * Constructs a Terrain object, loads tree images based on the current level.
     * @param parent The parent PApplet instance for drawing on the canvas.
     * @param app The main application object for accessing game settings and level information.
     */
    public Terrain(PApplet parent, App app) {
        this.parent = parent;
        this.app = app;
        loadTreeImage();
    }

     /**
     * Calculates and returns the y-coordinate for a given x-coordinate, interpolating if necessary.
     * @param x The x-coordinate for which to find the corresponding y-coordinate.
     * @return The interpolated y-coordinate based on the current terrain heights.
     */
    public static float getYc(float x) {
        int index = Math.round(x);
        if (index < 0) {
            index = 0;
        }
        if (index >= heights.size()) { // check if index is within the bounds of the list
            index = heights.size() - 1;
        }
        return heights.get(index); // return the y-coordinate from the heights list
    }

    /**
     * Loads the tree image based on the current level.
     */
    public void loadTreeImage() { // display the correct tree based on the map
        String treeImagePath;
        if (app.getCurrentLevel() == 3) {
            treeImagePath = "src/main/resources/Tanks/tree1.png";
        } 
        else {
            treeImagePath = "src/main/resources/Tanks/tree2.png";
        }
        this.treeImage = parent.loadImage(treeImagePath);
    }

    /**
     * Loads and processes terrain data from a file.
     * @param levelPath The file path to the terrain data for the level.
     */
    public void loadTerrain(String levelPath) {
        String[] lines = parent.loadStrings(levelPath);
        terrainGrid = new char[App.BOARD_WIDTH][lines.length]; // fill 2d array with dimensions
        for (int j = 0; j < 27; j++) { // loop through columns first
            for (int i = 0; i < lines.length; i++) { // loop through rows now after columns
                if (j < lines[i].length()) { // check if the current column exists in this row
                    terrainGrid[j][i] = lines[i].charAt(j); // col by row access
                    if (terrainGrid[j][i] == 'X') {
                        for (int k = 0; k < 32; k++) {
                            heights.add((float) i * App.CELLHEIGHT); // add heights to the list based on row position
                        }
                    }
                    else if (terrainGrid[j][i] == 'T') {
                        treeX.add((float) j * App.CELLSIZE);
                    }
                    else if (terrainGrid[j][i] == 'A' || terrainGrid[j][i] == 'B' || terrainGrid[j][i] == 'C' || terrainGrid[j][i] == 'D') {
                        switch (terrainGrid[j][i]) {
                            case 'A':
                                tankAX.add(j);
                                tankAY.add(i);
                                break;
                            case 'B':
                                tankBX.add(j);
                                tankBY.add(i);
                                break;
                            case 'C':
                                tankCX.add(j);
                                tankCY.add(i);
                                break;
                            case 'D':
                                tankDX.add(j);
                                tankDY.add(i);
                                break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Applies a moving average filter to the terrain heights to smooth out the graphical representation.
     * @param heights The list of height values for each point on the terrain.
     */
    public static void applyMovingAverage(ArrayList<Float> heights) { // smooth the terrain
        ArrayList<Float> transformed = new ArrayList<>(heights.size());
        int window = 32;
        for (int i = 0; i < heights.size(); i++) {
            float sum = 0;
            int count = 0;
            try{
                for (int j = i; j < Math.min(heights.size(), i + window); j++) { // calculate sum of next 32 elements
                    sum += heights.get(j);
                    count++;
                }
            }
            catch (NullPointerException e) {

            }
            transformed.add(sum / count); // add avg to list
        }
        heights.clear();
        heights.addAll(transformed); // update the main list (i suck at for loop)
    }

    /**
     * Displays the terrain based on the currently loaded heights and tree positions.
     * Colors and details are adjusted based on the current game level.
     */
    public void displayTerrain() {
        if (app.getCurrentLevel() == 1) {
            parent.fill(255); // white colour
        }
        else if (app.getCurrentLevel() == 2) {
            parent.fill(234,221,181); // beige
        }
        else if (app.getCurrentLevel() == 3) {
            parent.fill(120,171,0); // green
        }
        parent.noStroke(); // no black lines
        for (int x = 0; x < heights.size(); x++) {
            float y = heights.get(x);
            parent.rect(x, y, 1, App.HEIGHT - y);
        }
        for (float x : treeX) {
            parent.image(treeImage, x, heights.get((int) x + 15) - 32, App.CELLSIZE, App.CELLHEIGHT);
        }
    }
    
}

