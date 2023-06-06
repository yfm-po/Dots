package sk.tuke.kpi.kp.dots.core.PowerUp;

public class ExtraMoves extends PowerUp {

    private final int moves;
    private int remainingUses;

    public ExtraMoves(int remainingUses, int moves) {
        super(remainingUses);
        this.remainingUses = remainingUses;
        this.moves = moves;
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

    public int getMoves() {
        return this.moves;
    }
}
