package sk.tuke.kpi.kp.dots.core.GameBoard;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.dots.core.Dot.*;
import sk.tuke.kpi.kp.dots.core.PowerUp.*;
import sk.tuke.kpi.kp.dots.core.PowerUpShop.PowerUpShop;
import sk.tuke.kpi.kp.dots.service.Exception.GameStudioException;

import static sk.tuke.kpi.kp.dots.core.color.Color.*;

import java.io.*;
import java.util.*;

@Getter
@Setter
public class GameBoard implements Serializable {

    private String player;
    private String game;
    private Date playedAt;

    private Dot[][] dots;

    private List<PowerUp> powerUps = new ArrayList<>();

    @Serial
    private static final long serialVersionUID = -6201713747848327015L;
    private static final String FILE = "dots.bin";
    private GameState gameState = GameState.PLAYING;
    private PowerUpShop powerUpShop;
    private final int rowCount;
    private final int columnCount;
    private int remainingMoves;
    private int executedRedDots = 0;
    private int executedGreenDots = 0;
    private int executedBlueDots = 0;
    private int executedYellowDots = 0;
    private final int neededRedDotsToBeExecuted;
    private final int neededGreenDotsToBeExecuted;
    private final int neededBlueDotsToBeExecuted;
    private final int neededYellowDotsToBeExecuted;
    private int currentScore = 0;
    private int scoreMultiplier = 1;
    private final int neededScoreToBeSolved;

    public GameBoard() {
        this.game = "dots";
        this.player = "";
        this.playedAt = new Date();
        this.rowCount = 0;
        this.columnCount = 0;
        this.remainingMoves = 0;
        this.neededScoreToBeSolved = 0;
        this.neededRedDotsToBeExecuted = 0;
        this.neededGreenDotsToBeExecuted = 0;
        this.neededBlueDotsToBeExecuted = 0;
        this.neededYellowDotsToBeExecuted = 0;
    }

    public GameBoard(int rowCount, int columnCount, int remainingMoves) {
        if (rowCount < 1 || columnCount < 1 || remainingMoves < 1) {
            throw new IllegalArgumentException("Invalid game board dimensions or remaining moves.");
        }

        if (remainingMoves < (rowCount * columnCount) / 4) {
            throw new IllegalArgumentException("Remaining moves cannot be less than a quarter of the total number of dots.");
        }

        this.game = "dots";
        this.player = "";
        this.playedAt = new Date();

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.remainingMoves = remainingMoves;
        this.neededScoreToBeSolved = (rowCount * columnCount) / 2;

        this.neededRedDotsToBeExecuted
                = this.neededBlueDotsToBeExecuted
                = this.neededGreenDotsToBeExecuted
                = this.neededYellowDotsToBeExecuted
                = this.neededScoreToBeSolved / 2;

        this.powerUpShop = new PowerUpShop(this.remainingMoves / 2,
                this.neededScoreToBeSolved / 2,
                this.neededScoreToBeSolved / 3,
                this.neededScoreToBeSolved / 4);

        this.generate();
    }

    public void selectDot(int row, int column) {
        Dot dotBeingSelected = this.dots[row][column];

        if (dotBeingSelected.isSelected()) {
            System.out.println(ANSI_RED + "The dot is already selected." + ANSI_RESET);
            return;
        }

        if (!this.isAnyDotSelected()) {
            dotBeingSelected.setSelected(true);
            dotBeingSelected.setIsLatestSelected(true);
        } else {
            if (this.isAdjacentWithLatestSelectedDot(row, column)) {
                dotBeingSelected.setSelected(true);
                dotBeingSelected.setIsLatestSelected(true);
            } else {
                this.unselectEveryDot();
                System.out.println(ANSI_RED +
                        "The dot is not adjacent to the previously selected " +
                        "dot or they are not from the same color" + ANSI_RESET);
            }
        }
    }

