package Tanks;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import processing.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;


public class SampleTest {

    // gradle test jacocoTestReport
    // jacoco test report: file:///Users/faisalnaveed/Downloads/tanks_scaffold/build/reports/jacoco/test/html/Tanks/App.html
    // javadoc report: file:///Users/faisalnaveed/Downloads/tanks_scaffold/build/docs/javadoc/Tanks/package-summary.html

    @Test
    public void appTest() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        app.draw();
        app.initializeTanks();
        
        Object nativeObject = this;
        long millis = System.currentTimeMillis();
        int action = KeyEvent.PRESS;
        int modifiers = 0;
        int keyCode;
        char key;
        
        // Test keyPressed for 'W' and 'S' for each tank
        for (Tank tank : app.tanks) {
            app.currentTankIndex = app.tanks.indexOf(tank);
            
            // Test 'W' key
            keyCode = java.awt.event.KeyEvent.VK_W;
            key = 'W';
            processing.event.KeyEvent keW = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
            app.keyPressed(keW);
            
            // Test 'S' key
            keyCode = java.awt.event.KeyEvent.VK_S;
            key = 'S';
            processing.event.KeyEvent keS = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
            app.keyPressed(keS);
        }

        // Test left and right arrow keys for each tank
        char[] tankChars = {'A', 'B', 'C', 'D'};
        for (char tankChar : tankChars) {
            app.currentTankIndex = tankChar - 'A';

            // Test LEFT arrow key
            keyCode = java.awt.event.KeyEvent.VK_LEFT;
            key = App.LEFT;
            processing.event.KeyEvent keLeft = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
            app.keyPressed(keLeft);

            // Test RIGHT arrow key
            keyCode = java.awt.event.KeyEvent.VK_RIGHT;
            key = App.RIGHT;
            processing.event.KeyEvent keRight = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
            app.keyPressed(keRight);
        }

        // Test remaining keys
        int[] keyCodes = {
            java.awt.event.KeyEvent.VK_UP, 
            java.awt.event.KeyEvent.VK_DOWN, 
            'R', 
            'F', 
            'P', 
            'H', 
            java.awt.event.KeyEvent.VK_SPACE
        };
        char[] keys = {
            App.UP, 
            App.DOWN, 
            'R', 
            'F', 
            'P', 
            'H', 
            ' '
        };

        for (int i = 0; i < keyCodes.length; i++) {
            keyCode = keyCodes[i];
            key = keys[i];
            processing.event.KeyEvent ke = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
            app.keyPressed(ke);
        }

        // Test keyReleased for movement keys
        int[] releaseKeyCodes = {
            java.awt.event.KeyEvent.VK_LEFT, 
            java.awt.event.KeyEvent.VK_RIGHT, 
            java.awt.event.KeyEvent.VK_UP, 
            java.awt.event.KeyEvent.VK_DOWN
        };
        char[] releaseKeys = {
            App.LEFT, 
            App.RIGHT, 
            App.UP, 
            App.DOWN
        };

        for (int i = 0; i < releaseKeyCodes.length; i++) {
            keyCode = releaseKeyCodes[i];
            key = releaseKeys[i];
            processing.event.KeyEvent keRelease = new processing.event.KeyEvent(nativeObject, millis, KeyEvent.RELEASE, modifiers, key, keyCode);
            app.keyReleased(keRelease);
        }

