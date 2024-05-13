package Tanks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

// another class to help with player scores
class PlayerScore {
    String name;
    int score;
    int color;
    PlayerScore(String name, int score, int color) {
        this.name = name;
        this.score = score;
        this.color = color;
    }
}

/**
 * Handles the display of various heads-up display (HUD) elements in the Tanks game,
 * such as health bars, fuel icons, parachutes, and game-ending messages.
 */
public class HUD {

    private App app; // Reference to the Processing application.
    private Terrain terrain; // Terrain reference for checking positions.
    private float h = 60; // Base height offset for drawing elements.
    private float line = 50; // Length of the line for indicators.
    private float width = 14; // Width used for elements like arrows.
    private PImage fuelIcon; // Icon for fuel display.
    private PImage chute; // Icon for parachute display.
    public String winner = "";

    /**
     * Constructor for HUD class.
     * @param app Instance of PApplet used for drawing.
     */
    public HUD(App app) {
        this.app = app;
        fuelIcon = app.loadImage("src/main/resources/Tanks/fuel.png");
        chute = app.loadImage("src/main/resources/Tanks/parachute.png");
    }

    /**
     * Draws an arrow pointing to the tank's position.
     * @param tank The tank for which to draw the arrow.
     */
    public void drawArrow(Tank tank) {
        float tankX = tank.getPosX() + App.CELLSIZE / 2;
        float tankY = terrain.getYc(tank.getPosX() + App.CELLSIZE / 2) - h;
        // draw the line of the arrow
        app.stroke(0);
        app.fill(0, 0, 0);
        app.strokeWeight(2);
        app.line(tankX, tankY, tankX, tankY - line);
        // draw the triangle on top of the arrow
        app.noStroke(); 
        app.triangle(
            tankX, tankY + line- 45, // base of triangle
            tankX - width, tankY + line - width - 45, // left of triangle
            tankX + width, tankY + line - width-45  // right of triangle
        );
    }

    /**
     * Displays the current player's turn information.
     * @param currentTank The tank that is currently active.
     */
    public void whoIsDaPlayer(Tank currentTank) {
        String playerTurn = "Player " + currentTank.getTankName() + "'s turn"; // display the currennt tanks' turn
        app.fill(0);
        app.textSize(15);
        app.text(playerTurn, 10, 30);
    }

    /**
     * Displays the fuel status next to the fuel icon.
     * @param tank The tank whose fuel status is to be displayed.
     */
    public void displayFuel(Tank tank) {
        float tankX = 150;
        float tankY = 30;
        app.image(fuelIcon, tankX, tankY - 20, 20, 20); //draw the fuel icon with proper positioning
        app.fill(0);
        app.textSize(15);
        int fuelText = tank.remainingPetrol();
        app.text(fuelText, tankX + 25, tankY);
    }

    /**
     * Displays the parachute icon and remaining count for the specified tank.
     * @param tank The tank whose parachute info is displayed.
     */
    public void displayChuteIcon(Tank tank) {
        app.image(chute, 140, 40, 30, 30);
        app.fill(0);
        app.textSize(15);
        int text = tank.getParachute().getRemainingParachutes();
        app.text(text, 150 + 30, 60);
    }

    /**
     * Displays the status of the shield of the tank.
     * @param tank The tank whose shield status is to be displayed.
     */
    public void displayShield(Tank tank) {
        app.textSize(15);
        String tf = "";
        if (tank.isShieldActive() == true) {
            tf = "on";
        }
        else {
            tf = "off";
        }
        String text = "Shield: " + tf;
        app.text(text, 600, 30);
    }

    /**
     * Displays the health bar of the tank.
     * @param tank The tank whose health is to be displayed.
     */
    public void displayHealth(Tank tank) {
        float centerX = app.width / 2.0f;
        float centerY = app.height - 625;
        float barLength = 200; // length of bar
        float barHeight = 20; // height of bar
        float powerLevelPosition = (barLength * tank.getPowerLevel() / 100) - (barLength / 2); // display the line according to power level

        app.textSize(15);
        app.fill(0);
        app.text("Health: ", centerX - barLength / 2 - 60, centerY + 10); // type health

        app.fill(200, 200, 200);
        app.rect(centerX - barLength / 2, centerY, barLength, barHeight); // draw gray colour for health which is lost

        app.strokeWeight(4);
        app.stroke(0);
        app.fill(tank.getColor()); 
        app.rect(centerX - barLength / 2, centerY, barLength * (tank.getHealth() / 100.0f), barHeight); // draw the health bar

        float linePositionX = centerX + powerLevelPosition;
        app.strokeWeight(4); 
        app.stroke(0);  
        app.line(linePositionX, centerY, linePositionX, centerY + barHeight); // draw the power level line in the centre

        app.strokeWeight(2);
        app.stroke(255, 0, 0);
        app.line(linePositionX, centerY, linePositionX, centerY + barHeight); // the line is in the centre by default
        app.noStroke();

        app.fill(0);
        app.text("Power: " + tank.getPowerLevel(), centerX - barLength / 2 - 60, centerY + barHeight + 25); // display the power below health
    }

