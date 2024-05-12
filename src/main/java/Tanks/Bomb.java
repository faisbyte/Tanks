package Tanks;

import java.util.ArrayList;
import java.util.Iterator;
import processing.core.PApplet;

/**
 * Represents a bomb in the game, managing its physics, display, and interactions with the terrain and tanks.
 */
public class Bomb {

    private PApplet app; // Reference to the Processing applet for drawing and access to game constants
    private float x; // X-coordinate of the bomb's current position
    private float y; // Y-coordinate of the bomb's current position
    private float xvelo; // Horizontal velocity of the bomb
    private float yvelo; // Vertical velocity of the bomb, affected by gravity
    private static final float g = 0.1f; // Gravity constant
    private int color; // Color of the bomb
    private char firingTank; // Identifier of the tank that fired the bomb
    private int RED; // Red color constant
    private int ORANGE; // Orange color constant
    private int YELLOW; // Yellow color constant
    
    /**
     * Constructs a Bomb object with specified initial settings.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param x Initial x-coordinate of the bomb.
     * @param y Initial y-coordinate of the bomb.
     * @param angle The firing angle of the bomb.
     * @param powerLevel The power level affecting the bomb's initial speed.
     * @param colour The color of the bomb.
     * @param c The character identifier of the firing tank.
     */
    public Bomb (PApplet app, float x, float y, float angle, float powerLevel, int colour, char c) {
        this.app = app;
        this.x = x;
        this.y = y;
        float u = 1 + 8 * (powerLevel / 100); // b/w 1 and 9 pixels per frame
        this.xvelo = u * PApplet.cos(angle);
        this.yvelo = -u * PApplet.sin(angle);
        this.color = colour;
        this.RED = app.color(255, 0, 0);
        this.ORANGE = app.color(255, 165, 0);
        this.YELLOW = app.color(255, 255, 0);
        this.firingTank = c;
    }

    /**
     * Returns the identifier of the tank that fired the bomb.
     * @return The firing tank's identifier.
     */
    public char getFiringTank() { // returns the tank which fired the bomb
        return firingTank;
    }

    /**
     * Updates the position and velocity of the bomb according to physics calculations.
     */
    public void update() {
        // update pos based on velocity
        x += xvelo;
        y += yvelo;
        yvelo += g; // apply gravity
        xvelo += App.wind.getSpeed() * 0.03 / app.frameRate; // add wind
    }

    /**
     * Displays the bomb on the screen.
     */
    public void displayBomb () {
        app.noStroke();
        app.fill(color);
        app.ellipse(x, y, 10, 10);
    }

    /**
     * Displays the explosion effects when the bomb detonates.
     */
    public void displayExplosion() { // display the explosion on the screen
        float explosionRing = 30;
        float time = 0.2f * app.frameRate; // 0.2 seconds
        float currentFrame = app.frameCount % time; // current frame in the bomb blasting
        float red = PApplet.map(currentFrame, 0, time, 0, explosionRing);
        float orange = PApplet.map(currentFrame, 0, time, 0, explosionRing * 0.5f);
        float yellow = PApplet.map(currentFrame, 0, time, 0, explosionRing * 0.2f);
        app.noStroke();
        app.fill(RED);
        app.ellipse(x, y, red * 2, red * 2);
        app.fill(ORANGE);
        app.ellipse(x, y, orange * 2, orange * 2);
        app.fill(YELLOW);
        app.ellipse(x, y, yellow * 2, yellow * 2);
    }

    /**
     * Destroys the terrain where the bomb impacts, modifying the terrain heights.
     */
    public void destroyTerrain() { // destroy the terrain
        final float r = 30f;
        for (int i = (int) Math.max(0, x - (int) r); i < Math.min(Terrain.heights.size(), x + (int) r); i++) {
          float d = Math.abs(i - x);
          float semiCircleHeight = (float) Math.sqrt(Math.max(0, r * r - d * d));
          if (Terrain.heights.get(i) < y + semiCircleHeight && Terrain.heights.get(i) > y - semiCircleHeight) {
            Terrain.heights.set(i, y + semiCircleHeight);
            }
          else if (Terrain.heights.get(i) < y - semiCircleHeight) {
            Terrain.heights.set(i, Terrain.heights.get(i) + 2 * semiCircleHeight);
            Terrain.heights.set(i, Math.min(Terrain.heights.get(i), y + semiCircleHeight));
            }
        }
    }

