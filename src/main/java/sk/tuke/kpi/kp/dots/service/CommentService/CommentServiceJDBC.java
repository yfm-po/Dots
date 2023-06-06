package sk.tuke.kpi.kp.dots.service.CommentService;

import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.service.Exception.CommentException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

public class CommentServiceJDBC implements CommentService {

    public static final String PREPARE_STATEMENT = "INSERT INTO comment (player, game, comment, commented_at) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, comment, commented_at FROM comment where game = ? ORDER BY commented_at DESC";
    public static final String DELETE_STATEMENT = "DELETE FROM comment";

    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(PREPARE_STATEMENT)
        ) {
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    comments.add(new Comment(
                            rs.getString("player"),
                            rs.getString("game"),
                            rs.getString("comment"),
                            rs.getTimestamp("commented_at")
                    ));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new CommentException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new CommentException(e);
        }
    }
}
