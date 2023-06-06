package service.RatingServiceTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceJPA;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class RatingServiceTestJPA {

    private RatingServiceJPA ratingService;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        ratingService = new RatingServiceJPA();
        ratingService.entityManager = entityManager;
    }

    @Test
    void setRating() {
        Query query = mock(Query.class);
        when(entityManager.createQuery("DELETE FROM Rating r WHERE r.player =:player")).thenReturn(query);
        when(query.setParameter("player", "player")).thenReturn(query);

        Rating rating = new Rating("player", "game", 5, new Date());
        ratingService.setRating(rating);

        verify(entityManager, times(1)).createQuery("DELETE FROM Rating r WHERE r.player =:player");
        verify(entityManager, times(1)).persist(rating);
    }

    @Test
    void getRating() {
        Query query = mock(Query.class);
        when(entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player =:player")).thenReturn(query);
        when(query.setParameter("game", "game")).thenReturn(query);
        when(query.setParameter("player", "player")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Rating());

        ratingService.getRating("game", "player");

        verify(entityManager, times(1)).createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player =:player");
    }

    @Test
    void getRatings() {
        Query query = mock(Query.class);
        when(entityManager.createQuery("select r from Rating r where r.game = :game order by r.ratedAt desc")).thenReturn(query);
        when(query.setParameter("game", "game")).thenReturn(query);
        when(query.setMaxResults(10)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Rating()));

        ratingService.getRatings("game");

        verify(entityManager, times(1)).createQuery("select r from Rating r where r.game = :game order by r.ratedAt desc");
        verify(query, times(1)).setParameter("game", "game");
        verify(query, times(1)).setMaxResults(10);
        verify(query, times(1)).getResultList();
    }

    @Test
    void reset() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery("DELETE FROM rating")).thenReturn(query);

        ratingService.reset();

        verify(entityManager, times(1)).createNativeQuery("DELETE FROM rating");
        verify(query, times(1)).executeUpdate();
    }
}
