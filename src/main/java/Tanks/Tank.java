package Tanks;

import processing.core.PApplet;

/**
 * Represents a tank in the game, managing its position, health, fuel, power level, and interactions with terrain and other game elements.
 * This class includes methods for moving the tank, adjusting its turret, managing fuel consumption, and deploying parachutes.
 */
public class Tank {

    private char tankName; // Identifier for the tank based on characters (A, B, C, D)
    private int posX; // X-coordinate position of the tank on the grid
    private float posY; // Y-coordinate position of the tank on the grid
    private App app; // Reference to the main Processing applet for drawing
    private Terrain terrain; // Reference to the terrain for checking positions
    private Bomb bomb;  // Bomb associated with the tank
    private float speed; // Current speed of the tank
    private float turretAngle; // Current angle of the turret
    public int petrol = 250; // Amount of fuel left for the tank
    private int health = 100; // Current health of the tank 
    public int powerLevel = 50; // Current power level for shooting
    public int descentRate = 60; // Rate at which the tank descends when falling
    private Parachute parachute; // Parachute object for managing falls
    private boolean isFalling = false; // Flag to check if the tank is currently falling
    private boolean shield = false; // Flag to check if the shield is active or not
    
    /**
     * Constructs a Tank object with specified settings.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param tankName The character name of the tank.
     * @param gridX The x-coordinate on the grid where the tank is initially placed.
     * @param gridY The y-coordinate on the grid where the tank is initially placed.
     */
    public Tank(App app, char tankName, int gridX, int gridY) {
        this.app = app;
        this.tankName = tankName;
        this.posX = gridX * App.CELLSIZE;
        this.posY =  Math.round(Terrain.getYc(gridX + 15));
        this.turretAngle = PApplet.HALF_PI; 
        this.parachute = new Parachute(this, app);
    }

    /**
     * Increases the power level of the tank, ensuring it does not exceed the tank's health.
     */
    public void increasePower() {
        if (powerLevel < this.getHealth()) {
            powerLevel += 1;
            if (powerLevel >= this.getHealth()) {
                powerLevel = this.getHealth();
            }
        }
    }

    /**
     * Decreases the power level of the tank, ensuring it does not go below zero.
     */
    public void decreasePower() {
        if (powerLevel > 0) {
            powerLevel -= 1;
            if (powerLevel <= 0) {
                powerLevel = 0;
            }
        }
    }

    /**
     * Returns the current power level of the tank.
     * @return The current power level.
     */
    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * Returns the X positon of the tank.
     * @return The X position.
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Set the value of Y co ordinate for the tank.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Returns the Y positon of the tank.
     * @return The Y position.
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Returns the character of the current tank.
     * @return The tank character.
     */
    public char getTankName() {
        return tankName;
    }

    /**
     * Toggles the tank shield.
     * @param active The boolean value.
     */
    public void setShield(boolean active) {
        this.shield = active;
    }

    /**
     * Returns the boolean value of whether or not the shield is active.
     * @return The shield status.
     */
    public boolean isShieldActive() {
        return shield;
    }

    /**
     * Method to set the terrain for the tank. (Only for testing purposes)
     * @param terrain The terrain.
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     * Inflicts damage on the tank, reducing its health.
     * @param amount The amount of damage to inflict.
     */
    public void damage(int amount) {
        if (this.isShieldActive() == true) {
            amount = 0;
            this.setShield(false);
        }
        else {
            health -= amount;
            if (health < 0) {
                health = 0;
            }
        }
    }

    /**
     * Returns the current health of the tank.
     * @return The tank health.
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Sets the health for the current tank.
     * @param health Set the health.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the speed for the tank.
     * @param speed Tank's speed.
     */
    public void setSpeed(float speed) { // set the speed of the tank
        this.speed = speed;
    }

    /**
     * Sets the angle of the turret for the tank.
     * @param angle Turret angle.
     */
    public void setTurretAngle(float angle) {
        turretAngle = PApplet.constrain(angle, 0, PApplet.PI); // the turret can only rotate 180 degrees horizontally
    }

    /**
     * Returns the angle of the turret for the tank.
     * @return The turret angle.
     */
    public float getTurretAngle() {
        return turretAngle;
    }

    /**
     * Moves the tank according to its current speed and updates its position based on terrain and fuel status.
     */
    public void go() {
        int oldPos = posX;
        posX += speed / App.FPS;
        posY = Math.round(terrain.getYc(posX + 15));
        float distance = Math.abs(posX - oldPos);
        petrolOver((int) distance);
        if (petrol <= 0) { // if fuel is empty then the tank should stop moving
            posX = oldPos;
            speed = 0;
        }
        if (posY > 640) { // destroy the tank if it goes below the map
            try {
                bomb.destroyTerrain();
            } catch (NullPointerException e) {
                System.err.println("Tank did not explode even after going below the terrain.");      
            }
        }
    }

    /**
     * Sets the fuel for the tank.
     * @param petrol The fuel for the tank.
     */
    public void setPetrol(int petrol) {
        this.petrol = petrol;
    }
    
    /**
     * Calculates the amount of petrol consumed on the distance travelled.
     * @param distance The distance travelled.
     */
    public void petrolOver (int distance) { // fuel reduces by one unit as the tank travels one pixel horizontally
        petrol -= distance;
        petrol = Math.max(petrol, 0);
    }

    /**
     * Returns the amount of petrol left for the tank.
     * @return Fuel
     */
    public int remainingPetrol() {
        return petrol;
    }