    private boolean isAdjacentWithLatestSelectedDot(int row, int column) {
        if (row > 0 && this.dots[row - 1][column].getIsLatestSelected() && this.dots[row - 1][column].getColor() == this.dots[row][column].getColor()) {
            this.dots[row - 1][column].setIsLatestSelected(false);
            return true;
        }

        if (row < this.rowCount - 1 && this.dots[row + 1][column].getIsLatestSelected() && this.dots[row + 1][column].getColor() == this.dots[row][column].getColor()) {
            this.dots[row + 1][column].setIsLatestSelected(false);
            return true;
        }

        if (column > 0 && this.dots[row][column - 1].getIsLatestSelected() && this.dots[row][column - 1].getColor() == this.dots[row][column].getColor()) {
            this.dots[row][column - 1].setIsLatestSelected(false);
            return true;
        }

        if (column < this.columnCount - 1 && this.dots[row][column + 1].getIsLatestSelected() && this.dots[row][column + 1].getColor() == this.dots[row][column].getColor()) {
            this.dots[row][column + 1].setIsLatestSelected(false);
            return true;
        }

        return false;
    }

    private boolean isAnyDotSelected() {
        return Arrays.stream(this.dots).flatMap(Arrays::stream).anyMatch(Dot::isSelected);
    }

    public void unselectDot(int row, int column) {
        if (this.dots == null || row < 0 || row >= rowCount || column < 0 || column >= columnCount) {
            System.out.println(ANSI_RED + "Invalid dot coordinates." + ANSI_RESET);
            return;
        }

        if (!this.dots[row][column].isSelected()) {
            System.out.println(ANSI_RED + "The dot is not selected." + ANSI_RESET);
            return;
        }

        this.dots[row][column].setSelected(false);
    }

