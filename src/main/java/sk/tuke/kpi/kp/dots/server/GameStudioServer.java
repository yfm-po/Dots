package sk.tuke.kpi.kp.dots.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sk.tuke.kpi.kp.dots.server.config.CommentServiceConfig;
import sk.tuke.kpi.kp.dots.server.config.RatingServiceConfig;
import sk.tuke.kpi.kp.dots.server.config.ScoreServiceConfig;
import sk.tuke.kpi.kp.dots.server.webservice.DotsRestService;

@SpringBootApplication
@EntityScan({"sk.tuke.kpi.kp.dots.entity", "sk.tuke.kpi.kp.dots.core.gameboard"})
@EnableTransactionManagement
@Import({ScoreServiceConfig.class, CommentServiceConfig.class, RatingServiceConfig.class, DotsRestService.class})
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }
}