    /**
     * Displays the leaderboard with scores of all players.
     * @param app The main application instance to access scores.
     */
    public void displayLeaderboard(App app) {
        float x = 740; 
        float y = 110;  
        float h = 92;
        float w = 127;
        // get the word scores in the leaderboard
        app.fill(0);
        app.textSize(15);
        app.noFill();
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text("Scores", x + 25, y - 35);
        app.textAlign(PApplet.LEFT, PApplet.BASELINE); 
        app.rect(x, y - 10, w, h);
        // draw the line 
        app.stroke(0); 
        app.strokeWeight(4); 
        app.line(732, 90, 857, 90); 
        // draw the box and the scores in
        app.stroke(0); 
        app.strokeWeight(4);
        app.noFill(); 
        app.rect(x - 10, y - 45, w, h + 25);
        app.textSize(15);
        app.fill(app.color(0, 0, 255)); 
        app.text("Player A     " + app.getAScore(), x, y);
        y += 20;
        
        app.fill(app.color(255, 0, 0)); 
        app.text("Player B     " + app.getBScore(), x, y);
        y += 20;
        
        app.fill(app.color(0, 255, 255)); 
        app.text("Player C     " + app.getCScore(), x, y);
        y += 20;
        
        app.fill(app.color(255, 255, 0));
        app.text("Player D     " + app.getDScore(), x, y);
    }

    /**
     * Displays the end game message and a list of player scores in descending order.
     */
    public void displayTheEnd() {
        int scoreA = ((App) app).getAScore();
        int scoreB = ((App) app).getBScore();
        int scoreC = ((App) app).getCScore();
        int scoreD = ((App) app).getDScore();   
        int maxScore = Math.max(Math.max(scoreA, scoreB), Math.max(scoreC, scoreD));
        if (scoreA == maxScore) {
            winner = "Player A wins!";
        } else if (scoreB == maxScore) {
            winner = "Player B wins!";
        } else if (scoreC == maxScore) {
            winner = "Player C wins!";
        } else if (scoreD == maxScore) {
            winner = "Player D wins!";
        }
        // list of playrs and scores
        List<PlayerScore> scores = new ArrayList<>();
        scores.add(new PlayerScore("Player A", scoreA, app.color(0, 0, 255)));  // Blue
        scores.add(new PlayerScore("Player B", scoreB, app.color(255, 0, 0)));  // Red
        scores.add(new PlayerScore("Player C", scoreC, app.color(0, 255, 255))); // Cyan
        scores.add(new PlayerScore("Player D", scoreD, app.color(255, 255, 0))); // Yellow
        // sort with desc order
        Collections.sort(scores, (p1, p2) -> Integer.compare(p2.score, p1.score));
        // winner
        app.textSize(32);
        app.fill(0);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text(winner, app.width / 2, app.height / 2 - 100);
        //box 
        float boxX = app.width / 2 - 100;
        float boxY = app.height / 2 - 50;
        float boxWidth = 200;
        float boxHeight = 130;
        if (scoreA == maxScore) {
            app.fill(0, 0, 255, 150);
            app.rect(boxX, boxY, boxWidth, boxHeight);
        }
        else if (scoreB == maxScore) {
            app.fill(255, 0, 0, 150);
            app.rect(boxX, boxY, boxWidth, boxHeight);
        }
        else if (scoreC == maxScore) {
            app.fill(0, 255, 255, 150);
            app.rect(boxX, boxY, boxWidth, boxHeight);
        }
        else if (scoreD == maxScore) {
            app.fill(255, 255, 0, 150);
            app.rect(boxX, boxY, boxWidth, boxHeight);
        }
        app.textSize(20);
        app.fill(0);
        app.textAlign(PApplet.CENTER, PApplet.CENTER);
        app.text("Hit 'R' to restart the game :)", app.width / 2, app.height / 2 + 97);
        // display scores
        float yPos = boxY + 20;
        app.textSize(24);
        for (PlayerScore playerScore : scores) {
            app.fill(playerScore.color);
            app.text(playerScore.name + ":     " + playerScore.score, boxX + 100, yPos);
            yPos += 30;
        }
        app.noLoop();
    }
    
}

