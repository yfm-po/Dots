package sk.tuke.kpi.kp.dots.service.ScoreService;

import sk.tuke.kpi.kp.dots.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<Score> getTopScores(String game);
    void reset();
}