    /**
     * Updates the tank's state, checking for mid-air conditions and managing parachute deployment.
     */
    public void update() {
        if (this.posY + 10 < terrain.getYc(this.posX + 15) && !parachute.isDeployed()) {
            // when mid air
            isFalling = true;
            parachute.deployParachute();
        } else if (this.posY > terrain.getYc(this.posX + 15)) {
            parachute.isDeployed = false;
            isFalling = false;
        }

        if (isFalling) {
            this.posY += descentRate / App.FPS;
        }

        if (parachute.isDeployed) {
            parachute.displayParachute();
        }
    }

    /**
     * Sets the rate the tank falls down when the terrain below it is destroyed.
     * @param rate Rate of descent
     */
    public void setDescentRate(int rate) {
        this.descentRate = rate;
        this.isFalling = true;
    }

    /**
     * Returns the terrain.
     * @return The terrain.
     */
    public Terrain getTerrain() {
        return this.terrain;
    }

    /**
     * Displays the tank on the canvas, using colors and shapes specified for each tank type.
     */
    public void displayTanks() {
        switch(tankName) {
            case 'A':
                tankA();
                break;
            case 'B':
                tankB();
                break;
            case 'C':
                tankC();
                break;
            case 'D':
                tankD();
                break;
        }
    }

    /**
     * Create tank A in the game.
     */
    private void tankA() {
        float iX = this.posX; // posX already contains the converted x-coordinate
        float iY = this.posY;
        if (!isFalling) {
            iY = terrain.getYc(iX + 15); // posY already contains the converted y-coordinate, adjusted for the tank height
        }
        app.fill(0, 0, 255); // blue colour
        app.rect(iX, iY, App.CELLSIZE, App.CELLSIZE / 4, 40); // draw base of tank
        app.rect(iX + (App.CELLSIZE - App.CELLSIZE / 4) / 4, iY - App.CELLSIZE / 4, App.CELLSIZE / 1.5f, App.CELLSIZE / 4, 40); // draw top of tank
        app.stroke(0);
        app.strokeWeight(4);
        drawTurret();
    }

     /**
     * Create tank B in the game.
     */
    private void tankB() {
        app.noStroke();
        float iX = this.posX; // posX already contains the converted x-coordinate
        float iY = this.posY;
        if (!isFalling) {
            iY = terrain.getYc(iX + 15); // posY already contains the converted y-coordinate, adjusted for the tank height
        }
        app.fill(255, 0, 0); // red colour
        app.rect(iX, iY, App.CELLSIZE, App.CELLSIZE / 4, 40); // draw base of tank
        app.rect(iX + (App.CELLSIZE - App.CELLSIZE / 4) / 4, iY - App.CELLSIZE / 4, App.CELLSIZE / 1.5f, App.CELLSIZE / 4, 40); // draw top of tank
        app.stroke(0);
        app.strokeWeight(4);
        drawTurret();
    }

     /**
     * Create tank C in the game.
     */
    private void tankC() {
        app.noStroke();
        float iX = this.posX; // posX already contains the converted x-coordinate
        float iY = this.posY;
        if (!isFalling) {
            iY = terrain.getYc(iX + 15); // posY already contains the converted y-coordinate, adjusted for the tank height
        }
        app.fill(0, 255, 255); // cyan colour
        app.rect(iX, iY, App.CELLSIZE, App.CELLSIZE / 4, 40); // draw base of tank
        app.rect(iX + (App.CELLSIZE - App.CELLSIZE / 4) / 4, iY - App.CELLSIZE / 4, App.CELLSIZE / 1.5f, App.CELLSIZE / 4, 40); // draw top of tank
        app.stroke(0);
        app.strokeWeight(4);
        drawTurret();
    }

     /**
     * Create tank D in the game.
     */
    private void tankD() {
        float iX = this.posX; // posX already contains the converted x-coordinate
        float iY = this.posY;
        if (!isFalling) {
            iY = terrain.getYc(iX + 15); // posY already contains the converted y-coordinate, adjusted for the tank height
        }
        app.noStroke();
        app.fill(255, 255, 0); // yellow colour
        app.rect(iX, iY, App.CELLSIZE, App.CELLSIZE / 4, 40); // draw base of tank
        app.rect(iX + (App.CELLSIZE - App.CELLSIZE / 4) / 4, iY - App.CELLSIZE / 4, App.CELLSIZE / 1.5f, App.CELLSIZE / 4, 40); // draw top of tank
        app.stroke(0);
        app.strokeWeight(4);
        drawTurret();
    }

     /**
     * Draws the turret for the tanks.
     */
    private void drawTurret() {
        float iX = this.posX + App.CELLSIZE / 2; // posX already contains the converted x-coordinate
        float iY = this.posY;
        if (!isFalling) {
            iY = terrain.getYc(iX) - 5; // posY already contains the converted y-coordinate, adjusted for the tank height
        }
        float turretLength = 15; // turret length in pixels
        // endpoints below
        float turretEndX = iX + turretLength * PApplet.cos(turretAngle);
        float turretEndY = iY - turretLength * PApplet.sin(turretAngle);
        app.stroke(0);
        app.strokeWeight(4);
        app.line(iX, iY, turretEndX, turretEndY);
    }

    /**
     * Returns the color associated with the tank based on its identifier.
     * @return The color of the tank.
     */
    public int getColor() {
        switch (tankName) {
            case 'A':
                return app.color(0, 0, 255); // Blue
            case 'B':
                return app.color(255, 0, 0); // Red
            case 'C':
                return app.color(0, 255, 255); // Cyan
            case 'D':
                return app.color(255, 255, 0); // Yellow
            default:
                return app.color(255, 255, 255);
        }
    }

     /**
     * Returns the number of parachutes left.
     * @return The number of parachutes left.
     */
    public Parachute getParachute() {
        return parachute;
    }
    
}

