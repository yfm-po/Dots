package sk.tuke.kpi.kp.dots.core.PowerUp;

import java.io.Serializable;

public abstract class PowerUp implements Serializable {

    private boolean activeState = false;
    private int remainingUses;

    public PowerUp(int remainingUses) {
        if (remainingUses < 0) {
            throw new IllegalArgumentException("Remaining uses cannot be negative");
        }

        this.remainingUses = remainingUses;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public void applyPowerUp() {
        if (this.isActive() && this.getRemainingUses() > 0) {
            this.remainingUses--;

            if (this.getRemainingUses() == 0) {
                this.setActiveState(false);
            }
        }
    }

    public boolean isActive() {
        return this.activeState;
    }

    public boolean getActiveState() {
        return this.activeState;
    }

    public int getRemainingUses() {
        return this.remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public String getName() {
        if (this instanceof ExtraMoves) {
            return "ExtraMoves";
        } else if (this instanceof Bomb) {
            return "Bomb";
        } else if (this instanceof ScoreMultiplier) {
            return "ScoreMultiplier";
        }

        return null;
    }
}
