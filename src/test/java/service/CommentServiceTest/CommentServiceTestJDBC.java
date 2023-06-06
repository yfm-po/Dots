package service.CommentServiceTest;

import org.junit.Before;
import org.junit.Test;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

public class CommentServiceTestJDBC extends CommentServiceTest {
    private static final String DELETE_STATEMENT = "DELETE FROM comment";

    public CommentServiceTestJDBC() {
        super.commentService = new CommentServiceJDBC();
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
    public void testAddComment() throws Exception {
        super.testAddComment();
    }

    @Test
    public void testGetComments() throws Exception {
        super.testGetComments();
    }
}
