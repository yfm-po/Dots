package sk.tuke.kpi.kp.dots.service.RatingService;

import sk.tuke.kpi.kp.dots.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating);
    int getRating(String game, String player);
    List<Rating> getRatings(String game);
    int getAverageRating(String game);
    void reset();
}
