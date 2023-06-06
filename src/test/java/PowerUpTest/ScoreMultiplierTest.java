package PowerUpTest;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.core.PowerUp.ExtraMoves;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreMultiplierTest {
    private final Random randomNumberGenerator = new Random();
    private final int remainingUses;
    private final int multiplier;
    private ExtraMoves extraMoves;

    public ScoreMultiplierTest() {
        this.remainingUses = randomNumberGenerator.nextInt(10) + 1;
        this.multiplier = randomNumberGenerator.nextInt(10) + 2;
        this.extraMoves = new ExtraMoves(remainingUses, multiplier);
    }

    @Test
    public void checkAmountOfRemainingUsesAfterApplyingPowerUp() {
        extraMoves.setActiveState(true);
        extraMoves.applyPowerUp();
        assertEquals(remainingUses - 1, extraMoves.getRemainingUses(),
                "Amount of remaining uses after applying power up is not correct.");
    }

    @Test
    public void checkAmountOfRemainingUsesAfterApplyingPowerUpMultipleTimes() {
        int amountOfUses = randomNumberGenerator.nextInt(10) + 1;
        while (amountOfUses > remainingUses) {
            amountOfUses = randomNumberGenerator.nextInt(10) + 1;
        }

        extraMoves.setActiveState(true);
        for (int i = 0; i < amountOfUses; i++) {
            extraMoves.applyPowerUp();
        }
        assertEquals(remainingUses - amountOfUses, extraMoves.getRemainingUses(),
                "Amount of remaining uses after applying power up multiple times is not correct.");
    }

    @Test
    public void checkMoves() {
        assertEquals(multiplier, extraMoves.getMoves(),
                "Multiplier is not correct.");
    }

    @Test
    public void checkInitialActiveState() {
        assertEquals(false, extraMoves.getActiveState(),
                "Initial active state is not correct.");
    }

    @Test
    public void checkActiveStateAfterActivating() {
        extraMoves.setActiveState(true);
        assertEquals(true, extraMoves.getActiveState(),
                "Active state after activating is not correct.");
    }

    @Test
    public void checkActiveStateAfterDeactivating() {
        extraMoves.setActiveState(true);
        extraMoves.setActiveState(false);
        assertEquals(false, extraMoves.getActiveState(),
                "Active state after deactivating is not correct.");
    }
}
