package service.RatingServiceTest;

import org.junit.Before;
import org.junit.Test;
import sk.tuke.kpi.kp.dots.service.RatingService.RatingServiceJDBC;
import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RatingServiceTestJDBC extends RatingServiceTest {
    private static final String DELETE_STATEMENT = "DELETE FROM rating";

    public RatingServiceTestJDBC() {
        super.ratingService = new RatingServiceJDBC();
    }

    @Before
    public void setUp() throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        Statement statement = connection.createStatement();
        statement.execute(DELETE_STATEMENT);
    }

    @Test
    public void testDbInit() throws Exception {
        super.testDbInit();
    }

    @Test
    public void testAddRating() throws Exception {
        super.testAddRating();
    }

    @Test
    public void testGetAverageRating() throws Exception {
        super.testGetAverageRating();
    }

    @Test
    public void testGetRatings() throws Exception {
        super.testGetRatings();
    }
}
