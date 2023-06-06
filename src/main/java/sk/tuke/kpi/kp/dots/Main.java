package sk.tuke.kpi.kp.dots;

import sk.tuke.kpi.kp.dots.ConsoleUI.ConsoleUI;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceJDBC;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceJDBC;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceJDBC;

public class Main {
    public static void main(String[] args) {
        new ConsoleUI(new ScoreServiceJDBC(), new CommentServiceJDBC(), new RatingServiceJDBC()).play();
    }
}