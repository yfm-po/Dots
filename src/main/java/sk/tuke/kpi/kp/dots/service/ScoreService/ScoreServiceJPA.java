package sk.tuke.kpi.kp.dots.service.ScoreService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.dots.entity.Score;

import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Adds a new score.
     *
     * @param score the score to be added
     */
    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    /**
     * Retrieves the top scores for a specified game.
     *
     * @param game the game for which top scores should be retrieved
     * @return a list of top scores
     */
    @Override
    public List<Score> getTopScores(String game) {
        return entityManager.createQuery("select s from Score s where s.game = :game order by s.points desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * Resets the scores by deleting all records.
     */
    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM score").executeUpdate();
    }
}
