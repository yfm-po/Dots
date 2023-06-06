package service.ScoreServiceTest;

import org.junit.Before;
import org.junit.Test;
import sk.tuke.kpi.kp.dots.service.ScoreService.ScoreServiceJDBC;
import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ScoreServiceTestJDBC extends ScoreServiceTest {
    private static final String DELETE_STATEMENT = "DELETE FROM score";

    public ScoreServiceTestJDBC() {
        super.scoreService = new ScoreServiceJDBC();
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
    public void testAddScore() throws Exception {
        super.testAddScore();
    }

    @Test
    public void testGetTopScores() throws Exception {
        super.testGetTopScores();
    }
}