        // Simulate game ending and restarting
        app.gameEnded = true;
        keyCode = 'R';
        key = 'R';
        processing.event.KeyEvent keRestart = new processing.event.KeyEvent(nativeObject, millis, action, modifiers, key, keyCode);
        app.keyPressed(keRestart);
    }


    @Test
    public void testTerrainLoadingForDifferentLevels() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        // app.setup();
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
        // app.setup();
        Wind wind = new Wind(app);
        assertTrue(wind.getSpeed() >= -35 && wind.getSpeed() <= 35, "Wind speed should be initialized within the defined bounds.");
    }

    @Test
    public void testUpdateWind() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        // app.setup();
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
        // app.setup();
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
        // app.setup();
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
        // app.setup();
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
        // app.setup();
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
        // app.setup();
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
        // app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        Terrain terrain = new Terrain(app, app);
        tank.setTurretAngle(50);
        assertEquals(50, tank.getTurretAngle(), "Correct angle");
    }

    @Test
    public void testforfuel() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        // app.setup();
        Tank tank = new Tank(app, 'A', 10, 10);
        tank.setPetrol(200);
    }

    @Test
    public void bombTests() {
        App app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        // app.setup();
        final float halfC = 15.0f;
        Tank tank = new Tank(app, 'A', 10, 10);
        Bomb bomb = new Bomb(app, 10, 10, 50, 50, 255, 'A');
        
        // Test firing tank
        bomb.getFiringTank();
        assertEquals('A', bomb.getFiringTank(), "Correct firing tank");

        // Test display functions
        bomb.displayBomb();
        bomb.displayExplosion();

        // Test bomb coordinates
        bomb.getX();
        assertEquals(10, bomb.getX(), "Correct X co ordinate");
        bomb.getY();
        assertEquals(10, bomb.getY(), "Correct Y co ordinate");

        // Test bomb update
        bomb.update();
        
        // Test destroyTerrain
        Terrain.heights = new ArrayList<>(Collections.nCopies(100, 640.0f)); // Initialize terrain heights
        bomb.destroyTerrain();
        for (int i = (int) Math.max(0, bomb.getX() - 30); i < Math.min(Terrain.heights.size(), bomb.getX() + 30); i++) {
            float d = Math.abs(i - bomb.getX());
            float semiCircleHeight = (float) Math.sqrt(Math.max(0, 30 * 30 - d * d));
            if (Terrain.heights.get(i) < bomb.getY() + semiCircleHeight && Terrain.heights.get(i) > bomb.getY() - semiCircleHeight) {
                assertEquals(bomb.getY() + semiCircleHeight, Terrain.heights.get(i), 0.01, "Terrain height modified correctly within explosion range");
            }
        }

        // Test if bomb is outside the boundaries
        bomb.setX(-1);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when x < 0");
        bomb.setX(1000);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when x > App.WIDTH");
        bomb.setY(1000);
        assertTrue(bomb.outside(), "Bomb should be outside the boundaries when y > App.HEIGHT");
        bomb.setX(1);
        
        // Test static bomb creation methods
        Bomb bombA = Bomb.bombA(app, 10, 10, PApplet.HALF_PI, 50);
        assertNotNull(bombA, "Bomb A should be created successfully");
        Bomb bombB = Bomb.bombB(app, 10, 10, PApplet.HALF_PI, 50);
        assertNotNull(bombB, "Bomb B should be created successfully");
        Bomb bombC = Bomb.bombC(app, 10, 10, PApplet.HALF_PI, 50);
        assertNotNull(bombC, "Bomb C should be created successfully");
        Bomb bombD = Bomb.bombD(app, 10, 10, PApplet.HALF_PI, 50);
        assertNotNull(bombD, "Bomb D should be created successfully");

        // Test destroying a tank within explosion radius
        tank.setHealth(0); // Simulate tank health is 0
        final float r = 15f;
        for (int i = (int) Math.max(0, bomb.getX() - r); i < Math.min(Terrain.heights.size(), bomb.getX() + r); i++) {
            float d = Math.abs(i - bomb.getX());
            float semiCircleHeight = (float) Math.sqrt(Math.max(0, r * r - d * d));
            if (Terrain.heights.get(i) < bomb.getY() + semiCircleHeight && Terrain.heights.get(i) > bomb.getY() - semiCircleHeight) {
                Terrain.heights.set(i, bomb.getY() + semiCircleHeight);
            } else if (Terrain.heights.get(i) < bomb.getY() - semiCircleHeight) {
                Terrain.heights.set(i, Terrain.heights.get(i) + 2 * semiCircleHeight);
                Terrain.heights.set(i, Math.min(Terrain.heights.get(i), bomb.getY() + semiCircleHeight));
            }
        }
        assertEquals(0, tank.getHealth(), "Tank should be destroyed if health <= 0");
    }



    @Test
    public void testDestroyTankFunctionality() {
        // Initialize applet and bomb
        PApplet app = new App();
        PApplet.runSketch(new String[] {"Tanks"}, app);
        app.setup();
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
        PApplet.runSketch(new String[]{"Tanks"}, app);
        app.setup();
        Tank tankA = new Tank(app, 'A', 10, 10);
        Tank tankB = new Tank(app, 'B', 10, 10);
        Tank tankC = new Tank(app, 'C', 10, 10);
        Tank tankD = new Tank(app, 'D', 10, 10);

        Powerups powerup = new Powerups(app);

        // Scenario where all scores are above the cost and positive
        app.setAScore(50);
        app.setBScore(50);
        app.setCScore(50);
        app.setDScore(50);

        tankA.setHealth(70);
        powerup.repairTank(tankA);
        assertEquals(90, tankA.getHealth(), "Correct health update for tankA");
        tankA.setHealth(100);
        powerup.repairTank(tankA);
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        assertEquals(300, tankA.remainingPetrol(), "Correct petrol update for tankA");
        powerup.buyChute(tankA);
        powerup.buyShield(tankA);

        tankB.setHealth(70);
        powerup.repairTank(tankB);
        assertEquals(90, tankB.getHealth(), "Correct health update for tankB");
        tankB.setHealth(100);
        powerup.repairTank(tankB);
        tankB.setPetrol(100);
        powerup.buyPetrol(tankB);
        assertEquals(300, tankB.remainingPetrol(), "Correct petrol update for tankB");
        powerup.buyChute(tankB);
        powerup.buyShield(tankB);

        tankC.setHealth(70);
        powerup.repairTank(tankC);
        assertEquals(90, tankC.getHealth(), "Correct health update for tankC");
        tankC.setHealth(100);
        powerup.repairTank(tankC);
        tankC.setPetrol(100);
        powerup.buyPetrol(tankC);
        assertEquals(300, tankC.remainingPetrol(), "Correct petrol update for tankC");
        powerup.buyChute(tankC);
        powerup.buyShield(tankC);

        tankD.setHealth(70);
        powerup.repairTank(tankD);
        assertEquals(90, tankD.getHealth(), "Correct health update for tankD");
        tankD.setHealth(100);
        powerup.repairTank(tankD);
        tankD.setPetrol(100);
        powerup.buyPetrol(tankD);
        assertEquals(300, tankD.remainingPetrol(), "Correct petrol update for tankD");
        powerup.buyChute(tankD);
        powerup.buyShield(tankD);

        // Scenario where no scores are sufficient
        app.setAScore(5);
        app.setBScore(5);
        app.setCScore(5);
        app.setDScore(5);

        tankA.setHealth(70);
        powerup.repairTank(tankA);
        assertEquals(70, tankA.getHealth(), "Health should not change if score is insufficient for tankA");
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        assertEquals(100, tankA.remainingPetrol(), "Petrol should not change if score is insufficient for tankA");
        powerup.buyChute(tankA);
        assertEquals(1, tankA.getParachute().getRemainingParachutes(), "Parachutes should not change if score is insufficient for tankA");
        powerup.buyShield(tankA);
        assertFalse(tankA.isShieldActive(), "Shield should not activate if score is insufficient for tankA");

        tankB.setHealth(70);
        powerup.repairTank(tankB);
        assertEquals(70, tankB.getHealth(), "Health should not change if score is insufficient for tankB");
        tankB.setPetrol(100);
        powerup.buyPetrol(tankB);
        assertEquals(100, tankB.remainingPetrol(), "Petrol should not change if score is insufficient for tankB");
        powerup.buyChute(tankB);
        assertEquals(1, tankB.getParachute().getRemainingParachutes(), "Parachutes should not change if score is insufficient for tankB");
        powerup.buyShield(tankB);
        assertFalse(tankB.isShieldActive(), "Shield should not activate if score is insufficient for tankB");

        tankC.setHealth(70);
        powerup.repairTank(tankC);
        assertEquals(70, tankC.getHealth(), "Health should not change if score is insufficient for tankC");
        tankC.setPetrol(100);
        powerup.buyPetrol(tankC);
        assertEquals(100, tankC.remainingPetrol(), "Petrol should not change if score is insufficient for tankC");
        powerup.buyChute(tankC);
        assertEquals(1, tankC.getParachute().getRemainingParachutes(), "Parachutes should not change if score is insufficient for tankC");
        powerup.buyShield(tankC);
        assertFalse(tankC.isShieldActive(), "Shield should not activate if score is insufficient for tankC");

        tankD.setHealth(70);
        powerup.repairTank(tankD);
        assertEquals(70, tankD.getHealth(), "Health should not change if score is insufficient for tankD");
        tankD.setPetrol(100);
        powerup.buyPetrol(tankD);
        assertEquals(100, tankD.remainingPetrol(), "Petrol should not change if score is insufficient for tankD");
        powerup.buyChute(tankD);
        assertEquals(1, tankD.getParachute().getRemainingParachutes(), "Parachutes should not change if score is insufficient for tankD");
        powerup.buyShield(tankD);
        assertFalse(tankD.isShieldActive(), "Shield should not activate if score is insufficient for tankD");

        // Scenarios where only some scores are sufficient
        app.setAScore(50);
        app.setBScore(5);
        app.setCScore(5);
        app.setDScore(5);

        tankA.setHealth(70);
        powerup.repairTank(tankA);
        assertEquals(90, tankA.getHealth(), "Correct health update for tankA with sufficient score");
        tankA.setHealth(100);
        powerup.repairTank(tankA);
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        assertEquals(300, tankA.remainingPetrol(), "Correct petrol update for tankA with sufficient score");
        powerup.buyChute(tankA);
        powerup.buyShield(tankA);

        app.setAScore(5);
        app.setBScore(50);
        app.setCScore(5);
        app.setDScore(5);

        tankB.setHealth(70);
        powerup.repairTank(tankB);
        assertEquals(90, tankB.getHealth(), "Correct health update for tankB with sufficient score");
        tankB.setHealth(100);
        powerup.repairTank(tankB);
        tankB.setPetrol(100);
        powerup.buyPetrol(tankB);
        assertEquals(300, tankB.remainingPetrol(), "Correct petrol update for tankB with sufficient score");
        powerup.buyChute(tankB);
        powerup.buyShield(tankB);

        app.setAScore(5);
        app.setBScore(5);
        app.setCScore(50);
        app.setDScore(5);

        tankC.setHealth(70);
        powerup.repairTank(tankC);
        assertEquals(90, tankC.getHealth(), "Correct health update for tankC with sufficient score");
        tankC.setHealth(100);
        powerup.repairTank(tankC);
        tankC.setPetrol(100);
        powerup.buyPetrol(tankC);
        assertEquals(300, tankC.remainingPetrol(), "Correct petrol update for tankC with sufficient score");
        powerup.buyChute(tankC);
        powerup.buyShield(tankC);

        app.setAScore(5);
        app.setBScore(5);
        app.setCScore(5);
        app.setDScore(50);

        tankD.setHealth(70);
        powerup.repairTank(tankD);
        assertEquals(90, tankD.getHealth(), "Correct health update for tankD with sufficient score");
        tankD.setHealth(100);
        powerup.repairTank(tankD);
        tankD.setPetrol(100);
        powerup.buyPetrol(tankD);
        assertEquals(300, tankD.remainingPetrol(), "Correct petrol update for tankD with sufficient score");
        powerup.buyChute(tankD);
        powerup.buyShield(tankD);

        // Edge case: exactly at the cost threshold
        app.setAScore(20);
        app.setBScore(0);
        app.setCScore(0);
        app.setDScore(0);

        tankA.setHealth(70);
        powerup.repairTank(tankA);
        assertEquals(90, tankA.getHealth(), "Correct health update for tankA exactly at cost threshold");
        tankA.setHealth(100);
        powerup.repairTank(tankA);
        tankA.setPetrol(100);
        powerup.buyPetrol(tankA);
        assertEquals(300, tankA.remainingPetrol(), "Correct petrol update for tankA exactly at cost threshold");
        powerup.buyChute(tankA);
        powerup.buyShield(tankA);
    }




    @Test
    public void testCorrectWinnerAnnouncement() {
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        // app.setup();

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

    @Test
    public void testDisplayShield() {
        // Set up the app and HUD
        App app = mock(App.class);
        when(app.color(anyInt(), anyInt(), anyInt())).thenReturn(0);
        HUD hud = new HUD(app);

        // Set up the tank with the shield active
        Tank tankWithShield = mock(Tank.class);
        when(tankWithShield.isShieldActive()).thenReturn(true);

        // Invoke displayShield method
        hud.displayShield(tankWithShield);

        // Verify that the shield status text is correctly set to "on"
        verify(app).text("Shield: on", 600, 30);

        // Set up the tank without the shield active
        Tank tankWithoutShield = mock(Tank.class);
        when(tankWithoutShield.isShieldActive()).thenReturn(false);

        // Invoke displayShield method
        hud.displayShield(tankWithoutShield);

        // Verify that the shield status text is correctly set to "off"
        verify(app).text("Shield: off", 600, 30);
    }

    @Test
    public void testForParachutes() {
        // Initialize App and setup the sketch
        App app = new App();
        PApplet.runSketch(new String[]{"Tanks"}, app);
        // app.setup();

        // Create a tank and a parachute
        
        for (int i = 0; i < 896; i++) {
            Terrain.heights.add(400f);
        }
        Tank tank = new Tank(app, 'A', 100, 0);
        Parachute parachute = new Parachute(tank, app);

        // Mock terrain height to test isTankInMidair
        Terrain terrain = Mockito.mock(Terrain.class);
        Mockito.when(terrain.getYc(0)).thenReturn(0f); // Simulate ground at y = 0
        tank.setTerrain(terrain);

        // Scenario 1: Tank in midair with parachutes
        tank.setPosY(100); // Tank is in midair
        tank.getParachute().setParachutes(3);
        tank.getParachute().updateParachute();
        assertTrue(tank.getParachute().isDeployed(), "Parachute should be deployed when tank is in midair with remaining parachutes");
        assertEquals(2, tank.getParachute().getRemainingParachutes(), "Remaining parachutes should decrease by one after deployment");

        // Scenario 2: Tank in midair without parachutes
        tank.getParachute().setParachutes(0);
        tank.getParachute().updateParachute();
        assertFalse(tank.getParachute().isDeployed(), "Parachute should not deploy when no remaining parachutes");

        // Scenario 3: Tank on the ground
        tank.setPosY(0); // Tank is on the ground
        tank.getParachute().updateParachute();
        assertFalse(tank.getParachute().isDeployed(), "Parachute should not be deployed when tank is on the ground");

        // Test handleFreeFall
        tank.setPosY(100); // Tank is in midair without parachutes
        tank.getParachute().setParachutes(0);
        int initialHealth = tank.getHealth();
        tank.getParachute().updateParachute();
        assertEquals(initialHealth - 1 * (120 / App.FPS), tank.getHealth(), "Tank should take damage during free fall");

        parachute.isTankInMidair();

        // Test resetParachute
        tank.getParachute().deployParachute(); // Deploy parachute first
        tank.getParachute().resetParachute();
        assertFalse(tank.getParachute().isDeployed(), "Parachute should be reset and not deployed");
        assertEquals(0, 60, "Tank descent rate should be reset to 0");
    
    }

    

}