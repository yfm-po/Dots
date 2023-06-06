package sk.tuke.kpi.kp.dots.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceRestClient;

@Configuration
public class CommentServiceRestClientConfig {
    @Bean
    public CommentService commentService(RestTemplate restTemplate) {
        return new CommentServiceRestClient(restTemplate);
    }
}
