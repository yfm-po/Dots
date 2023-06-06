package sk.tuke.kpi.kp.dots.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int ident;
    private String player;
    private String game;
    private String comment;
    private Date commentedAt;

    public Comment() {
    }

    public Comment(String player, String game, String comment, Date commentedAt) {
        if (player == null || player.isEmpty()) {
            throw new IllegalArgumentException("Player cannot be null or empty");
        }

        if (game == null || game.isEmpty()) {
            throw new IllegalArgumentException("Game cannot be null or empty");
        }

        if (comment == null || comment.isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be null or empty");
        }

        if (commentedAt == null) {
            throw new IllegalArgumentException("Commented at cannot be null");
        }

        this.player = player;
        this.game = game;
        this.comment = comment;
        this.commentedAt = commentedAt;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }

    public void setIdent(int id) {
        this.ident = id;
    }

    public int getIdent() {
        return ident;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "player='" + player + '\'' +
            ", game='" + game + '\'' +
            ", comment='" + comment + '\'' +
            ", commentedAt=" + commentedAt +
            '}';
    }
}
