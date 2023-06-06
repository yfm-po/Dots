package sk.tuke.kpi.kp.dots.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceJPA;

@Configuration
public class CommentServiceConfig {
    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
}
