package sk.tuke.kpi.kp.dots.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceJPA;

@Configuration
public class RatingServiceConfig {
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
