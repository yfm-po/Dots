package sk.tuke.kpi.kp.dots.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    private final ScoreService scoreService;
    public ScoreServiceRest(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * Retrieves the top scores for a specified game.
     *
     * @param game the game for which top scores should be retrieved
     * @return a list of top scores
     */
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    /**
     * Adds a new score.
     *
     * @param score the score to be added
     */
    @PostMapping
    public void addScore(@RequestBody Score score) {
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

        scoreService.addScore(score);
    }
}
