package sk.tuke.kpi.kp.dots.service.RatingService;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.service.DataRest.DataRest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RatingServiceRestClient implements RatingService {

    private final String url = DataRest.getRatingsUrl();

    private final RestTemplate restTemplate;

    public RatingServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Sets a rating for a player in a game.
     *
     * @param rating the rating to be set
     */
    @Override
    public void setRating(Rating rating) {
        if (rating == null) {
            return;
        }

        if (rating.getPlayer() == null || rating.getPlayer().isEmpty()) {
            return;
        }

        if (rating.getGame() == null || rating.getGame().isEmpty()) {
            return;
        }

        if (rating.getRating() < 1 || rating.getRating() > 5) {
            return;
        }

        if (rating.getRatedAt() == null) {
            return;
        }

        restTemplate.postForEntity(url, rating, Rating.class);
    }

    /**
     * Retrieves the rating of a player in a game.
     *
     * @param game the game for which the rating should be retrieved
     * @param player the player whose rating should be retrieved
     * @return the rating value
     */
    @Override
    public int getRating(String game, String player) {
        return Objects.requireNonNull(restTemplate.getForEntity(url + '/' + player, Rating.class).getBody()).getRating();
    }

    /**
     * Retrieves the ratings for a specified game.
     *
     * @param gameName the game for which ratings should be retrieved
     * @return a list of ratings
     */
    @Override
    public List<Rating> getRatings(String gameName) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(DataRest.getRatingsWithGameUrl(), Rating[].class).getBody()));
    }

    /**
     * Retrieves the average rating for a specified game.
     *
     * @param gameName the game for which the average rating should be retrieved
     * @return the average rating value
     */
    @Override
    public int getAverageRating(String gameName) {
        Integer averageRating = restTemplate.getForEntity(DataRest.getAverageRatingUrl(), Integer.class).getBody();
        return averageRating != null ? averageRating : 0;
    }

    /**
     * Reset the ratings - Not supported via web service.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}