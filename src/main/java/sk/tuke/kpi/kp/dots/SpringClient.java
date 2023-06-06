package sk.tuke.kpi.kp.dots;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.*;
import sk.tuke.kpi.kp.dots.config.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.kpi.kp.dots.server.*"))
@Import({
        ScoreServiceRestClientConfig.class,
        CommentServiceRestClientConfig.class,
        RatingServiceRestClientConfig.class,
        ConsoleUIConfig.class,
        RestTemplateConfig.class
})
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
}
