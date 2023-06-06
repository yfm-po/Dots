package sk.tuke.kpi.kp.dots.core.Dot;
import java.io.Serializable;

import static sk.tuke.kpi.kp.dots.core.color.Color.*;

public class Dot implements Serializable {

    private final DotColor color;
    private DotState state;
    private int row;
    private int column;
    private boolean isLatestSelected = false;

    public Dot(DotColor color, int row, int column) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }

        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Row and column cannot be negative");
        }

        this.color = color;
        this.setSelected(false);
        this.setRow(row);
        this.setColumn(column);
    }

    public boolean isSelected() {
        return state == DotState.SELECTED;
    }

    public DotColor getColor() {
        return color;
    }

    public void setSelected(boolean state) {
        this.state = state ? DotState.SELECTED : DotState.UNSELECTED;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean getIsLatestSelected() {
        return this.isLatestSelected;
    }

    public void setIsLatestSelected(boolean latestSelected) {
        this.isLatestSelected = latestSelected;
    }

    @Override
    public String toString() {
        String color = switch (this.color) {
            case RED -> ANSI_RED;
            case GREEN -> ANSI_GREEN;
            case YELLOW -> ANSI_YELLOW;
            case BLUE -> ANSI_BLUE;
        };
        return !this.isSelected() ? color + "●" + ANSI_RESET : ANSI_WHITE_BACKGROUND + color + "●" + ANSI_RESET;
    }
}