    /**
     * Checks if the bomb has gone outside the boundaries of the game area.
     * @return True if the bomb is outside the game area, false otherwise.
     */
    boolean outside() {
        return x < 0 || x > App.WIDTH || y > App.HEIGHT; // check if the bomb went outside the screen
    }

     /**
     * Constructs a Bomb for tank A.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param x Initial x-coordinate of the bomb.
     * @param y Initial y-coordinate of the bomb.
     * @param angle The firing angle of the bomb.
     * @param power The power of the bomb being fired.
     */
    public static Bomb bombA(PApplet app, float x, float y, float angle, int power) {
        return new Bomb(app, x, y, angle, power, app.color(0, 0, 255), 'A'); // blue
    }

    /**
     * Constructs a Bomb for tank B.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param x Initial x-coordinate of the bomb.
     * @param y Initial y-coordinate of the bomb.
     * @param angle The firing angle of the bomb.
     * @param power The power of the bomb being fired.
     */
    public static Bomb bombB(PApplet app, float x, float y, float angle, int power) {
        return new Bomb(app, x, y, angle, power, app.color(255, 0, 0), 'B'); // red
    }

    /**
     * Constructs a Bomb for tank C.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param x Initial x-coordinate of the bomb.
     * @param y Initial y-coordinate of the bomb.
     * @param angle The firing angle of the bomb.
     * @param power The power of the bomb being fired.
     */
    public static Bomb bombC(PApplet app, float x, float y, float angle, int power) {
        return new Bomb(app, x, y, angle, power, app.color(0, 255, 255), 'C'); // cyan
    }

    /**
     * Constructs a Bomb for tank D.
     * @param app The main Processing applet instance for drawing and handling game logic.
     * @param x Initial x-coordinate of the bomb.
     * @param y Initial y-coordinate of the bomb.
     * @param angle The firing angle of the bomb.
     * @param power The power of the bomb being fired.
     */
    public static Bomb bombD(PApplet app, float x, float y, float angle, int power) {
        return new Bomb(app, x, y, angle, power, app.color(255, 255, 0), 'D'); // yellow
    }

    /**
     * Gets the current x-coordinate of the bomb.
     * @return Current x-coordinate.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the current y-coordinate of the bomb.
     * @return Current y-coordinate.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Handles damage to tanks within the explosion radius and removes destroyed tanks.
     * @param tanks The list of tanks to check and update based on the explosion.
     */
    public void destroyTank(ArrayList<Tank> tanks) { // remove the tank from the game if it falls below the map or health is 0
        float radius = 30f;
        Iterator<Tank> iterator = tanks.iterator();
        while (iterator.hasNext()) {
            Tank tank = iterator.next();
            float dist = PApplet.dist(x, y, tank.getPosX(), tank.getPosY());
            if (dist < radius) {
                int damage = (int) ((1 - (dist / radius)) * 60);
                int oldHealth = tank.getHealth();
                tank.damage(damage);
                int healthLost = oldHealth - tank.getHealth();
                if (firingTank != tank.getTankName()) { // only update score if the damaged tank is not the firing tank
                    ((App) app).updateScore(firingTank, healthLost);
                }
                if (tank.getHealth() <= 0) { // destroy the tank with an explostion radius of 15
                    final float r = 15f;
                    for (int i = (int) Math.max(0, x - (int) r); i < Math.min(Terrain.heights.size(), x + (int) r); i++) {
                    float d = Math.abs(i - x);
                    float semiCircleHeight = (float) Math.sqrt(Math.max(0, r * r - d * d));
                    if (Terrain.heights.get(i) < y + semiCircleHeight && Terrain.heights.get(i) > y - semiCircleHeight) {
                        Terrain.heights.set(i, y + semiCircleHeight);
                        }
                    else if (Terrain.heights.get(i) < y - semiCircleHeight) {
                        Terrain.heights.set(i, Terrain.heights.get(i) + 2 * semiCircleHeight);
                        Terrain.heights.set(i, Math.min(Terrain.heights.get(i), y + semiCircleHeight));
                        }
                    }
                    iterator.remove(); // remove tank 
                }
            }
        }
    }

}
