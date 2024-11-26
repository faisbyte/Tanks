package Tanks;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.util.*;

import org.json.JSONObject;

/**
 * The main application class for the tank game, managing game initialization,
 * input handling, game state updates, and rendering.
 * This class extends PApplet, using the Processing library to handle graphics and UI events.
 */
public class App extends PApplet {

    // Constants for game configuration
    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;
    public static final int CELLAVG = 32;
    public static final int TOPBAR = 0;
    public static int WIDTH = 864; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE;
    public static final int BOARD_HEIGHT = 20;
    public static final int INITIAL_PARACHUTES = 1;
    public static final int FPS = 30;

    // Game state variables
    public int currentTankIndex = 0;  // Index of the current tank in play
    private ArrayList<Bomb> bombs = new ArrayList<>();  // List of active bombs
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftArrowPressed = false;
    private boolean rightArrowPressed = false;

    // Game components
    public Terrain terrain;
    public ArrayList<Tank> tanks;
    public HUD hud;
    public static Wind wind;
    public Powerups powerup;
    private PImage backgroundImage;
    public static Random random = new Random(); 

    // Scores for individual tanks
    private int AScore = 0;
    private int BScore = 0;
    private int CScore = 0;
    private int DScore = 0;

    // Level management
    public int currentLevel = 1;
    private final int maxLevel = 3;
    public boolean gameEnded = false;

    // Configurations
    public String configPath; // Path to the configuration file, not used in current implementation
    public processing.data.JSONObject jsonData;

	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    /**
     * Constructor for the game application, initializes configuration path and tank list.
     */
    public App() {
        this.configPath = "config.json";
        tanks = new ArrayList<>();
    }

    /**
     * Returns the character of the current tank.
     * @return Current tank character.
     */
    public char getCurrentTankChar() {
        return tanks.get(currentTankIndex).getTankName(); // get current tanks character ie a b c or d
    }

    /**
     * Returns the current level the game is running on.
     * @return Game level.
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Loads the level configuration from files, sets up the terrain and tanks for the specified level.
     * @param level The level number to load.
     */
    public void loadLevel(int level) {
        String terrainFile = "";
        String backgroundImageFile = "";
        if (level == 1) {
            terrainFile = "level1.txt";
            String backgroundImageFileUrl = jsonData.getJSONArray("levels").getJSONObject(level - 1).getString("background");
            backgroundImageFile = getClass().getResource(backgroundImageFileUrl).getPath().replace("%20", " ");
        }
        else if (level == 2) {
            terrainFile = "level2.txt";
            String backgroundImageFileUrl = jsonData.getJSONArray("levels").getJSONObject(level - 1).getString("background");
            backgroundImageFile = getClass().getResource(backgroundImageFileUrl).getPath().replace("%20", " ");
        }
        else if (level == 3) {
            terrainFile = "level3.txt";
            String backgroundImageFileUrl = jsonData.getJSONArray("levels").getJSONObject(level - 1).getString("background");
            backgroundImageFile = getClass().getResource(backgroundImageFileUrl).getPath().replace("%20", " ");
        }
        Terrain.tankAX.clear();
        Terrain.tankBX.clear();
        Terrain.tankCX.clear();
        Terrain.tankDX.clear();
        Terrain.tankAY.clear();
        Terrain.tankBY.clear();
        Terrain.tankCY.clear();
        Terrain.tankDY.clear();
        Terrain.heights.clear();
        Terrain.treeX.clear();
        bombs.clear();

        backgroundImage = loadImage(backgroundImageFile);
        terrain = new Terrain(this, this);
        if (terrain != null) {
            terrain.loadTerrain(terrainFile);
            Terrain.applyMovingAverage(Terrain.heights);
            Terrain.applyMovingAverage(Terrain.heights); 
        }
        initializeTanks();
    }

