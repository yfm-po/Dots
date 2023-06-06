package sk.tuke.kpi.kp.dots.service.CommentService;

import sk.tuke.kpi.kp.dots.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getComments(String game);
    void reset();
}
