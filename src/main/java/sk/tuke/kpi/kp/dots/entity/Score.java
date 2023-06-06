package sk.tuke.kpi.kp.dots.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue
    private int ident;
    private String player;
    private String game;
    private int points;
    private Date playedAt;

    public Score() {
    }

    public Score(String player, String game, int points, Date playedAt) {
        if (player == null || player.isEmpty()) {
            throw new IllegalArgumentException("Player cannot be null or empty");
        }

        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }

        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }

        if (playedAt == null) {
            throw new IllegalArgumentException("Played at cannot be null");
        }

        this.player = player;
        this.game = game;
        this.points = points;
        this.playedAt = playedAt;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "ident=" + ident +
                ", game='" + game + '\n' +
        ", player='" + player + '\n' +
        ", points='" + points +
                ", playedAt=" + playedAt +
                '}';
    }

    public void setIdent(int id) {
        this.ident = id;
    }

    public int getIdent() {
        return ident;
    }
}
