package sk.tuke.kpi.kp.dots.service.ScoreService;

import sk.tuke.kpi.kp.dots.entity.Score;
import sk.tuke.kpi.kp.dots.service.Exception.GameStudioException;
import sk.tuke.kpi.kp.dots.service.Exception.ScoreException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

public class ScoreServiceJDBC implements ScoreService {
    public static final String DELETE_STATEMENT = "DELETE FROM score";
    public static final String PREPARE_STATEMENT = "INSERT INTO score (player, game, points, played_at) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, points, played_at FROM score where game = ? ORDER BY points DESC LIMIT 10";

    @Override
    public void addScore(Score score) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(PREPARE_STATEMENT)
        ) {
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Score(
                            rs.getString("player"),
                            rs.getString("game"),
                            rs.getInt("points"),
                            rs.getTimestamp("played_at")
                    ));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }
}
