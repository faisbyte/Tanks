package Tanks;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;


public class SampleTest {

    // gradle test jacocoTestReport
    // jacoco test report: file:///Users/faisalnaveed/Downloads/tanks_scaffold/build/reports/jacoco/test/html/Tanks/App.html
    // javadoc report: file:///Users/faisalnaveed/Downloads/tanks_scaffold/build/docs/javadoc/Tanks/package-summary.html

    @Test
    public void simpleTest() {
        App app = new App();
        PApplet.runSketch(new String[] { "Tanks" }, app);
        app.setup();
        app.draw();
        app.initializeTanks();
        // app.loadLevel(1);
        // app.loadLevel(2);
        // app.loadLevel(3);
        app.getCurrentTankChar();
        // app.updateScore('A', 20);
        // app.updateScore('B', 20);
        // app.updateScore('C', 20);
        // app.updateScore('D', 20);
        app.getAScore();
        app.getBScore();
        app.getCScore();
        app.getDScore();

        Object nativeObject = this;
        long millis = System.currentTimeMillis();
        int action = KeyEvent.PRESS;
        int modifiers = 0;
        int keyCode = java.awt.event.KeyEvent.VK_A;
        char key = App.UP;
        processing.event.KeyEvent ke = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
        app.keyPressed(ke);
        char key1 = 'W';
        processing.event.KeyEvent ke1 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key1, keyCode);
        app.keyPressed(ke1);
        char key2 = 'S';
        processing.event.KeyEvent ke2 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key2, keyCode);
        app.keyPressed(ke2);
        char key3 = 'R';
        processing.event.KeyEvent ke3 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key3, keyCode);
        app.keyPressed(ke3);
        char key4 = 'F';
        processing.event.KeyEvent ke4 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key4, keyCode);
        app.keyPressed(ke4);
        char key5 = App.DOWN;
        processing.event.KeyEvent ke5 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key5, keyCode);
        app.keyPressed(ke5);
        char key6 = App.RIGHT;
        processing.event.KeyEvent ke6 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key6, keyCode);
        app.keyPressed(ke6);
        char key7 = App.LEFT;
        processing.event.KeyEvent ke7 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key7, keyCode);
        app.keyPressed(ke7);
        char key8 = 'P';
        processing.event.KeyEvent ke8 = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key8, keyCode);
        app.keyPressed(ke8);

        Object nativeObject1 = this;
        long millis1 = System.currentTimeMillis();
        int action1 = KeyEvent.RELEASE;
        int modifiers1 = 0;
        int keyCode1 = java.awt.event.KeyEvent.VK_A;
        char key_r = App.RIGHT;
        processing.event.KeyEvent ke_r = new processing.event.KeyEvent(nativeObject1, millis1, action1, modifiers1, key_r, keyCode1);
        app.keyReleased(ke_r);
        char key_l = App.LEFT;
        processing.event.KeyEvent ke_l = new processing.event.KeyEvent(nativeObject1, millis1, action1, modifiers1, key_l, keyCode1);
        app.keyReleased(ke_l);
        char key_u = App.UP;
        processing.event.KeyEvent ke_u = new processing.event.KeyEvent(nativeObject1, millis1, action1, modifiers1, key_u, keyCode1);
        app.keyReleased(ke_u);
        
    }

    // @Test
    // public void test1() {
    //     App app = new App();
    //     app.setup();
    //     app.draw();
    // }

    // @Test
    // public void testAppInitialization() {
    //     App app = new App();
    //     assertNotNull(app.tanks, "Tanks list should be initialized.");
    //     assertTrue(app.tanks.isEmpty(), "Tanks list should initially be empty.");
    //     assertEquals(1, app.getCurrentLevel(), "Initial level should be 1.");
    //     assertNotNull(app.configPath, "Config path should be initialized.");
    // }

    // @Test
    // public void testWindInitialization() {
    //     App app = new App();
    //     PApplet.runSketch(new String[] {"Tanks"}, app);
    //     Wind wind = new Wind(app);
    //     assertTrue(wind.getSpeed() >= -35 && wind.getSpeed() <= 35, "Wind speed should be initialized within the defined bounds.");
    // }

    // @Test
    // public void testUpdateWind() {
    //     App app = new App();
    //     PApplet.runSketch(new String[] {"Tanks"}, app);
    //     Wind wind = new Wind(app);
    //     float initialSpeed = wind.getSpeed();
    //     wind.updateWind();
    //     float newSpeed = wind.getSpeed();
    //     assertTrue(newSpeed >= -35 && newSpeed <= 35, "Updated wind speed should still be within the allowed range.");
    //     assertTrue(Math.abs(newSpeed - initialSpeed) <= 5, "Change in wind speed should not exceed the maximum change rate.");
    // }

    // @Test
    // public void testBombInitialization() {
    //     App app = new App();
    //     PApplet.runSketch(new String[] {"Tanks"}, app);
    //     Bomb bomb = new Bomb(app, 100, 100, PApplet.HALF_PI, 50, app.color(255, 0, 0), 'A');

    //     assertEquals(100, bomb.getX(), "Bomb should initialize at the correct X position.");
    //     assertEquals(100, bomb.getY(), "Bomb should initialize at the correct Y position.");
    //     assertEquals('A', bomb.getFiringTank(), "Bomb should record the correct firing tank.");
    // }

    // @Test
    // public void testParachuteInitialization() {
    //     App app = new App();
    //     PApplet.runSketch(new String[] {"Tanks"}, app);
    //     Tank tank = new Tank(app, 'A', 10, 10);
    //     Parachute parachute = new Parachute(tank, app);

    //     assertEquals(3, parachute.getRemainingParachutes(), "Initial parachutes should be set to 3.");
    //     assertFalse(parachute.isDeployed(), "Parachute should not be deployed initially.");
    // }

    @Test
    public void testTerrainLoadingForDifferentLevels() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        app.loadLevel(1);
        assertNotNull(app.terrain, "Terrain should be initialized when level 1 is loaded.");
        app.loadLevel(2); 
        assertNotNull(app.terrain, "Terrain should be initialized when level 2 is loaded.");
        app.loadLevel(3);
        assertNotNull(app.terrain, "Terrain should be initialized when level 3 is loaded.");
    }

    @Test
    public void testWindInitialization() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        Wind wind = new Wind(app);
        assertTrue(wind.getSpeed() >= -35 && wind.getSpeed() <= 35, "Wind speed should be initialized within the defined bounds.");
    }

    @Test
    public void testUpdateWind() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        Wind wind = new Wind(app);
        float initialSpeed = wind.getSpeed();
        wind.updateWind();
        float newSpeed = wind.getSpeed();
        assertTrue(newSpeed >= -35 && newSpeed <= 35, "Updated wind speed should still be within the allowed range.");
        assertTrue(Math.abs(newSpeed - initialSpeed) <= 5, "Change in wind speed should not exceed the maximum change rate.");
    }

    @Test
    public void testGetSpeed() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        Wind wind = new Wind(app);
        assertEquals(wind.getSpeed(), wind.getSpeed(), "getSpeed should consistently return the current wind speed.");
    }

    @Test
    public void testPlayerScoreInitialization() {
        PlayerScore playerScore = new PlayerScore("Player A", 100, 255); // Color set as an example
        assertEquals("Player A", playerScore.name, "Name should be correctly initialized.");
        assertEquals(100, playerScore.score, "Score should be correctly initialized.");
        assertEquals(255, playerScore.color, "Color should be correctly initialized.");
    }

    @Test
    public void testDisplayWind() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        App.wind.windSpeed = -30;
        App.wind.displayWind();
    }

    @Test
    public void testTree2() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        app.currentLevel = 3;
        app.terrain.loadTreeImage();
        app.terrain.displayTerrain();
        app.currentLevel = 2;
        app.terrain.displayTerrain();
        app.currentLevel = 99;
        app.terrain.displayTerrain();
        
        Terrain.getYc(-5);
    }

    @Test
    public void testIncreasePowerDoesNotExceedHealth() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setHealth(50);  // Set health to 50
        tank.powerLevel = 49; // Set power level close to health limit

        tank.increasePower();
        assertEquals(50, tank.getPowerLevel(), "Power level should not exceed health.");

        // Attempt to increase power beyond health
        tank.increasePower();
        assertEquals(50, tank.getPowerLevel(), "Power level should not exceed health even after trying to increase again.");
    }   

    @Test
    public void testIncreasePowerAdjustsToMaxHealth() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setHealth(50);  // Set health to 50
        tank.powerLevel = 50; // Set power level at health limit

        // Increase power when already at health limit
        tank.increasePower();
        assertEquals(50, tank.getPowerLevel(), "Power level should adjust to not exceed health.");
    }
    
    @Test
    public void testForWeirdPowerLevel() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setHealth(50);
        tank.powerLevel = 49;
        tank.increasePower();
        tank.getHealth();
        tank.increasePower();
        tank.decreasePower();
        tank.powerLevel = 1;
        tank.decreasePower();
        tank.getTerrain();
    }

    @Test
    public void testForDamage() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setHealth(50);
        tank.damage(30);
        assertEquals(20, tank.getHealth(), "Correct damage");
        tank.damage(50);
    }

    @Test
    public void turretAngleTest() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setTurretAngle(50);
        assertEquals(50, tank.getTurretAngle(), "Correct angle");
    }

    @Test
    public void testforfuel() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setPetrol(200);
    }

    @Test
    public void testForParachutes() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        Parachute chute = new Parachute(tank, app);
        chute.setParachutes(3);
        assertEquals(2, chute.getRemainingParachutes(), "Correct number of parachutes");
    }

    @Test
    public void bombTests() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        Bomb bomb = new Bomb(app, 10, 10, 50, 50, 255, 'A');
        bomb.getFiringTank();
        assertEquals('A', bomb.getFiringTank(), "Correct firing tank");
        bomb.displayBomb();
        bomb.displayExplosion();
        bomb.getX();
        assertEquals(10, bomb.getX(), "Correct X co ordinate");
        bomb.getY();
        bomb.getY();
        assertEquals(10, bomb.getY(), "Correct Y co ordinate");
        bomb.update();
        bomb.destroyTerrain();
    }

    @Test
    public void powerupTests() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        Tank tankA = new Tank(app, 'A', 10, 10);
        Tank tankB = new Tank(app, 'B', 10, 10);
        Tank tankC = new Tank(app, 'C', 10, 10);
        Tank tankD = new Tank(app, 'D', 10, 10);

        Powerups powerup = new Powerups(app);
        System.out.println("nigpoi");
        app.setAScore(50);
        app.setBScore(50);
        app.setCScore(50);
        app.setDScore(50);

        // a>=0, a>=cost
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setHealth(100);
        powerup.repairTank(tankA);
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(70);
        powerup.buyChute(tankA);

        app.setAScore(-10);
        app.setAScore(-10);
        app.setBScore(-10);
        app.setCScore(-10);
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        // assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        // assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(-10);
        powerup.buyChute(tankA);

        app.setAScore(9);
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        // assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        // assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(70);
        powerup.buyChute(tankA);

        // tankB.setHealth(70);
        // powerup.repairTank(tankB);
        // assertEquals(90, tankB.getHealth(), "Correct health update");
        // tankB.setPetrol(100);
        // powerup.buyPetrol(tankB);
        // assertEquals(300, tankB.petrol, "Correct petrol update");
        // app.setAScore(70);
        // powerup.buyChute(tankB);

        // app.setBScore(-10);
        // tankB.setHealth(70);
        // powerup.repairTank(tankB);
        // assertEquals(90, tankB.getHealth(), "Correct health update");
        // tankB.setPetrol(100);
        // powerup.buyPetrol(tankB);
        // assertEquals(300, tankB.petrol, "Correct petrol update");
        // app.setAScore(70);
        // powerup.buyChute(tankB);
    }

}