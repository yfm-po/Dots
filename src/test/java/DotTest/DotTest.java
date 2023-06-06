package DotTest;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.core.Dot.Dot;
import sk.tuke.kpi.kp.dots.core.Dot.DotColor;
import sk.tuke.kpi.kp.dots.core.Dot.DotState;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DotTest {

    private final DotColor color;
    private DotState state;
    private int row = -1;
    private int column = -1;
    private Dot dot;

    public DotTest() {
        Random randomNumberGenerator = new Random();
        this.color = DotColor.values()[randomNumberGenerator.nextInt(DotColor.values().length)];
        this.row = randomNumberGenerator.nextInt(10);
        this.column = randomNumberGenerator.nextInt(10);
        this.dot = new Dot(this.color, this.row, this.column);
    }

    @Test
    public void checkDotColor() {
        assertEquals(this.color, this.dot.getColor(), "The color of the dot is not correct. -" +
                " The color of the dot should be equal to the color that was passed to the constructor.");
    }

    @Test
    public void checkDotState() {
        assertEquals(false, this.dot.isSelected(), "The state of the dot is not correct. -" +
                " The state of the dot should be equal to the state that was passed to the constructor.");
    }

    @Test
    public void checkDotRow() {
        assertEquals(this.row, this.dot.getRow(), "The row of the dot is not correct. -" +
                " The row of the dot should be equal to the row that was passed to the constructor.");
    }

    @Test
    public void checkDotColumn() {
        assertEquals(this.column, this.dot.getColumn(), "The column of the dot is not correct. -" +
                " The column of the dot should be equal to the column that was passed to the constructor.");
    }

    @Test
    public void checkDotStateAfterSelect() {
        this.dot.setSelected(true);
        assertEquals(true, this.dot.isSelected(), "The state of the dot is not correct. -" +
                " The state of the dot should be equal to the state that was passed to the constructor.");
    }

    @Test
    public void checkDotStateAfterUnselect() {
        this.dot.setSelected(false);
        assertEquals(false, this.dot.isSelected(), "The state of the dot is not correct. -" +
                " The state of the dot should be equal to the state that was passed to the constructor.");
    }
}
