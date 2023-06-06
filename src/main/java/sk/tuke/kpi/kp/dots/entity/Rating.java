package sk.tuke.kpi.kp.dots.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int ident;
    private String player;
    private String game;
    private int rating;
    private Date ratedAt;

    public Rating() {
    }

    public Rating(String player, String game, int rating, Date ratedAt) {
        if (player == null || player.isEmpty()) {
            throw new IllegalArgumentException("Player cannot be null or empty");
        }

        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }

        if (ratedAt == null) {
            throw new IllegalArgumentException("Rated at cannot be null");
        }

        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedAt = ratedAt;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date ratedAt) {
        this.ratedAt = ratedAt;
    }

    public void setIdent(int id) {
        this.ident = id;
    }

    public int getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        return switch (rating) {
            case 1 -> player + " rated " + game + " with " + "⭐" + " at " + ratedAt;
            case 2 -> player + " rated " + game + " with " + "⭐⭐" + " at " + ratedAt;
            case 3 -> player + " rated " + game + " with " + "⭐⭐⭐" + " at " + ratedAt;
            case 4 -> player + " rated " + game + " with " + "⭐⭐⭐⭐" + " at " + ratedAt;
            case 5 -> player + " rated " + game + " with " + "⭐⭐⭐⭐⭐" + " at " + ratedAt;
            default -> null;
        };
    }
}
