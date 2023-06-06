package sk.tuke.kpi.kp.dots.service.RatingService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.dots.entity.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Sets a rating for a game.
     *
     * @param rating the rating object containing the game and user details
     */
    @Override
    public void setRating(Rating rating) {
        entityManager.createQuery("DELETE FROM Rating r WHERE r.player =:player")
                .setParameter("player", rating.getPlayer()).executeUpdate();
        entityManager.persist(rating);
    }

    /**
     * Retrieves the rating for a specified user and game.
     *
     * @param game   the game for which the user's rating should be retrieved
     * @param player the user's name
     * @return the user's rating value
     */
    @Override
    public int getRating(String game, String player) {
        Rating rating = (Rating) entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player =:player")
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

        if (rating == null) {
            return 0;
        }
        return rating.getRating();
    }

    /**
     * Retrieves the ratings for a specified game.
     *
     * @param game the game for which ratings should be retrieved
     * @return a list of ratings
     */
    @Override
    public List<Rating> getRatings(String game) {
        return entityManager.createQuery("select r from Rating r where r.game = :game order by r.ratedAt desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * Retrieves the average rating for a specified game.
     *
     * @param game the game for which the average rating should be coming from the database
     * @return the average rating value
     */
    @Override
    public int getAverageRating(String game) {
        BigDecimal result = (BigDecimal) entityManager.createNativeQuery("select avg(rating) from rating where game = :game")
                .setParameter("game", game)
                .getSingleResult();

        if (result == null || result.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }

        BigDecimal roundedResult = result.setScale(0, RoundingMode.HALF_UP);
        return roundedResult.intValue();
    }

    /**
     * Resets the ratings by deleting all records.
     */
    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
