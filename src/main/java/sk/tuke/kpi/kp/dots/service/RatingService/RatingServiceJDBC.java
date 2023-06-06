package sk.tuke.kpi.kp.dots.service.RatingService;

import sk.tuke.kpi.kp.dots.entity.Rating;
import sk.tuke.kpi.kp.dots.service.Exception.GameStudioException;
import sk.tuke.kpi.kp.dots.service.Exception.RatingException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sk.tuke.kpi.kp.dots.service.DataJDBC.DataJDBC.*;

public class RatingServiceJDBC implements RatingService {
    public static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, rated_at) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, rating, rated_at FROM rating where game = ? ORDER BY rated_at DESC";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String DELETE_RATING = "DELETE FROM rating WHERE player = ? AND game = ?;";
    public static final String AVG_STATEMENT = "SELECT AVG(rating) FROM rating WHERE game = ?";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(DELETE_RATING)) {
                ps.setString(1, rating.getPlayer());
                ps.setString(2, rating.getGame());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT)) {
                ps.setString(1, rating.getPlayer());
                ps.setString(2, rating.getGame());
                ps.setInt(3, rating.getRating());
                ps.setDate(4, new Date(rating.getRatedAt().getTime()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT rating FROM rating WHERE game = ? AND player = ?")) {
                statement.setString(1, game);
                statement.setString(2, player);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("rating");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
        return 0;
    }

    @Override
    public List<Rating> getRatings(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_STATEMENT)) {
                statement.setString(1, game);
                try (ResultSet rs = statement.executeQuery()) {
                    List<Rating> ratings = new ArrayList<>();
                    while (rs.next()) {
                        ratings.add(new Rating(
                                rs.getString("player"),
                                rs.getString("game"),
                                rs.getInt("rating"),
                                rs.getTimestamp("rated_at")
                        ));
                    }
                    return ratings;
                }
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement statement = connection.prepareStatement(AVG_STATEMENT)) {
                statement.setString(1, game);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
        return 0;
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(DELETE_STATEMENT);
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
    }
}
