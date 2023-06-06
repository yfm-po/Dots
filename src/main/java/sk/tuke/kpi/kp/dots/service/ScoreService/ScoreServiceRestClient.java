package sk.tuke.kpi.kp.dots.service.ScoreService;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.DataRest.DataRest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScoreServiceRestClient implements ScoreService {
    private final String url = DataRest.getScoresUrl();

    private final RestTemplate restTemplate;

    public ScoreServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Adds a new score.
     *
     * @param score the score to be added
     */
    @Override
    public void addScore(Score score) {
        if (score == null) {
            return;
        }

        if (score.getPlayer() == null || score.getPlayer().isEmpty()) {
            return;
        }

        if (score.getGame() == null || score.getGame().isEmpty()) {
            return;
        }

        if (score.getPoints() < 0) {
            return;
        }

        if (score.getPlayedAt() == null) {
            return;
        }

        restTemplate.postForEntity(url, score, Score.class);
    }

    /**
     * Retrieves the top scores for a specified game.
     *
     * @param gameName the game for which top scores should be retrieved
     * @return a list of top scores
     */
    @Override
    public List<Score> getTopScores(String gameName) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(DataRest.getScoresWithGameUrl(), Score[].class).getBody()));
    }

    /**
     * Reset the scores - Not supported via web service.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
