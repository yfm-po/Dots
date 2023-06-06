package GameBoardTest;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameState;
import sk.tuke.kpi.kp.dots.core.PowerUp.Bomb;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameBoardTest {

    private final GameBoard gameBoard;
    private final int rowCount;
    private final int columnCount;
    private final int remainingMoves;

    public GameBoardTest() {
        Random randomNumberGenerator = new Random();
        this.rowCount = randomNumberGenerator.nextInt(5) + 4;
        this.columnCount = randomNumberGenerator.nextInt(5) + 4;
        this.remainingMoves = randomNumberGenerator.nextInt(10) + 20;
        this.gameBoard = new GameBoard(this.rowCount, this.columnCount, this.remainingMoves);
    }

    @Test
    public void checkDotsCount() {
        int dotsCount = 0;
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (this.gameBoard.getDot(i, j) != null) {
                    dotsCount++;
                }
            }
        }

        assertEquals(this.rowCount * this.columnCount, dotsCount, "The number of dots on the game board is not correct. -" +
                " The number of dots on the game board should be equal to the number of rows multiplied by the number of columns.");
    }

    @Test
    public void checkAllDotsAreUnselected() {
        boolean allDotsAreUnselected = true;
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (this.gameBoard.getDot(i, j).isSelected()) {
                    allDotsAreUnselected = false;
                }
            }
        }

        assertEquals(true, allDotsAreUnselected, "All dots on the game board should be unselected at the beginning of the game.");
    }

    @Test
    public void checkRemainingMoves() {
        assertEquals(this.remainingMoves, this.gameBoard.getRemainingMoves(), "The number of remaining moves is not correct. -" +
                " The number of remaining moves should be equal to the number of moves that the player has at the beginning of the game.");
    }

    @Test
    public void checkRemainingMovesAfterMove() {
        int remainingMoves = this.gameBoard.getRemainingMoves();
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (i + 1 < this.rowCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i + 1, j).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i + 1, j);
                    break;
                }

                if (j + 1 < this.columnCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i, j + 1).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i, j + 1);
                    break;
                }
            }
        }

        this.gameBoard.executeDots();

        assertEquals(remainingMoves - 1, this.gameBoard.getRemainingMoves(), "The number of remaining moves is not correct. -" +
                " The number of remaining moves should be decreased by one after each move.");
    }

    @Test
    public void checkGameState() {
        assertEquals(GameState.PLAYING, this.gameBoard.getGameState(), "The game state is not correct. -" +
                " The game state should be PLAYING at the beginning of the game.");
    }

    @Test
    public void checkGameStateAfterMoveWithOneRemainingMove() {
        this.gameBoard.setRemainingMoves(1);
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (i + 1 < this.rowCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i + 1, j).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i + 1, j);
                    break;
                }

                if (j + 1 < this.columnCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i, j + 1).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i, j + 1);
                    break;
                }
            }
        }

        this.gameBoard.executeDots();

        assertNotEquals(GameState.PLAYING, this.gameBoard.getGameState(), "The game state is not correct. -" +
                " The game state should be different from PLAYING after the last move.");
    }

    @Test
    public void checkEveryWhereAreDotsAfterMove() {
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (i + 1 < this.rowCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i + 1, j).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i + 1, j);
                    break;
                }

                if (j + 1 < this.columnCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i, j + 1).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i, j + 1);
                    break;
                }
            }
        }

        this.gameBoard.executeDots();

        boolean everyWhereAreDots = true;
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (this.gameBoard.getDot(i, j) == null) {
                    everyWhereAreDots = false;
                }
            }
        }

        assertEquals(true, everyWhereAreDots, "There should be dots everywhere after each move.");
    }

    @Test
    public void checkMoveAbilityWithNoMoves() {
        this.gameBoard.setRemainingMoves(0);
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (i + 1 < this.rowCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i + 1, j).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i + 1, j);
                    break;
                }

                if (j + 1 < this.columnCount && this.gameBoard.getDot(i, j).getColor() == this.gameBoard.getDot(i, j + 1).getColor()) {
                    this.gameBoard.selectDot(i, j);
                    this.gameBoard.selectDot(i, j + 1);
                    break;
                }
            }
        }

        this.gameBoard.executeDots();

        assertEquals(0, this.gameBoard.getRemainingMoves(), "The number of remaining moves is not correct. -" +
                " The number of remaining moves should not be decreased when the player has no remaining moves.");
    }

    @Test
    public void checkNotSelectedPowerUpsHaveInactiveState() {
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        for (int i = 0; i < this.gameBoard.getPowerUpsCount(); i++) {
            assertEquals(false, this.gameBoard.getPowerUp(i).getActiveState(), "The power up state is not correct. -" +
                    " The power up state should be INACTIVE when the player has not selected a power up.");
        }
    }

    @Test
    public void checkSelectingPowerUpGetsSelectedState() {
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        this.gameBoard.selectPowerUp(0);
        assertEquals(true, this.gameBoard.getPowerUp(0).getActiveState(), "The power up state is not correct. -" +
                " The power up state should be SELECTED when the player selects a power up.");
    }

    @Test
    public void checkEveryWhereAreDotsAfterUsingBomb() {
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        this.gameBoard.selectPowerUp(0);
        this.gameBoard.applyPowerUp(0, 0, 0);
        boolean everyWhereAreDots = true;
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (this.gameBoard.getDot(i, j) == null) {
                    everyWhereAreDots = false;
                }
            }
        }

        assertEquals(true, everyWhereAreDots, "There should be dots everywhere after using a bomb.");
    }

    @Test
    public void checkEveryWhereAreDotsAfterUsingBombWithNoMoves() {
        this.gameBoard.setRemainingMoves(0);
        this.gameBoard.getPowerUps().add(new Bomb(1, 2));
        this.gameBoard.selectPowerUp(0);
        this.gameBoard.applyPowerUp(0, 0, 0);
        boolean everyWhereAreDots = true;
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if (this.gameBoard.getDot(i, j) == null) {
                    everyWhereAreDots = false;
                }
            }
        }

        assertEquals(true, everyWhereAreDots, "There should be dots everywhere after using a bomb.");
    }
}
