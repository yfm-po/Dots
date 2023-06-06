package sk.tuke.kpi.kp.dots.core.PowerUp;

public class Bomb extends PowerUp {

    private final int explosionRadius;
    private int remainingUses;

    public Bomb(int remainingUses, int explosionRadius) {
        super(remainingUses);
        this.remainingUses = remainingUses;
        this.explosionRadius = explosionRadius;
    }

    @Override
    public void applyPowerUp() {
        if (this.getActiveState() && this.getRemainingUses() > 0) {
            this.remainingUses--;
        }
    }

    public int getRemainingUses() {
        return this.remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public int getExplosionRadius() {
        return this.explosionRadius;
    }
}
