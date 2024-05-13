package Tanks;

/**
 * Manages power-ups for tanks, including repairs, fuel refills, and additional parachutes.
 */
public class Powerups {

    private App app; // Reference to the main game app to access scores and update them
    private int repairCost = 20; // Cost in score points to repair a tank
    private int petrolCost = 10; // Cost in score points to buy additional fuel
    private int chuteCost = 30; // Cost in score points to buy an additional parachute
    private int maxHealth = 100; // Maximum health a tank can have
    private int morePetrol = 200; // Amount of fuel added when petrol is bought
    private int shieldCost = 40;

    /**
     * Constructor to create a Powerups object.
     * @param app The main game app reference.
     */
    public Powerups(App app) {
        this.app = app;
    }

    /**
     * Repairs a tank, increasing its health by 20 points if the player's score is sufficient.
     * @param tank The tank to repair.
     */
    public void repairTank(Tank tank) { // increase the tanks health by 20 if the score is above 20
        int a = app.getAScore();
        int b = app.getBScore();
        int c = app.getCScore();
        int d = app.getDScore();
        if ((a >= repairCost || b >= repairCost || c >= repairCost || d >= repairCost) && (a > 0 || b > 0 || c > 0 || d > 0)) {
            if (tank.getHealth() < maxHealth) {
                tank.setHealth(Math.min(tank.getHealth() + 20, maxHealth));
                app.updateScore(tank.getTankName(), -repairCost);
            }
        }
    }

    /**
     * Buys additional fuel for a tank, increasing its fuel by 200 points if the player's score is sufficient.
     * @param tank The tank to refuel.
     */
    public void buyPetrol(Tank tank) { // increase the tanks fuel by 200 if the score is above 10
        int a = app.getAScore();
        int b = app.getBScore();
        int c = app.getCScore();
        int d = app.getDScore();
        if ((a >= petrolCost || b >= petrolCost || c >= petrolCost || d >= petrolCost) && (a > 0 || b > 0 || c > 0 || d > 0)) {
            tank.setPetrol(tank.remainingPetrol() + morePetrol);
            app.updateScore(tank.getTankName(), -petrolCost);
        }
    }

    /**
     * Purchases an additional parachute for a tank if the player's score is sufficient.
     * @param tank The tank to receive the parachute.
     */
    public void buyChute(Tank tank) { // extension to buy a new parachute
        int a = app.getAScore();
        int b = app.getBScore();
        int c = app.getCScore();
        int d = app.getDScore();
        if ((a >= chuteCost || b >= chuteCost || c >= chuteCost || d >= chuteCost) && (a > 0 || b > 0 || c > 0 || d > 0)) {
            tank.getParachute().setParachutes(tank.getParachute().getRemainingParachutes() + 1);
            app.updateScore(tank.getTankName(), -chuteCost);
        }
    }

    public void buyShield(Tank tank) {
        int a = app.getAScore();
        int b = app.getBScore();
        int c = app.getCScore();
        int d = app.getDScore();
        if ((a >= shieldCost || b >= shieldCost || c >= shieldCost || d >= shieldCost) && (a > 0 || b > 0 || c > 0 || d > 0)) {
            tank.setShield(true);
            app.updateScore(tank.getTankName(), -shieldCost);
        }

    }

}