    /**
     * Initializes tanks based on terrain data.
     */
    public void initializeTanks() {
        tanks.clear();
        for (int i = 0; i < Terrain.tankAX.size(); i++) {
            tanks.add(new Tank(this, 'A', Terrain.tankAX.get(i), Terrain.tankAY.get(i)));
        }
        for (int i = 0; i < Terrain.tankBX.size(); i++) {
            tanks.add(new Tank(this, 'B', Terrain.tankBX.get(i), Terrain.tankBY.get(i)));
        }
        for (int i = 0; i < Terrain.tankCX.size(); i++) {
            tanks.add(new Tank(this, 'C', Terrain.tankCX.get(i), Terrain.tankCY.get(i)));
        }
        for (int i = 0; i < Terrain.tankDX.size(); i++) {
            tanks.add(new Tank(this, 'D', Terrain.tankDX.get(i), Terrain.tankDY.get(i)));
        }
    }
    

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        jsonData = loadJSONObject(configPath);
        frameRate(FPS);
        loadLevel(currentLevel);
        hud = new HUD(this);
        wind = new Wind(this);
        powerup = new Powerups(this);
    }

    /**
     * Handles key press events for controlling tanks and interacting with the game.
     * @param event The KeyEvent object containing details of the key pressed.
     */
	@Override
    public void keyPressed(KeyEvent event){
        super.keyPressed(event);
        try{
            Tank tank = tanks.get(currentTankIndex);
            if (event.getKeyCode() == 'W') {
                tank.increasePower();
            }
            else if (event.getKeyCode() == 'S') {
                tank.decreasePower();
            }
            if (getCurrentTankChar() == 'A' && event.getKeyCode() == LEFT) { // checking for movements only if it is the current tanks' turn
                leftArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'A' && event.getKeyCode() == RIGHT) {
                rightArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'B' && event.getKeyCode() == LEFT) {
                leftArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'B' && event.getKeyCode() == RIGHT) {
                rightArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'C' && event.getKeyCode() == LEFT) {
                leftArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'C' && event.getKeyCode() == RIGHT) {
                rightArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'D' && event.getKeyCode() == LEFT) {
                leftArrowPressed = true;
            }
            else if (getCurrentTankChar() == 'D' && event.getKeyCode() == RIGHT) {
                rightArrowPressed = true;
            }

            if (event.getKeyCode() == UP) {
                upPressed = true;
            } else if (event.getKeyCode() == DOWN) {
                downPressed = true;
            }

            if (event.getKeyCode() == 'R') {
                powerup.repairTank(tank);
            }

            if (event.getKeyCode() == 'R' && gameEnded == true) {
                currentLevel = 1;
                loadLevel(currentLevel);
                gameEnded = false;
                loop();
                setAScore(0);
                setBScore(0);
                setCScore(0);
                setDScore(0);
            }

            if (event.getKeyCode() == 'F') {
                powerup.buyPetrol(tank);
            }

            if (event.getKeyCode() == 'P') {
                powerup.buyChute(tank);
            }

            if (event.getKeyCode() == 'H') {
                powerup.buyShield(tank);
            }

            if (event.getKeyCode() == ' ') {
                wind.updateWind(); // update the wind every time a players' turn switches
                Tank currentTank = tanks.get(currentTankIndex);
                Bomb bomb = null;
                float turretEndX = currentTank.getPosX() + App.CELLSIZE / 2 + 15 * PApplet.cos(currentTank.getTurretAngle()); // end point of the turret
                float turretEndY = currentTank.getPosY() - 15 * PApplet.sin(currentTank.getTurretAngle()); // height of the turret
                switch (currentTank.getTankName()) {
                    case 'A':
                        bomb = Bomb.bombA(this, turretEndX, turretEndY, currentTank.getTurretAngle(), tank.getPowerLevel());
                        break;
                    case 'B':
                        bomb = Bomb.bombB(this, turretEndX, turretEndY, currentTank.getTurretAngle(), tank.getPowerLevel());
                        break;
                    case 'C':
                        bomb = Bomb.bombC(this, turretEndX, turretEndY, currentTank.getTurretAngle(), tank.getPowerLevel());
                        break;
                    case 'D':
                        bomb = Bomb.bombD(this, turretEndX, turretEndY, currentTank.getTurretAngle(), tank.getPowerLevel());
                        break;
                }
                if (bomb != null) {
                    bombs.add(bomb);
                }
                currentTankIndex = (currentTankIndex + 1) % tanks.size(); // increment the current tanks index
                for (Tank tank1 : tanks) {
                    tank1.setSpeed(0); // stop all tanks from moving
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Tank at invalid index: " + currentTankIndex);
            if (!tanks.isEmpty()) {
                currentTankIndex = 0; 
            }
        }
    }

    /**
     * Returns the score for player A.
     * @return Player A score.
     */
    public int getAScore() {
        return AScore;
    }

    /**
     * Returns the score for player B.
     * @return Player B score.
     */
    public int getBScore() {
        return BScore;
    }

    /**
     * Returns the score for player C.
     * @return Player C score.
     */
    public int getCScore() {
        return CScore;
    }

    /**
     * Returns the score for player D.
     * @return Player D score.
     */
    public int getDScore() {
        return DScore;
    }

    /**
     * Sets the score for player A.
     * @param score The score.
     */
    public void setAScore(int score) {
        this.AScore = score;
    }

    /**
     * Sets the score for player B.
     * @param score The score.
     */
    public void setBScore(int score) {
        this.BScore = score;
    }

    /**
     * Sets the score for player C.
     * @param score The score.
     */
    public void setCScore(int score) {
        this.CScore = score;
    }

    /**
     * Sets the score for player D.
     * @param score The score.
     */
    public void setDScore(int score) {
        this.DScore = score;
    }

    /**
     * Updates the score for each tank as the game progresses.
     * @param tankName Current tank name.
     * @param score Score for the tank.
     */
    public void updateScore(char tankName, int score) {
        switch (tankName) {
            case 'A':
                AScore += score;
                break;
            case 'B':
                BScore += score;
                break;
            case 'C':
                CScore += score;
                break;
            case 'D':
                DScore += score;
                break;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     *  @param event The KeyEvent object containing details of the key released.
     */
	@Override
    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == LEFT) {
            leftArrowPressed = false;
        }
        else if(event.getKeyCode() == RIGHT) {
            rightArrowPressed = false;
        }
        if (event.getKeyCode() == UP) {
            upPressed = false;
        } else if (event.getKeyCode() == DOWN) {
            downPressed = false;
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        if (backgroundImage != null) {
            background(backgroundImage); // Ensure backgroundImage is not null
        }
        if (terrain != null) {
            terrain.displayTerrain(); // Ensure terrain is not null
        }
        try{
            Tank currentTank = tanks.get(currentTankIndex); // i am doing this to move the current tank (whichever tanks turn it is)
            if (leftArrowPressed) {
                currentTank.setSpeed(-60);
            }
            else if (rightArrowPressed) {
                currentTank.setSpeed(60);
            }
            else {
                currentTank.setSpeed(0);
            }
            currentTank.go();
            if (upPressed) {
                currentTank.setTurretAngle(currentTank.getTurretAngle() + PApplet.radians(3)); // move the turret left
            } 
            else if (downPressed) {
                currentTank.setTurretAngle(currentTank.getTurretAngle() - PApplet.radians(3)); // move the turret right
            }
            // display all the tanks in the game
            for (Tank tank : tanks) {
                tank.update();
                tank.displayTanks();
            }

            for (Iterator<Tank> iterator = tanks.iterator(); iterator.hasNext();) {
                Tank tank = iterator.next();
                Bomb bomb = new Bomb(this, tank.getPosX(), tank.getPosY(), tank.getTurretAngle(), tank.getPowerLevel(), tank.getColor(), tank.getTankName());
                if (tank.getHealth() <= 0) {
                    iterator.remove(); // remove tank if health is 0
                }
                if (tank.getPosY() > 640) {
                    iterator.remove();
                    bomb.destroyTerrain();
                }
            }
            
            for (Iterator<Bomb> iterator = bombs.iterator(); iterator.hasNext(); ) {
                Bomb bomb = iterator.next();
                bomb.update();
                bomb.displayBomb();
                if (bomb.outside()) {
                    iterator.remove(); // remove the bomb if it goes outside the game
                }
                else if (bomb.getY() >= Terrain.heights.get((int) bomb.getX())) { // destroy the terrain when the bomb hits it
                    bomb.displayExplosion();
                    bomb.destroyTerrain();
                    bomb.destroyTank(tanks);
                    iterator.remove();
                }
            }
            
            // display all the game stats
            hud.drawArrow(currentTank);
            hud.whoIsDaPlayer(currentTank);
            hud.displayFuel(currentTank);
            hud.displayChuteIcon(currentTank);
            hud.displayHealth(currentTank); 
            hud.displayShield(currentTank);
            hud.displayLeaderboard(this);

            // display the windspeed
            wind.displayWind();
        }
        catch (IndexOutOfBoundsException e) {
            System.err.println("Tank at invalid index (draw function): " + currentTankIndex);
        }

        if (tanks.size() == 1) {
            currentLevel++;
            if (currentLevel <= maxLevel) {
                loadLevel(currentLevel);
            } 
            else {
                hud.displayTheEnd();
                gameEnded = true;
            }
        }
    }

    /**
     * Main function that serves as the entry point of the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}

