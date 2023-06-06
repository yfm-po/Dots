package sk.tuke.kpi.kp.dots.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceJPA;

@Configuration
public class ScoreServiceConfig {
    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
}
