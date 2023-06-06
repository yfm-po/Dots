package sk.tuke.kpi.kp.dots.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.dots.core.Dot.Dot;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;

@Controller
@RequestMapping("/dots")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DotsController {

    private GameBoard gameBoard = new GameBoard(8, 8, 16);
    private ScoreService scoreService;
    private CommentService commentService;
    private RatingService ratingService;
    private boolean isExecutingMode = false;

    public DotsController(ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    @RequestMapping
    public String dots(@RequestParam(required = false) String row, @RequestParam(required = false) String column, Model model) {
        processCommand(row, column);

        this.fillModel(model);
        return "dots";
    }

    private void processCommand(String row, String column) {
        try {
            if (row != null && column != null) {
                if (isExecutingMode) {
                    gameBoard.executeDots();
                } else if (this.gameBoard.getDot(Integer.parseInt(row), Integer.parseInt(column)).isSelected()) {
                    this.gameBoard.getDot(Integer.parseInt(row), Integer.parseInt(column)).setSelected(false);
                } else {
                    this.gameBoard.getDot(Integer.parseInt(row), Integer.parseInt(column)).setSelected(true);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/execute")
    public String toggleExecutingMode(Model model) {
        isExecutingMode = !isExecutingMode;
        this.fillModel(model);
        return "dots";
    }

    @RequestMapping("/gameboard")
    public String dots(@RequestParam(required = false) String row, @RequestParam(required = false) String column) {
        processCommand(row, column);
        return "dots-only";
    }

    @RequestMapping("/gameboard/new")
    public String newGame(Model model) {
        this.gameBoard = new GameBoard(4, 4, 4);
        this.fillModel(model);
        return "dots-only";
    }

    public String getHtmlGameBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table>\n");
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            stringBuilder.append("<tr>\n");
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                stringBuilder.append("<td>\n")
                        .append("<a href='/dots?row=" + i + "&column=" + j + "' id='dot_" + i + "_" + j + "'>\n")
                        .append("<img src='/images/dots/")
                        .append(getImageName(gameBoard.getDot(i, j)))
                        .append(".png' width='75' height='75'>\n")
                        .append("</a>\n")
                        .append("</td>\n");
            }
            stringBuilder.append("</tr>\n");
        }
        stringBuilder.append("</table>\n");

        stringBuilder.append("<p>Score: ").append(gameBoard.getScore()).append(" / " + gameBoard.getNeededScoreToBeSolved()).append("</p>\n");

        stringBuilder.append("<p>Red dots to execute: ").append(gameBoard.getNeededRedDotsToBeExecuted() - gameBoard.getExecutedRedDots()).append("</p>\n");

        stringBuilder.append("<p>Green dots to execute: ").append(gameBoard.getNeededGreenDotsToBeExecuted() - gameBoard.getExecutedGreenDots()).append("</p>\n");

        stringBuilder.append("<p>Blue dots to execute: ").append(gameBoard.getNeededBlueDotsToBeExecuted() - gameBoard.getExecutedBlueDots()).append("</p>\n");

        stringBuilder.append("<p>Yellow dots to execute: ").append(gameBoard.getNeededYellowDotsToBeExecuted() - gameBoard.getExecutedYellowDots()).append("</p>\n");
        return stringBuilder.toString();
    }

    private String getImageName(Dot dot) {
        String imageName = "";
        switch (dot.getColor()) {
            case RED -> imageName = "red";
            case GREEN -> imageName = "green";
            case BLUE -> imageName = "blue";
            case YELLOW -> imageName = "yellow";
        }

        if (dot.isSelected()) {
            imageName += "_selected";
        }
        return imageName;
    }

    private void fillModel(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("dots"));
        model.addAttribute("comments", commentService.getComments("dots"));
        model.addAttribute("rating", ratingService.getRatings("dots"));
        model.addAttribute("averageRating", ratingService.getAverageRating("dots"));
        model.addAttribute("gameBoard", getHtmlGameBoard());
    }
}
