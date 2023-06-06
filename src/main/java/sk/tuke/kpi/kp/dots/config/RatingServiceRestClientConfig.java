package sk.tuke.kpi.kp.dots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceRestClient;

@Configuration
public class RatingServiceRestClientConfig {
    @Bean
    public RatingService ratingService(RestTemplate restTemplate) {
        return new RatingServiceRestClient(restTemplate);
    }
}
