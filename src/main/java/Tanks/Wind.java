package Tanks;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the wind in the game, managing its speed and the display of wind indicators.
 */
public class Wind {

    private PApplet app; // Reference to the Processing applet for drawing and access to game resources
    public int windSpeed; // Current wind speed affecting the game elements
    private PImage icon; // Icon for displaying positive wind direction
    private PImage icon2; // Icon for displaying negative wind direction
    private static final int fastWind = 35; // Maximum positive wind speed
    private static final int slowWind = -35; // Maximum negative wind speed
    private static final int fastChange = 5; // Maximum increase per wind change
    private static final int slowChange = -5; // Maximum decrease per wind change

    /**
     * Constructs a Wind object with a random initial wind speed and loads related images.
     * @param app The main Processing applet instance for drawing and handling game logic.
     */
    public Wind(PApplet app) {
        this.app = app;
        this.icon = app.loadImage("src/main/resources/Tanks/wind.png");
        this.icon2 = app.loadImage("src/main/resources/Tanks/wind-1.png");
        this.windSpeed = (int) app.random(slowWind, fastWind);
    }

    /**
     * Updates the wind speed by a random amount within the defined change limits and constrains it within the maximum and minimum speeds.
     */
    public void updateWind() {
        this.windSpeed += app.random(slowChange, fastChange);
        this.windSpeed = App.constrain(this.windSpeed, slowWind, fastWind);
    }

    /**
     * Returns the current wind speed.
     * @return Current wind speed as a float.
     */
    public float getSpeed() { // return wind speed
        return this.windSpeed;
    }

    /**
     * Displays the wind indicator icon based on the current wind speed direction and the actual speed value on the screen.
     */
    public void displayWind() { // displays the correct wind icon based on the direction of wind
        if (windSpeed < 0) {
            app.image(icon2, 770, 10, 50, 50);
        }
        else {
            app.image(icon, 770, 10, 50, 50);
        }
        //updateWind();
        app.fill(0);
        app.textSize(15);
        int ws = windSpeed;
        app.text(ws, 830, 35);
    }

}

