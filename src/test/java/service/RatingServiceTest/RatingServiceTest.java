package service.RatingServiceTest;

import org.junit.Test;
import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingService;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RatingServiceTest {
    protected RatingService ratingService;

    @Test
    public void testDbInit() throws Exception {
        assertEquals(0, ratingService.getRatings("dots").size());
    }

    @Test
    public void testAddRating() throws Exception {
        ratingService.setRating(new Rating("Janko", "dots", 1, new Date()));
        assertEquals(1, ratingService.getRatings("dots").size());
    }

    @Test
    public void testGetRatings() throws Exception {
        Rating rating1 = new Rating("Dog", "dots", 1, new Date());
        Rating rating2 = new Rating("Cat", "dots", 1, new Date());

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);

        assertEquals(rating1.getRating(), ratingService.getRatings("dots").get(0).getRating());
        assertEquals(rating2.getPlayer(), ratingService.getRatings("dots").get(1).getPlayer());
    }

    @Test
    public void testGetAverageRating() throws Exception {
        Rating rating1 = new Rating("Dog", "dots", 1, new Date());
        Rating rating2 = new Rating("Cat", "dots", 3, new Date());

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);

        assertEquals(2, ratingService.getAverageRating("dots"), 0.001);
    }
}
