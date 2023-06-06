package sk.tuke.kpi.kp.dots.core.PowerUpShop;

import sk.tuke.kpi.kp.dots.core.PowerUp.Bomb;
import sk.tuke.kpi.kp.dots.core.PowerUp.ExtraMoves;
import sk.tuke.kpi.kp.dots.core.PowerUp.ScoreMultiplier;
import static sk.tuke.kpi.kp.dots.core.color.Color.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PowerUpShop implements Serializable {

    private final List<Bomb> availableBombs = new ArrayList<>();
    private final List<ExtraMoves> availableExtraMoves = new ArrayList<>();
    private final List<ScoreMultiplier> availableScoreMultipliers = new ArrayList<>();
    private final int bombPrice;
    private final int extraMovesPrice;
    private final int scoreMultiplierPrice;

    public PowerUpShop(int amountOfEachPowerUp, int bombPrice, int extraMovesPrice, int scoreMultiplierPrice) {
        if (amountOfEachPowerUp < 0) {
            throw new IllegalArgumentException("Amount of each power-up cannot be negative");
        }

        if (bombPrice < 0 || extraMovesPrice < 0 || scoreMultiplierPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }

        this.fillWithPowerUps(amountOfEachPowerUp);
        this.bombPrice = bombPrice;
        this.extraMovesPrice = extraMovesPrice;
        this.scoreMultiplierPrice = scoreMultiplierPrice;
    }

    public List<Bomb> getAvailableBombs() {
        return availableBombs;
    }

    public List<ExtraMoves> getAvailableExtraMoves() {
        return availableExtraMoves;
    }

    public List<ScoreMultiplier> getAvailableScoreMultipliers() {
        return availableScoreMultipliers;
    }

    public void sellBomb() {
        if (this.availableBombs.size() < 1) {
            System.out.println(ANSI_RED + "There are no bombs available in the shop" + ANSI_RESET);
            return;
        }

        this.availableBombs.remove(0);
    }

    public void sellExtraMoves() {
        if (this.availableExtraMoves.size() < 1) {
            System.out.println(ANSI_RED + "There are no extra moves available in the shop" + ANSI_RESET);
            return;
        }

        this.availableExtraMoves.remove(0);
    }

    public void sellScoreMultiplier() {
        if (this.availableScoreMultipliers.size() < 1) {
            System.out.println(ANSI_RED + "There are no score multipliers in the shop" + ANSI_RESET);
            return;
        }

        this.availableScoreMultipliers.remove(0);
    }

    private void fillWithPowerUps(int amount) {
        for (int i = 0; i < amount; i++) {
            this.availableBombs.add(new Bomb(1, 2));
            this.availableExtraMoves.add(new ExtraMoves(1, 3));
            this.availableScoreMultipliers.add(new ScoreMultiplier(1, 2));
        }
    }

    public int getBombPrice() {
        return this.bombPrice;
    }

    public int getExtraMovesPrice() {
        return this.extraMovesPrice;
    }

    public int getScoreMultiplierPrice() {
        return this.scoreMultiplierPrice;
    }

    @Override
    public String toString() {
        return ANSI_PINK + "Welcome to the power-up shop!\n" +
                ANSI_BLUE +
                "┌─────────────────┬───────────────┬────────────────┐\n" +
                "│      ||||       │               │                │\n" +
                "│      |          │    *****      │     *******    │\n" +
                "│     ####        │    *          │     *     *    │\n" +
                "│    ######       │    ****       │     *****      │\n" +
                "│    ######       │    *          │   *     *      │\n" +
                "│     ####        │    *****      │   *******      │\n" +
                "│                 │               │                │\n" +
                "│                 │               │                │\n" +
                "│                 │               │                │\n" +
                "│                 │               │                │\n" +
                "└─────────────────┴───────────────┴────────────────┘\n" +
                "         " + this.availableBombs.size() + "                " + this.availableExtraMoves.size() + "                " + this.availableScoreMultipliers.size() + "          " +
                ANSI_RESET;
    }
}