    public Dot getDot(int row, int column) {
        if (this.dots == null || row < 0 || row >= rowCount || column < 0 || column >= columnCount) {
            return null;
        }

        return this.dots[row][column];
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public PowerUp getPowerUp(int index) {
        if (this.powerUps == null || index < 0 || index >= this.powerUps.size()) {
            return null;
        }

        return this.powerUps.get(index);
    }

    public int getRemainingMoves() {
        return this.remainingMoves;
    }

    public int getScore() {
        return this.currentScore;
    }

    public int getNeededScoreToBeSolved() {
        return this.neededScoreToBeSolved;
    }

    private void fillWithRandomColoredDots(Dot[][] dots) {
        Random randomNumberGenerator = new Random();

        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                switch (randomNumberGenerator.nextInt(4)) {
                    case 0 -> dots[row][column] = new Dot(DotColor.RED, row, column);
                    case 1 -> dots[row][column] = new Dot(DotColor.BLUE, row, column);
                    case 2 -> dots[row][column] = new Dot(DotColor.GREEN, row, column);
                    case 3 -> dots[row][column] = new Dot(DotColor.YELLOW, row, column);
                }
            }
        }
    }

    private void generate() {
        this.dots = new Dot[this.rowCount][this.columnCount];
        this.fillWithRandomColoredDots(this.dots);

        while (!this.isPlayable()) {
            this.fillWithRandomColoredDots(this.dots);
        }
    }

    private int possibleMoves() {
        return Arrays.stream(this.dots).flatMap(Arrays::stream).mapToInt(dot -> this.countAdjacentDotsWithTheSameColor(dot.getRow(), dot.getColumn())).sum();
    }

    private boolean isPlayable() {
        return this.possibleMoves() >= (this.getRowCount() * this.getColumnCount()) / 4;
    }

    private int countAdjacentDotsWithTheSameColor(int row, int column) {
        if (this.dots == null || row < 0 || row >= rowCount || column < 0 || column >= columnCount) {
            return 0;
        }

        int count = 0;
        Dot dot = this.getDot(row, column);

        if (row > 0 && this.dots[row - 1][column].getColor() == dot.getColor()) {
            count++;
        }

        if (row < rowCount - 1 && this.dots[row + 1][column].getColor() == dot.getColor()) {
            count++;
        }

        if (column > 0 && this.dots[row][column - 1].getColor() == dot.getColor()) {
            count++;
        }

        if (column < columnCount - 1 && this.dots[row][column + 1].getColor() == dot.getColor()) {
            count++;
        }

        return count;
    }

    private List<Dot> checkHorizontalDotCombinations() {
        List<Dot> horizontallyMarkedDots = new ArrayList<>();

        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                Dot dot = this.getDot(row, column);

                if (dot.isSelected() && !horizontallyMarkedDots.contains(dot)) {
                    horizontallyMarkedDots.add(dot);

                    for (int i = column + 1; i < this.getColumnCount(); i++) {
                        Dot rightDot = this.getDot(row, i);

                        if (rightDot.isSelected() && rightDot.getColor() == dot.getColor()) {
                            horizontallyMarkedDots.add(rightDot);
                        } else {
                            break;
                        }
                    }

                    this.checkLeftSide(horizontallyMarkedDots, row, column, dot);
                }
            }
        }

        return horizontallyMarkedDots;
    }

    private void checkLeftSide(List<Dot> horizontallyMarkedDots, int row, int column, Dot dot) {
        for (int i = column - 1; i >= 0; i--) {
            Dot leftDot = this.getDot(row, i);

            if (leftDot.isSelected() && leftDot.getColor() == dot.getColor()) {
                horizontallyMarkedDots.add(leftDot);
            } else {
                break;
            }
        }
    }

    private List<Dot> checkVerticalDotCombinations() {
        List<Dot> verticallyMarkedDots = new ArrayList<>();

        for (int column = 0; column < this.getColumnCount(); column++) {
            for (int row = 0; row < this.getRowCount(); row++) {
                Dot dot = this.getDot(row, column);

                if (dot.isSelected() && !verticallyMarkedDots.contains(dot)) {
                    verticallyMarkedDots.add(dot);

                    for (int i = row + 1; i < this.getRowCount(); i++) {
                        Dot bottomDot = this.getDot(i, column);

                        if (bottomDot.isSelected() && bottomDot.getColor() == dot.getColor()) {
                            verticallyMarkedDots.add(bottomDot);
                        } else {
                            break;
                        }
                    }

                    for (int i = row - 1; i >= 0; i--) {
                        Dot topDot = this.getDot(i, column);

                        if (topDot.isSelected() && topDot.getColor() == dot.getColor()) {
                            verticallyMarkedDots.add(topDot);
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return verticallyMarkedDots;
    }

    private void removeDot(int row, int column) {
        if (this.dots == null || row < 0 || row >= rowCount || column < 0 || column >= columnCount) {
            return;
        }

        this.dots[row][column] = null;
    }

    public void executeDots() {
        if (this.remainingMoves < 1 || this.gameState != GameState.PLAYING) {
            return;
        }

        this.removeSelectedDotsWithTheSameColors();

        this.remainingMoves--;
        this.refill();
        this.unselectEveryDot();
        this.updateGameState();
    }

    private void removeSelectedDotsWithTheSameColors() {
        List<Dot> horizontalDots = this.checkHorizontalDotCombinations();
        List<Dot> verticalDots = this.checkVerticalDotCombinations();
        List<Dot> everySelectedDot = new ArrayList<>();

        everySelectedDot.addAll(verticalDots);
        everySelectedDot.addAll(horizontalDots);

        if ((everySelectedDot.size() / 2) < 2) {
            this.unselectEveryDot();
            System.out.println(ANSI_RED + "You should select at least 2 dots!" + ANSI_RESET);
            return;
        }

        for (int i = 0; i < everySelectedDot.size() - 1; i++) {
            if (everySelectedDot.get(i).getColor() != everySelectedDot.get(i + 1).getColor()) {
                this.unselectEveryDot();
                System.out.println(ANSI_RED + "You should select dots with the same color!" + ANSI_RESET);
                return;
            }
        }

        for (Dot dot : everySelectedDot) {
            this.removeDot(dot.getRow(), dot.getColumn());
        }

        for (int i = 0; i < everySelectedDot.size() / 2; i++) {
            this.incrementExecutedDotColorCount(everySelectedDot.get(i).getColor());
        }

        System.out.println(ANSI_GREEN + "Dots were successfully executed!" + ANSI_RESET);
        this.currentScore += (everySelectedDot.size() / 2) * this.scoreMultiplier;
    }

    private void incrementExecutedDotColorCount(DotColor color) {
        switch (color) {
            case RED -> this.executedRedDots++;
            case GREEN -> this.executedGreenDots++;
            case BLUE -> this.executedBlueDots++;
            case YELLOW -> this.executedYellowDots++;
        }
    }

    private void unselectEveryDot() {
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                if (this.getDot(row, column) != null) {
                    this.getDot(row, column).setSelected(false);
                }
            }
        }
    }

    public void selectPowerUp(int index) {
        if (index < 0 || index >= this.powerUps.size()) {
            return;
        }

        this.powerUps.get(index).setActiveState(true);
    }

    public void applyPowerUp(String powerUpName, int row, int column) {
        this.powerUps.stream().filter(powerUp -> powerUp.getName().equals(powerUpName)).findFirst().ifPresent(powerUp -> powerUp.setActiveState(true));

        if (powerUpName.equals("Bomb")) {
            this.BombBehavior(row, column, 1);
        } else if (powerUpName.equals("ExtraMoves")) {
            this.remainingMoves += 5;
        } else if (powerUpName.equals("ScoreMultiplier")) {
            this.scoreMultiplier += 2;
        }

        for (int i = 0; i < this.powerUps.size(); i++) {
            if (this.powerUps.get(i).getName().equals(powerUpName)) {
                this.powerUps.remove(i);
                break;
            }
        }
    }

    private void BombBehavior(int row, int column, int radius) {
        if (row < 0 || row >= this.getRowCount() || column < 0 || column >= this.getColumnCount()) {
            return;
        }

        this.removeDot(row, column);
        int removedDots = 1;

        for (int i = 1; i <= radius; i++) {
            if (row - i >= 0) {
                this.incrementExecutedDotColorCount(this.getDot(row - i, column).getColor());
                this.removeDot(row - i, column);
                removedDots++;
            }
            if (row + i < this.getRowCount()) {
                this.incrementExecutedDotColorCount(this.getDot(row + i, column).getColor());
                this.removeDot(row + i, column);
                removedDots++;
            }
            if (column - i >= 0) {
                this.incrementExecutedDotColorCount(this.getDot(row, column - i).getColor());
                this.removeDot(row, column - i);
                removedDots++;
            }
            if (column + i < this.getColumnCount()) {
                this.incrementExecutedDotColorCount(this.getDot(row, column + i).getColor());
                this.removeDot(row, column + i);
                removedDots++;
            }
            if (row - i >= 0 && column - i >= 0) {
                this.incrementExecutedDotColorCount(this.getDot(row - i, column - i).getColor());
                this.removeDot(row - i, column - i);
                removedDots++;
            }
            if (row - i >= 0 && column + i < this.getColumnCount()) {
                this.incrementExecutedDotColorCount(this.getDot(row - i, column + i).getColor());
                this.removeDot(row - i, column + i);
                removedDots++;
            }
            if (row + i < this.getRowCount() && column - i >= 0) {
                this.incrementExecutedDotColorCount(this.getDot(row + i, column - i).getColor());
                this.removeDot(row + i, column - i);
                removedDots++;
            }
            if (row + i < this.getRowCount() && column + i < this.getColumnCount()) {
                this.incrementExecutedDotColorCount(this.getDot(row + i, column + i).getColor());
                this.removeDot(row + i, column + i);
                removedDots++;
            }
        }

        this.currentScore += removedDots * this.scoreMultiplier;
        this.refill();
        this.updateGameState();
    }

    public void updateGameState() {
        if (this.executedRedDots >= this.neededRedDotsToBeExecuted && this.executedGreenDots >= this.neededGreenDotsToBeExecuted
                && this.executedBlueDots >= this.neededBlueDotsToBeExecuted && this.executedYellowDots >= this.neededYellowDotsToBeExecuted) {
            this.gameState = GameState.WON;
        }

        if (this.remainingMoves == 0 && (this.executedRedDots < this.neededRedDotsToBeExecuted || this.executedGreenDots < this.neededGreenDotsToBeExecuted
                || this.executedBlueDots < this.neededBlueDotsToBeExecuted || this.executedYellowDots < this.neededYellowDotsToBeExecuted)) {
            this.gameState = GameState.LOST;
        }
    }

    private void makeDotsFallDown() {
        for (int column = 0; column < this.getColumnCount(); column++) {
            for (int row = this.getRowCount() - 1; row >= 0; row--) {
                if (this.getDot(row, column) != null) {
                    int emptyDotPlacesBelow = 0;
                    for (int i = row + 1; i < this.getRowCount(); i++) {
                        if (this.getDot(i, column) == null) {
                            emptyDotPlacesBelow++;
                        }
                    }

                    if (emptyDotPlacesBelow > 0) {
                        this.dots[row + emptyDotPlacesBelow][column] = this.dots[row][column];
                        this.dots[row + emptyDotPlacesBelow][column].setRow(row + emptyDotPlacesBelow);
                        this.dots[row][column] = null;
                    }
                }
            }
        }
    }

    private void refillEmptyDotPlaces() {
        Random randomNumberGenerator = new Random();
        for (int column = 0; column < this.getColumnCount(); column++) {
            for (int row = this.getRowCount() - 1; row >= 0; row--) {
                if (this.getDot(row, column) == null) {
                    switch (randomNumberGenerator.nextInt(4)) {
                        case 0 -> this.dots[row][column] = new Dot(DotColor.RED, row, column);
                        case 1 -> this.dots[row][column] = new Dot(DotColor.BLUE, row, column);
                        case 2 -> this.dots[row][column] = new Dot(DotColor.GREEN, row, column);
                        case 3 -> this.dots[row][column] = new Dot(DotColor.YELLOW, row, column);
                    }
                }
            }
        }
    }

    private void refill() {
        this.makeDotsFallDown();
        this.refillEmptyDotPlaces();
    }

    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new GameStudioException(e);
        }
    }

    public static GameBoard load() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE))) {
            return readObject(objectInputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new GameStudioException(e);
        }
    }

    private static GameBoard readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return (GameBoard) objectInputStream.readObject();
    }

    public void buyPowerUp(String powerUpName) {
        if (powerUpName.equals("Bomb")) {
            this.powerUpShop.sellBomb();
            this.setCurrentScore(this.getCurrentScore() - this.powerUpShop.getBombPrice());
            this.powerUps.add(new Bomb(1, 3));
        } else if (powerUpName.equals("ExtraMoves")) {
            this.powerUpShop.sellExtraMoves();
            this.setCurrentScore(this.getCurrentScore() - this.powerUpShop.getExtraMovesPrice());
            this.powerUps.add(new ExtraMoves(1, 3));
        } else if (powerUpName.equals("ScoreMultiplier")) {
            this.powerUpShop.sellScoreMultiplier();
            this.setCurrentScore(this.getCurrentScore() - this.powerUpShop.getScoreMultiplierPrice());
            this.powerUps.add(new ScoreMultiplier(1, 3));
        } else {
            throw new GameStudioException("Invalid power up name!");
        }
    }
}
