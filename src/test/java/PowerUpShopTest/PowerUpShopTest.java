package PowerUpShopTest;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.core.PowerUpShop.PowerUpShop;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerUpShopTest {

    private final PowerUpShop powerUpShop;
    private final int bombPrice;
    private final int extraMovesPrice;
    private final int scoreMultiplierPrice;

    public PowerUpShopTest() {
        Random random = new Random();
        this.bombPrice = random.nextInt(100);
        this.extraMovesPrice = random.nextInt(100);
        this.scoreMultiplierPrice = random.nextInt(100);
        int amountOfEachPowerUp = random.nextInt(100);
        this.powerUpShop = new PowerUpShop(amountOfEachPowerUp, this.bombPrice, this.extraMovesPrice, this.scoreMultiplierPrice);
    }

    @Test
    public void testSellBomb() {
        int initialAmountOfBombs = this.powerUpShop.getAvailableBombs().size();
        this.powerUpShop.sellBomb();
        int finalAmountOfBombs = this.powerUpShop.getAvailableBombs().size();
        assertEquals(initialAmountOfBombs - 1, finalAmountOfBombs);
    }

    @Test
    public void testSellExtraMoves() {
        int initialAmountOfExtraMoves = this.powerUpShop.getAvailableExtraMoves().size();
        this.powerUpShop.sellExtraMoves();
        int finalAmountOfExtraMoves = this.powerUpShop.getAvailableExtraMoves().size();
        assertEquals(initialAmountOfExtraMoves - 1, finalAmountOfExtraMoves);
    }

    @Test
    public void testSellScoreMultiplier() {
        int initialAmountOfScoreMultipliers = this.powerUpShop.getAvailableScoreMultipliers().size();
        this.powerUpShop.sellScoreMultiplier();
        int finalAmountOfScoreMultipliers = this.powerUpShop.getAvailableScoreMultipliers().size();
        assertEquals(initialAmountOfScoreMultipliers - 1, finalAmountOfScoreMultipliers);
    }

    @Test
    public void testGetBombPrice() {
        assertEquals(this.bombPrice, this.powerUpShop.getBombPrice());
    }

    @Test
    public void testGetExtraMovesPrice() {
        assertEquals(this.extraMovesPrice, this.powerUpShop.getExtraMovesPrice());
    }

    @Test
    public void testGetScoreMultiplierPrice() {
        assertEquals(this.scoreMultiplierPrice, this.powerUpShop.getScoreMultiplierPrice());
    }
}
