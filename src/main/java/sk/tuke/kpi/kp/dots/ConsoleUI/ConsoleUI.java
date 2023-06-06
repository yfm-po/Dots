package sk.tuke.kpi.kp.dots.ConsoleUI;

import sk.tuke.kpi.kp.dots.core.Dot.Dot;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameState;
import sk.tuke.kpi.kp.dots.core.PowerUpShop.PowerUpShop;
import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceJDBC;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceJDBC;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceJDBC;
import static sk.tuke.kpi.kp.dots.core.color.Color.*;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final Pattern INPUT_PATTERN = Pattern.compile(("([SUPB])([A-Z])([1-9][0-9]*)"));
    private final Scanner scanner = new Scanner(System.in);
    private GameBoard gameBoard;
    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;
    private String playerName;

    public ConsoleUI(ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        this.printWelcomeMessage();
        System.out.println(ANSI_GREEN + "Do you want to play a saved game? (Y/N)" + ANSI_RESET);
        String input = this.scanner.nextLine().toUpperCase();
        if (input.equals("Y")) {
            if (GameBoard.load() == null) {
                this.gameBoard = this.handleLevelSelect();
            } else {
                this.gameBoard = GameBoard.load();
            }
        } else {
            this.gameBoard = this.handleLevelSelect();
        }
    }

    public ConsoleUI() {
        this.scoreService = new ScoreServiceJDBC();
        this.commentService = new CommentServiceJDBC();
        this.ratingService = new RatingServiceJDBC();
        this.printWelcomeMessage();
        System.out.println(ANSI_GREEN + "Do you want to play a saved game? (Y/N)" + ANSI_RESET);
        String input = this.scanner.nextLine().toUpperCase();
        if (input.equals("Y")) {
            if (GameBoard.load() == null) {
                this.gameBoard = this.handleLevelSelect();
            } else {
                this.gameBoard = GameBoard.load();
            }
        } else {
            this.gameBoard = this.handleLevelSelect();
        }
    }

    private GameBoard handleLevelSelect() {
        System.out.println(ANSI_RED + "Select level (1-10): " + ANSI_RESET);
        String level = this.scanner.nextLine();
        switch (level) {
            case "1" -> {
                return new GameBoard(2, 2, 4);
            }
            case "2" -> {
                return new GameBoard(3, 3, 4);
            }
            case "3" -> {
                return new GameBoard(4, 4, 4);
            }
            case "4" -> {
                return new GameBoard(5, 5, 6);
            }
            case "5" -> {
                return new GameBoard(6, 6, 9);
            }
            case "6" -> {
                return new GameBoard(7, 7, 12);
            }
            case "7" -> {
                return new GameBoard(8, 8, 16);
            }
            case "8" -> {
                return new GameBoard(9, 9, 21);
            }
            case "9" -> {
                return new GameBoard(10, 10, 25);
            }
            case "10" -> {
                return new GameBoard(11, 11, 31);
            }
            default -> {
                this.displayError("Invalid level! Please select a level between 1 and 10!");
                return this.handleLevelSelect();
            }
        }
    }

    public void play() {
        this.playerName = this.askForName();

        do {
            this.gameLoopIteration();
        } while (this.gameBoard.getGameState() == GameState.PLAYING);

        this.displayGameBoard();
        System.out.println("\n" + this.gameBoard.getGameState());

        this.scoreService.addScore(new Score(this.playerName, "dots", this.gameBoard.getScore(), new Date()));
        this.askForComment(this.playerName);
        this.askForRating(this.playerName);

        System.out.println(ANSI_BLUE + "Do you want to play again? (Y/N): " + ANSI_RESET);
        String playAgainInput = this.scanner.nextLine().toUpperCase();

        if (playAgainInput.equals("Y")) {
            this.gameBoard = this.handleLevelSelect();
            this.play();
        } else {
            this.gameBoard.setGameState(GameState.ENDED);
            System.out.println(this.gameBoard.getGameState());
        }
    }

    private String askForName() {
        System.out.println(ANSI_GREEN + "Enter your name: " + ANSI_RESET);
        String name = this.scanner.nextLine();

        while (name.isEmpty() || name.isBlank()) {
            this.displayError("Name cannot be empty! " +
                    "Please enter a username (at least 1 character long): ");
            name = this.scanner.nextLine();
        }

        this.gameBoard.setPlayer(name);
        return name;
    }

    private void askForRating(String name) {
        System.out.println(ANSI_PINK + "Do you want to rate this game (Y/N)?" + ANSI_RESET);

        String ratingInput = this.scanner.nextLine().toUpperCase();

        if (ratingInput.equals("Y")) {
            System.out.println(ANSI_PINK + "Enter your rating (1-5): " + ANSI_RESET);
            this.handleRatingInput(name);
        }
    }

    private void handleRatingInput(String name) {
        try {
            int rating = this.scanner.nextInt();
            if (rating < 1 || rating > 5) {
                this.displayError("Invalid rating! Enter a rating between 1 and 5!");
                this.scanner.nextLine();
                this.handleRatingInput(name);
            } else {
                this.ratingService.setRating(new Rating(name, "dots", rating, new Date()));
            }
        } catch (NumberFormatException | InputMismatchException e) {
            this.displayError("Invalid rating! Enter a rating between 1 and 5!");
            this.scanner.nextLine();
            this.handleRatingInput(name);
        }

        this.scanner.nextLine();
    }

    private void askForComment(String name) {
        System.out.println(ANSI_PINK + "What is your opinion about this game?: (to skip just leave it blank)" + ANSI_RESET);
        String comment = this.scanner.nextLine();

        if (comment.isEmpty() || comment.isBlank()) {
            return;
        }

        this.commentService.addComment(new Comment(name, "dots", comment, new Date()));
    }

    private void printWelcomeMessage() {
        System.out.println("\n"
                + ANSI_RED
                + "┌─────────────────────────────────────┐\n"
                + "│  "+ ANSI_GREEN + "██████╗░░█████╗░████████╗░██████╗"  + ANSI_RED + "  │\n"
                + "│  "+ANSI_GREEN + "██╔══██╗██╔══██╗╚══██╔══╝██╔════╝" + ANSI_RED + "  │\n"
                + "│  " + ANSI_GREEN + "██║░░██║██║░░██║░░░██║░░░╚█████╗░" + ANSI_RED + "  │\n"
                + "│  " + ANSI_GREEN + "██║░░██║██║░░██║░░░██║░░░░╚═══██╗" + ANSI_RED + "  │\n"
                + "│  " + ANSI_GREEN + "██████╔╝╚█████╔╝░░░██║░░░██████╔╝" + ANSI_RED + "  │\n"
                + "│  " + ANSI_GREEN + "╚═════╝░░╚════╝░░░░╚═╝░░░╚═════╝░" + ANSI_RED + "  │\n"
                + "└─────────────────────────────────────┘\n" + ANSI_RESET);

        System.out.println("The goal of the game is to connect " +
                ANSI_RED + "D" + ANSI_GREEN + "O" + ANSI_BLUE + "T" + ANSI_YELLOW + "S" + ANSI_RESET + " \uD83C\uDF0D " +
                "of the same color horizontally or/and vertically and reach enough points with it!");

        System.out.println(ANSI_CYAN + "\nYou can buy power-ups to help you with that. \uD83D\uDE0E\uD83D\uDC4C\uD83D\uDD25\n" + ANSI_RESET);

        System.out.println("Press " + ANSI_YELLOW + "H" + ANSI_RESET + " to see the Hall of Fame, Comments and Ratings.");
        String input = this.scanner.nextLine().toUpperCase();

        if (input.equals("H")) {
            this.displayHallOfFame();
            this.displayComments();
            this.displayRatings();
        }

        System.out.println(ANSI_YELLOW + "\nPress any key to continue..." + ANSI_RESET);
        this.scanner.nextLine();
    }

    private void displayRatings() {
        List<Rating> ratings = this.ratingService.getRatings("dots");

        if (ratings.isEmpty()) {
            System.out.println(ANSI_PINK + "\nTotal Ratings: " + ANSI_RESET);
            System.out.println("No ratings yet!");
            return;
        }

        System.out.println(ANSI_PINK + "\nTotal Ratings: " + ANSI_RESET);
        for (Rating rating : ratings) {
            System.out.printf("%s\n\n", rating);
        }

        System.out.println("\n" + ANSI_PINK + "Average rating: " + ANSI_RESET + this.ratingService.getAverageRating("dots") + "\n");
    }

    private void displayComments() {
        List<Comment> comments = this.commentService.getComments("dots");

        if (comments.isEmpty()) {
            System.out.println(ANSI_PINK + "\nComments: " + ANSI_RESET);
            System.out.println("No comments yet!");
            return;
        }

        System.out.println(ANSI_PINK + "\nComments: " + ANSI_RESET);
        for (Comment comment : comments) {
            System.out.printf("%s - by: %s - %s\n\n", comment.getComment(), comment.getPlayer(), comment.getCommentedAt());
        }
    }

    private void displayHallOfFame() {
        List<Score> scores = this.scoreService.getTopScores("dots");

        if (scores.isEmpty()) {
            System.out.println(ANSI_PINK + "\nHall of fame: " + ANSI_RESET);
            System.out.println("No scores yet!");
            return;
        }

        System.out.println(ANSI_PINK + "\nHall of fame: " + ANSI_RESET);
        for (int i = 0; i < scores.size(); i++) {
            if (i == 0) {
                System.out.print(ANSI_GREEN + "🥇 " + ANSI_RESET);
            } else if (i == 1) {
                System.out.print(ANSI_GREEN + "🥈 " + ANSI_RESET);
            } else if (i == 2) {
                System.out.print(ANSI_GREEN + "🥉 " + ANSI_RESET);
            } else {
                System.out.print(ANSI_GREEN + "   " + ANSI_RESET);
            }
            System.out.printf("%d. %s - %d points\n", i + 1, scores.get(i).getPlayer(), scores.get(i).getPoints());
        }
    }

    private void printScoreAndRemainingMoves() {
        System.out.println();
        System.out.println(ANSI_PINK + "Your stats: ");
        System.out.println(ANSI_BLUE + "Remaining moves: " + ANSI_RESET + this.gameBoard.getRemainingMoves());
        System.out.println(ANSI_BLUE + "Score: " + ANSI_RESET + this.gameBoard.getScore() +
                "/" + this.gameBoard.getNeededScoreToBeSolved());

        System.out.println();

        if (this.gameBoard.getExecutedRedDots() < this.gameBoard.getNeededRedDotsToBeExecuted())
            System.out.println(ANSI_RED + "Needed Red dots to be executed: " + ANSI_RESET
                    + (this.gameBoard.getNeededRedDotsToBeExecuted() - this.gameBoard.getExecutedRedDots()));

        if (this.gameBoard.getExecutedBlueDots() < this.gameBoard.getNeededBlueDotsToBeExecuted())
            System.out.println(ANSI_BLUE + "Needed Blue dots to be executed: " + ANSI_RESET
                    + (this.gameBoard.getNeededBlueDotsToBeExecuted() - this.gameBoard.getExecutedBlueDots()));

        if (this.gameBoard.getExecutedGreenDots() < this.gameBoard.getNeededGreenDotsToBeExecuted())
            System.out.println(ANSI_GREEN + "Needed Green dots to be executed: " + ANSI_RESET
                    + (this.gameBoard.getNeededGreenDotsToBeExecuted() - this.gameBoard.getExecutedGreenDots()));

        if (this.gameBoard.getExecutedYellowDots() < this.gameBoard.getNeededYellowDotsToBeExecuted())
            System.out.println(ANSI_GOLD + "Needed Yellow dots to be executed: " + ANSI_RESET
                    + (this.gameBoard.getNeededYellowDotsToBeExecuted() - this.gameBoard.getExecutedYellowDots()));
    }

    private void printGameBoardHeader() {
        System.out.println();
        System.out.print("      ");
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            System.out.print(ANSI_PINK);
            System.out.print((i + 1) + "  ");
            System.out.print(ANSI_RESET);
        }
        System.out.println();
        System.out.print("      ");
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            System.out.print(ANSI_CYAN + "═  " + ANSI_RESET);
        }
        System.out.println();
    }

    private void printGameBoardFooter() {
        System.out.print("      ");
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            System.out.print(ANSI_CYAN + "═  " + ANSI_RESET);
        }
        System.out.println();
    }

    private void printDot(int i, int j) {
        Dot dot = this.gameBoard.getDot(i, j);
        if (dot == null) {
            System.out.print(" ");
        } else {
            System.out.print(dot);
        }
    }

    private void printGameBoardBody() {
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            System.out.print(ANSI_PINK + (char) (i + 65) + " " + ANSI_RESET);
            System.out.print(ANSI_CYAN + " ║" + ANSI_RESET);
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                System.out.print("  ");
                this.printDot(i, j);
            }
            System.out.println();
        }
    }

    private void displayGameBoard() {
        this.printGameBoardHeader();
        this.printGameBoardBody();
        this.printGameBoardFooter();
    }

    private void handleInput() {
        String input = scanner.nextLine().toUpperCase();
        Matcher matcher = INPUT_PATTERN.matcher(input);

        if (matcher.matches()) {
            handleMatcher(matcher);
        }
        else if (input.length() > 3) {
            handleInputSequence(input);
        }
        else if (input.equals("X")) {
            this.gameBoard.executeDots();
        } else if (input.equals("Q")) {
            System.out.println(ANSI_GREEN + "Are you sure you want to quit? (Y/N)" + ANSI_RESET);
            String quitInput = scanner.nextLine().toUpperCase();
            if (quitInput.equals("Y")) {
                System.out.println(ANSI_GREEN + "Do you want to save the game? (Y/N)" + ANSI_RESET);
                String saveInput = scanner.nextLine().toUpperCase();
                if (saveInput.equals("Y")) {
                    this.gameBoard.save();
                }
                this.gameBoard.setGameState(GameState.ENDED);
                System.out.println(this.gameBoard.getGameState());
                System.exit(0);
            }
        } else if (input.equals("P")) {
            this.handlePowerUpMenu();
        } else if (input.equals("B")) {
            this.handlePowerUpShop();
        } else if (input.equals("C")) {
            this.askForComment(this.playerName);
        } else if (input.equals("H")) {
            this.displayHallOfFame();
        } else if (input.equals("DC")) {
            this.displayComments();
        } else if (input.equals("DR")) {
            this.displayRatings();
        } else if (input.equals("I")) {
            this.displayInstructions();
        } else if (input.equals("R")) {
            this.restartGame();
        } else {
            this.displayError("Invalid input");
            gameLoopIteration();
        }
    }

    private void restartGame() {
        System.out.println(ANSI_GREEN + "Do you want to save the game? (Y/N)" + ANSI_RESET);
        String saveInput = scanner.nextLine().toUpperCase();
        if (saveInput.equals("Y")) {
            this.gameBoard.save();
        }

        System.out.println(ANSI_GREEN + "Press R to restart the game or Q to quit" + ANSI_RESET);
        String input = scanner.nextLine().toUpperCase();

        if (input.equals("R")) {
            System.out.println(ANSI_GREEN + "Do you want to play a saved game? (Y/N)" + ANSI_RESET);
            String restartInput = this.scanner.nextLine().toUpperCase();

            if (restartInput.equals("Y")) {
                this.gameBoard = GameBoard.load();
            } else {
                this.gameBoard = this.handleLevelSelect();
            }
            this.play();
        } else if (input.equals("Q")) {
            System.out.println(ANSI_GREEN + "Are you sure you want to quit? (Y/N)" + ANSI_RESET);
            String quitInput = this.scanner.nextLine().toUpperCase();
            if (quitInput.equals("Y")) {
                this.askForComment(this.playerName);
                this.askForRating(this.playerName);
                System.exit(0);
            }
        } else {
            this.displayError("Invalid input (press I for instructions)");
            this.restartGame();
        }
    }

    private void handleInputSequence(String input) {
        String[] inputParts = input.split(" ");
        for (String inputPart : inputParts) {
            Matcher matcher = INPUT_PATTERN.matcher(inputPart);
            if (matcher.matches()) {
                handleMatcher(matcher);
            }
        }
    }

    private void handleMatcher(Matcher matcher) {
        String command = matcher.group(1);
        int row = matcher.group(2).charAt(0) - 65;
        int column = Integer.parseInt(matcher.group(3)) - 1;

        if (command.equals("S")) {
            this.gameBoard.selectDot(row, column);
        } else if (command.equals("U")) {
            this.gameBoard.unselectDot(row, column);
        }
    }

    private void printPowerUps() {
        if (this.gameBoard.getPowerUps().size() == 0) {
            return;
        }

        System.out.println();
        System.out.println(ANSI_GOLD + "Available power-ups: " + ANSI_RESET);
        for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
            System.out.println(ANSI_GREEN +
                    this.gameBoard.getPowerUps().get(i).getName() + ": " +
                    this.gameBoard.getPowerUps().get(i).getRemainingUses() + ANSI_RESET);
        }
    }

    private void displayInstructions() {
        System.out.println(
                  ANSI_GREEN + "Q => quit\n"
                + ANSI_BLUE + "SA1 => select dot A1\n"
                + ANSI_GOLD + "UA1 => unselect dot A1\n"
                + ANSI_MAGENTA + "X => execute selected dots\n"
                + ANSI_CYAN + "P => open power-up menu\n"
                + ANSI_WHITE + "B => open power-up shop\n"
                + ANSI_YELLOW + "C => leave a comment\n"
                + ANSI_RED + "H => see Hall of Fame\n"
                + ANSI_BLUE + "DC => display comments\n"
                + ANSI_GREEN + "DR => display ratings\n"
                + ANSI_GOLD + "R => restart game\n"
                + ANSI_RESET);
    }

    private void handlePowerUpMenu() {
        if (this.gameBoard.getPowerUps().size() == 0) {
            this.displayError("No power-ups available");
            this.gameLoopIteration();
        } else {
            this.displayGameBoard();
            System.out.println(ANSI_BLUE + "Enter your command: \n" +
                    ANSI_RED + "BA1 => use bomb power-up on dot A1\n" +
                    ANSI_BLUE + "E => activate ExtraMoves power-up\n" +
                    ANSI_YELLOW + "S => activate ScoreMultiplier power-up\n" + ANSI_RESET);

            String powerUpInput = this.scanner.nextLine().toUpperCase();

            if (powerUpInput.equals("E")) {
                this.handleExtraMoves();
            } else if (powerUpInput.equals("S")) {
                this.handleScoreMultiplier();
            } else if (powerUpInput.length() == 3 && powerUpInput.charAt(0) == 'B') {
                this.detonateBomb(powerUpInput);
            } else {
                this.displayError("Invalid input!");
                this.gameLoopIteration();
            }
        }
    }

    private void handleExtraMoves() {
        if (this.gameBoard.getPowerUps().size() == 0) {
            this.displayError("No power-ups available");
            this.gameLoopIteration();
        }

        for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
            if (this.gameBoard.getPowerUps().get(i).getName().equals("ExtraMoves")) {
                this.gameBoard.getPowerUps().get(i).setActiveState(true);
                //this.gameBoard.applyPowerUp(i, 0, 0);
                System.out.println(ANSI_GREEN + "Extra moves activated" + ANSI_RESET);
                return;
            }
        }

        this.displayError("You don't have any ExtraMoves power-ups");
        this.gameLoopIteration();
    }

    private void handleScoreMultiplier() {
        if (this.gameBoard.getPowerUps().size() == 0) {
            this.displayError("No power-ups available");
            this.gameLoopIteration();
        }

        for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
            if (this.gameBoard.getPowerUps().get(i).getName().equals("ScoreMultiplier")) {
                this.gameBoard.getPowerUps().get(i).setActiveState(true);
                //.gameBoard.applyPowerUp(i, 0, 0);
                System.out.println(ANSI_GREEN + "Score multiplier activated" + ANSI_RESET);
                return;
            }
        }

        this.displayError("You don't have any ScoreMultiplier power-ups");
        this.gameLoopIteration();
    }

    private void detonateBomb(String powerUpInput) {
        if (this.gameBoard.getPowerUps().size() == 0) {
            this.displayError("No power-ups available");
            this.gameLoopIteration();
        }

        int row = powerUpInput.charAt(1) - 65;
        int column = Integer.parseInt(powerUpInput.charAt(2) + "") - 1;
        for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
            if (this.gameBoard.getPowerUps().get(i).getName().equals("Bomb")) {
                this.gameBoard.getPowerUp(i).setActiveState(true);
               // this.gameBoard.applyPowerUp(i, row, column);
                System.out.println(ANSI_GREEN + "Bomb has been exploded!" + ANSI_RESET);
                return;
            }
        }

        this.displayError("You don't have any Bomb power-ups");
        this.gameLoopIteration();
    }

    private void handlePowerUpShop() {
        if (this.gameBoard.getScore() < this.gameBoard.getNeededScoreToBeSolved() / 4) {
            this.displayError("You don't have enough score to buy a power-up! You need at least " +
                    this.gameBoard.getNeededScoreToBeSolved() / 4 + " points");
            this.gameLoopIteration();
        }

        PowerUpShop powerUpShop = this.gameBoard.getPowerUpShop();

        if (powerUpShop.getAvailableBombs().size() < 1 && powerUpShop.getAvailableExtraMoves().size() < 1 &&
        powerUpShop.getAvailableScoreMultipliers().size() < 1) {
            this.displayError("There are no power-ups available in the shop!");
            this.gameLoopIteration();
        }

        this.displayGameBoard();
        System.out.println(powerUpShop);
        this.printPowerUpShopPrices();

        String powerUpShopInput = this.scanner.nextLine().toUpperCase();
        switch (powerUpShopInput) {
            case "B" -> this.handleBombBuy();
            case "E" -> this.handleExtraMovesBuy();
            case "S" -> this.handleScoreMultiplierBuy();
            default -> {
                this.displayError("Invalid input");
                this.gameLoopIteration();
            }
        }
    }

    private void handleBombBuy() {
        int bombPrice = this.gameBoard.getPowerUpShop().getBombPrice();
        if (this.gameBoard.getScore() >= bombPrice && this.gameBoard.getPowerUpShop().getAvailableBombs().size() > 0) {
            if (this.gameBoard.getPowerUps().size() == 0) {
                this.gameBoard.getPowerUps().add(this.gameBoard.getPowerUpShop().getAvailableBombs().get(0));
                this.gameBoard.getPowerUpShop().sellBomb();
                this.gameBoard.setCurrentScore(this.gameBoard.getScore() - bombPrice);
            } else {
                for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
                    if (this.gameBoard.getPowerUps().get(i).getName().equals("Bomb")) {
                        this.gameBoard.getPowerUps().get(i).setRemainingUses(this.gameBoard.getPowerUps().get(i).getRemainingUses() + 1);
                        break;
                    }
                }
            }
            System.out.println(ANSI_GREEN + "Bomb power-up bought!" + ANSI_RESET);
        } else {
            this.displayError("Not enough points");
            this.gameLoopIteration();
        }
    }

    private void handleExtraMovesBuy() {
        int extraMovesPrice = this.gameBoard.getPowerUpShop().getExtraMovesPrice();
        if (this.gameBoard.getScore() >= extraMovesPrice && this.gameBoard.getPowerUpShop().getAvailableExtraMoves().size() > 0) {
            if (this.gameBoard.getPowerUps().size() == 0) {
                this.gameBoard.getPowerUps().add(this.gameBoard.getPowerUpShop().getAvailableExtraMoves().get(0));
                this.gameBoard.getPowerUpShop().sellExtraMoves();
                this.gameBoard.setCurrentScore(this.gameBoard.getScore() - extraMovesPrice);
            } else {
                for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
                    if (this.gameBoard.getPowerUps().get(i).getName().equals("ExtraMoves")) {
                        this.gameBoard.getPowerUps().get(i).setRemainingUses(this.gameBoard.getPowerUps().get(i).getRemainingUses() + 1);
                        break;
                    }
                }
            }
            System.out.println(ANSI_GREEN + "ExtraMoves power-up bought!" + ANSI_RESET);
        } else {
            this.displayError("Not enough points");
            this.gameLoopIteration();
        }
    }

    private void handleScoreMultiplierBuy() {
        int scoreMultiplierPrice = this.gameBoard.getPowerUpShop().getScoreMultiplierPrice();
        if (this.gameBoard.getScore() >= scoreMultiplierPrice && this.gameBoard.getPowerUpShop().getAvailableScoreMultipliers().size() > 0) {
            if (this.gameBoard.getPowerUps().size() == 0) {
                this.gameBoard.getPowerUps().add(this.gameBoard.getPowerUpShop().getAvailableScoreMultipliers().get(0));
                this.gameBoard.getPowerUpShop().sellScoreMultiplier();
                this.gameBoard.setCurrentScore(this.gameBoard.getScore() - scoreMultiplierPrice);
            } else {
                for (int i = 0; i < this.gameBoard.getPowerUps().size(); i++) {
                    if (this.gameBoard.getPowerUps().get(i).getName().equals("ScoreMultiplier")) {
                        this.gameBoard.getPowerUps().get(i).setRemainingUses(this.gameBoard.getPowerUps().get(i).getRemainingUses() + 1);
                        break;
                    }
                }
            }
            System.out.println(ANSI_GREEN + "ScoreMultiplier power-up bought!" + ANSI_RESET);
        } else {
            this.displayError("Not enough points");
            this.gameLoopIteration();
        }
    }

    private void printPowerUpShopPrices() {
        System.out.println(ANSI_RED + "Bomb price: " + this.gameBoard.getPowerUpShop().getBombPrice() + ANSI_RESET + " (B to buy)");
        System.out.println(ANSI_BLUE + "ExtraMoves price: " + this.gameBoard.getPowerUpShop().getExtraMovesPrice() + ANSI_RESET + " (E to buy)");
        System.out.println(ANSI_GOLD + "ScoreMultiplier price: " + this.gameBoard.getPowerUpShop().getScoreMultiplierPrice() + ANSI_RESET + " (S to buy)");
    }

    private void gameLoopIteration() {
        this.clearScreen();
        this.displayGameBoard();
        this.printScoreAndRemainingMoves();
        this.printPowerUps();
        promptUser();
        this.handleInput();
    }

    private static void promptUser() {
        System.out.println();
        System.out.println(ANSI_RED + "Enter your command: " + ANSI_RESET + "(press I for instructions)\n");
    }

    private void displayError(String errorMessage) {
        System.out.println(ANSI_RED + "Error: " + errorMessage + ANSI_RESET);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int[] getGameBoardParameters() {
        if (this.gameBoard == null) {
            return null;
        }

        return new int[] {this.gameBoard.getRowCount(), this.gameBoard.getColumnCount(), this.gameBoard.getRemainingMoves()};
    }
}
