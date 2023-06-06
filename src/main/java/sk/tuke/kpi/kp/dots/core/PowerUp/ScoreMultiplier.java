package sk.tuke.kpi.kp.dots.core.PowerUp;

public class ScoreMultiplier extends PowerUp {

    private final int multiplier;
    private int remainingUses;

    public ScoreMultiplier(int remainingUses, int multiplier) {
        super(remainingUses);
        this.remainingUses = remainingUses;
        this.multiplier = multiplier;
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

    public int getMultiplier() {
        return this.multiplier;
    }
}
