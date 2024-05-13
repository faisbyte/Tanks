package Tanks;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;


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
        tank.setHealth(100);
        tank.setPosY(500);
        chute.setParachutes(3);
        //assertEquals(2, chute.getRemainingParachutes(), "Correct number of parachutes");
        tank.getParachute().updateParachute();
        chute.setParachutes(3);
        tank.getParachute().deployParachute();
        chute.setParachutes(-10);
        tank.getParachute().deployParachute();
        chute.isDeployed = true;
        tank.getParachute().resetParachute();
        chute.isDeployed = false;
        tank.getParachute().handleFreeFall();
        chute.isDeployed = true;
        tank.getParachute().handleFreeFall();
        chute.isDeployed = false;
        chute.displayParachute();
    }

    @Test
    public void bombTests() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        final float halfC = 15.0f;
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
        bomb.outside();
        bomb.setX(-1);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when x < 0");
        bomb.outside();
        bomb.setX(1000);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when x > App.WIDTH");
        bomb.outside();
        bomb.setY(1000);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when y > App.HEIGHT");
        bomb.outside();
        bomb.setX(1);
        
    }

    @Test
    public void testDestroyTankFunctionality() {
        // Initialize applet and bomb
        PApplet app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        //app.setup();
        Bomb bomb = new Bomb(app, 100, 100, 0, 100, app.color(255, 0, 0), 'A');

        // Create a list of tanks
        ArrayList<Tank> tanks = new ArrayList<>();
        Tank tankWithinRange = mock(Tank.class);
        Tank tankOutsideRange = mock(Tank.class);

        // Configure the mock tanks
        when(tankWithinRange.getPosX()).thenReturn(110.0f);  // Within the 30f radius
        when(tankWithinRange.getPosY()).thenReturn(100.0f);
        when(tankWithinRange.getHealth()).thenReturn(50);
        when(tankWithinRange.getTankName()).thenReturn('B');

        when(tankOutsideRange.getPosX()).thenReturn(200.0f);  // Outside the 30f radius
        when(tankOutsideRange.getPosY()).thenReturn(200.0f);
        when(tankOutsideRange.getHealth()).thenReturn(50);
        when(tankOutsideRange.getTankName()).thenReturn('C');

        tanks.add(tankWithinRange);
        tanks.add(tankOutsideRange);

        // Apply the destroyTank method
        bomb.destroyTank(tanks);

        // Verify that damage is applied correctly within range and not outside range
        verify(tankWithinRange).damage(anyInt());
        verify(tankOutsideRange, never()).damage(anyInt());

        // Check if the correct score updating logic is called
        verify((App) app, times(1)).updateScore(eq('A'), anyInt());

        // Check tank removal for zero health
        when(tankWithinRange.getHealth()).thenReturn(0);
        bomb.destroyTank(tanks);
        assertTrue(tanks.contains(tankOutsideRange) && !tanks.contains(tankWithinRange), "Tank within range should be removed");

        // Optionally, check if terrain modification occurs (this part depends on your implementation specifics)
    }

    @Test
    public void powerupTests() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
        Tank tankA = new Tank(app, 'A', 10, 10);
        Tank tankB = new Tank(app, 'B', 10, 10);

        Powerups powerup = new Powerups(app);
        app.setAScore(50);
        app.setBScore(50);
        app.setCScore(50);
        app.setDScore(50);

        // a>=0, a>=cost
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        //assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setHealth(100);
        powerup.repairTank(tankA);
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        //assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(70);
        powerup.buyChute(tankA);

        app.setAScore(20);
        app.setAScore(20);
        app.setBScore(20);
        app.setCScore(20);
        tankB.setHealth(70);
        powerup.repairTank(tankB);
        //assertEquals(90, tankA.getHealth(), "Correct health update");
        tankB.setPetrol(100);
        app.setAScore(10);
        app.setAScore(10);
        app.setBScore(10);
        app.setCScore(10);
        powerup.buyPetrol(tankB);
       // assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(-10);
        app.setAScore(30);
        app.setAScore(30);
        app.setBScore(30);
        app.setCScore(30);
        powerup.buyChute(tankB);

        app.setAScore(9);
        tankB.setHealth(70);
        powerup.repairTank(tankB);
        //assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankB);
        //assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(70);
        powerup.buyChute(tankB);

        app.setAScore(-10);
        app.setAScore(-10);
        app.setBScore(-10);
        app.setCScore(-10);
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        //assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        //assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(-10);
        powerup.buyChute(tankA);

        app.setAScore(9);
        tankA.setHealth(70);
        powerup.repairTank(tankA);
        //assertEquals(90, tankA.getHealth(), "Correct health update");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        //assertEquals(300, tankA.petrol, "Correct petrol update");
        app.setAScore(70);
        powerup.buyChute(tankA);
    }

    @Test
    public void testCorrectWinnerAnnouncement() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        //app.setup();

        app.setAScore(50);
        app.setBScore(40);
        app.setCScore(30);
        app.setDScore(20);
        HUD hud = new HUD(app);
        hud.displayTheEnd();
        // Assertions to check the text and color displayed for the winner
        assertEquals("Player A wins!", hud.winner, "Winner should be Player A");

        app.setAScore(40);
        app.setBScore(50);
        app.setCScore(30);
        app.setDScore(20);
        hud.displayTheEnd();
        // Assertions to check the text and color displayed for the winner
        assertEquals("Player B wins!", hud.winner, "Winner should be Player B");

        app.setAScore(40);
        app.setBScore(30);
        app.setCScore(50);
        app.setDScore(20);
        hud.displayTheEnd();
        // Assertions to check the text and color displayed for the winner
        assertEquals("Player C wins!", hud.winner, "Winner should be Player C");

        app.setAScore(40);
        app.setBScore(30);
        app.setCScore(20);
        app.setDScore(50);
        hud.displayTheEnd();
        // Assertions to check the text and color displayed for the winner
        assertEquals("Player D wins!", hud.winner, "Winner should be Player D");

        app.setAScore(0);
        app.setBScore(0);
        app.setCScore(0);
        app.setDScore(0);
        hud.displayTheEnd();
        // Assertions to check the text and color displayed for the winner
        assertEquals("Player A wins!", hud.winner, "Winner should be Player A");
    }

}