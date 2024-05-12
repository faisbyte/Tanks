package Tanks;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a parachute for a tank, managing deployment and effects on the tank during falls.
 */
public class Parachute {
    
    private Tank tank; // The tank associated with this parachute
    private PApplet app; // Reference to the Processing applet for drawing and access to resources
    private PImage chute; // Image of the parachute
    public boolean isDeployed; // Flag to check if the parachute is currently deployed
    private int remainingParachutes; // Number of parachutes left for deployment
    private static final int descentRateWithParachute = 60; // Descent rate in pixels per second with parachute
    private static final int descentRateWithoutParachute = 120; // Descent rate in pixels per second without parachute
    private static final int damagePerPixel = 1; // Damage taken per pixel fallen without parachute

    /**
     * Constructs a Parachute object, initializing it with a tank, the application context, and loading the parachute image.
     * @param tank The tank associated with this parachute.
     * @param app The main Processing applet instance used for drawing and handling game logic.
     */
    public Parachute(Tank tank, PApplet app) {
        this.tank = tank;
        this.app = app;
        this.isDeployed = false;
        this.remainingParachutes = 3;
        this.chute = app.loadImage("src/main/resources/Tanks/parachute.png");
    }

     /**
     * Sets the number of parachutes available for deployment.
     * @param p The number of parachutes to set.
     */
    public void setParachutes(int p) {
        this.remainingParachutes = p;
    }

    /**
     * Updates the status of the parachute during the game loop, handling deployment and free fall effects.
     */
    public void updateParachute() {
        if (isTankInMidair()) {
            if (remainingParachutes > 0 && !isDeployed) {
                deployParachute();
            } else {
                handleFreeFall();
            }
        } else {
            resetParachute();
        }
    }

    /**
     * Checks if the associated tank is currently in midair above the terrain.
     * @return true if the tank is in midair, false otherwise.
     */
    public boolean isTankInMidair() {
        float terrainHeight = tank.getTerrain().getYc(tank.getPosX() + 15);
        return tank.getPosY() > terrainHeight;
    }

    /**
     * Deploys a parachute if available, reducing the descent rate of the tank and decrementing the count of remaining parachutes.
     */
    public void deployParachute() {
        isDeployed = true;
        remainingParachutes--;
        if (remainingParachutes < 0) {
            remainingParachutes = 0;
        }
        tank.setDescentRate(descentRateWithParachute);
        System.out.println(remainingParachutes);
    }

     /**
     * Handles the tank's free fall when no parachute is deployed, applying damage based on the fall speed.
     */
    public void handleFreeFall() {
        if (!isDeployed) {
            tank.setDescentRate(descentRateWithoutParachute);
            tank.damage(damagePerPixel * (descentRateWithoutParachute / App.FPS));
        }
    }

    /**
     * Resets the parachute when the tank is no longer in midair, preparing it for potential redeployment.
     */
    public void resetParachute() {
        isDeployed = false;
        tank.setDescentRate(0);
    }

    /**
     * Displays the parachute image above the tank when deployed.
     */
    public void displayParachute() {
        if (isDeployed) {
            app.image(chute, tank.getPosX() - 15, tank.getPosY() - 60); // Adjust positioning as necessary
        }
    }

    /**
     * Returns the deployment status of the parachute.
     * @return true if the parachute is deployed, false otherwise.
     */
    public boolean isDeployed() {
        return isDeployed;
    }

    /**
     * Returns the number of remaining parachutes.
     * @return The number of parachutes available for deployment.
     */
    public int getRemainingParachutes() {
        return remainingParachutes;
    }

}

