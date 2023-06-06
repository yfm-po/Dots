package service.ScoreServiceTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceJPA;

import java.util.List;

import static org.mockito.Mockito.*;

public class ScoreServiceTestJPA {

    private ScoreServiceJPA scoreService;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        scoreService = new ScoreServiceJPA();
        scoreService.entityManager = entityManager;
    }

    @Test
    void addScore() {
        Score score = new Score();
        scoreService.addScore(score);

        verify(entityManager, times(1)).persist(score);
    }

    @Test
    void getTopScores() {
        Query query = mock(Query.class);
        when(entityManager.createQuery("select s from Score s where s.game = :game order by s.points desc")).thenReturn(query);
        when(query.setParameter("game", "game")).thenReturn(query);
        when(query.setMaxResults(10)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Score()));

        scoreService.getTopScores("game");

        verify(entityManager, times(1)).createQuery("select s from Score s where s.game = :game order by s.points desc");
        verify(query, times(1)).setParameter("game", "game");
        verify(query, times(1)).setMaxResults(10);
        verify(query, times(1)).getResultList();
    }

    @Test
    void reset() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery("DELETE FROM score")).thenReturn(query);

        scoreService.reset();

        verify(entityManager, times(1)).createNativeQuery("DELETE FROM score");
        verify(query, times(1)).executeUpdate();
    }
}
