package service.ScoreServiceTest;

import org.junit.Test;
import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreServiceTest {
    protected ScoreService scoreService;

    @Test
    public void testDbInit() throws Exception {
        assertEquals(0, scoreService.getTopScores("dots").size());
    }

    @Test
    public void testAddScore() throws Exception {
        scoreService.addScore(new Score("Janko", "dots", 100, new Date()));
        assertEquals(1, scoreService.getTopScores("dots").size());
    }

    @Test
    public void testGetTopScores() throws Exception {
        Score score1 = new Score("Dog", "dots", 100, new Date());
        Score score2 = new Score("Cat", "dots", 200, new Date());

        scoreService.addScore(score1);
        scoreService.addScore(score2);

        List<Score> topScores = scoreService.getTopScores("dots");
        assertEquals(score2.getPoints(), topScores.get(0).getPoints());
        assertEquals(score1.getPlayer(), topScores.get(1).getPlayer());
    }
}
