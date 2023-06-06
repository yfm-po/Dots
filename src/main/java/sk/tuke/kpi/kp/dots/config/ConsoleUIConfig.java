package sk.tuke.kpi.kp.dots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.dots.ConsoleUI.ConsoleUI;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;
import sk.tuke.kpi.kp.dots.service.GameBoardService.GameBoardService;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;

@Configuration
public class ConsoleUIConfig {
    @Bean
    public ConsoleUI ui(ScoreService scoreService, CommentService commentService, RatingService ratingService, GameBoardService gameBoardService) {
        return new ConsoleUI(scoreService, commentService, ratingService);
    }
}
