package sk.tuke.kpi.kp.dots.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    private final RatingService ratingService;
    public RatingServiceRest(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Retrieves the ratings for a specified game.
     *
     * @param game the game for which ratings should be retrieved
     * @return a list of ratings
     */
    @GetMapping("/{game}")
    public List<Rating> getRatings(@PathVariable String game) {
        return ratingService.getRatings(game);
    }

    /**
     * Retrieves the average rating for a specified game.
     *
     * @param game the game for which the average rating should be coming from the database
     * @return the average rating value
     */
    @GetMapping("/average/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    /**
     * Sets a rating for a game.
     *
     * @param rating the rating object containing the game and user details
     */
    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        if (rating == null) {
            return;
        }

        if (rating.getGame() == null || rating.getGame().isEmpty()) {
            return;
        }

        if (rating.getPlayer() == null || rating.getPlayer().isEmpty()) {
            return;
        }

        if (rating.getRating() < 0) {
            return;
        }

        if (rating.getRatedAt() == null) {
            return;
        }

        ratingService.setRating(rating);
    }

    /**
     * Retrieves the rating for a specified user and game.
     *
     * @param name the user's name
     * @param game the game for which the user's rating should be retrieved
     * @return the user's rating value
     */
    @GetMapping("/{game}/{name}")
    public int getRating(@PathVariable String name, @PathVariable String game) {
        return ratingService.getRating(game, name);
    }
}
