package sk.tuke.kpi.kp.dots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceRestClient;

@Configuration
public class ScoreServiceRestClientConfig {
    @Bean
    public ScoreService scoreService(RestTemplate restTemplate) {
        return new ScoreServiceRestClient(restTemplate);
    }
}
