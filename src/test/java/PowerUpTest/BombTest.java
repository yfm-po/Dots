package PowerUpTest;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.core.PowerUp.Bomb;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BombTest {

    private final Random randomNumberGenerator = new Random();
    private final int remainingUses;
    private final int explosionRadius;
    private final Bomb bomb;

    public BombTest() {
        this.explosionRadius = randomNumberGenerator.nextInt(10) + 1;
        this.remainingUses = randomNumberGenerator.nextInt(10) + 1;
        this.bomb = new Bomb(remainingUses, explosionRadius);
    }

    @Test
    public void checkAmountOfRemainingUsesAfterApplyingPowerUp() {
        bomb.setActiveState(true);
        bomb.applyPowerUp();
        assertEquals(remainingUses - 1, bomb.getRemainingUses(),
                "Amount of remaining uses after applying power up is not correct.");
    }

    @Test
    public void checkAmountOfRemainingUsesAfterApplyingPowerUpMultipleTimes() {
        int amountOfUses = randomNumberGenerator.nextInt(10) + 1;
        while (amountOfUses > remainingUses) {
            amountOfUses = randomNumberGenerator.nextInt(10) + 1;
        }

        bomb.setActiveState(true);
        for (int i = 0; i < amountOfUses; i++) {
            bomb.applyPowerUp();
        }
        assertEquals(remainingUses - amountOfUses, bomb.getRemainingUses(),
                "Amount of remaining uses after applying power up multiple times is not correct.");
    }

    @Test
    public void checkExplosionRadius() {
        assertEquals(explosionRadius, bomb.getExplosionRadius(),
                "Explosion radius is not correct.");
    }

    @Test
    public void checkInitialActiveState() {
        assertEquals(false, bomb.getActiveState(),
                "Initial active state is not correct.");
    }

    @Test
    public void checkActiveStateAfterActivating() {
        bomb.setActiveState(true);
        assertEquals(true, bomb.getActiveState(),
                "Active state after activating is not correct.");
    }

    @Test
    public void checkActiveStateAfterDeactivating() {
        bomb.setActiveState(false);
        assertEquals(false, bomb.getActiveState(),
                "Active state after deactivating is not correct.");
    }
}
