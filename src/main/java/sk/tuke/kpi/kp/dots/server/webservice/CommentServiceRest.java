package sk.tuke.kpi.kp.dots.server.webservice;

import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentServiceRest {

    private final CommentService commentService;
    public CommentServiceRest(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves the comments for a specified game.
     *
     * @param game the game for which comments should be retrieved
     * @return a list of comments
     */
    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    /**
     * Adds a new comment.
     *
     * @param comment the comment to be added
     */
    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        if (comment == null) {
            return;
        }

        if (comment.getPlayer() == null || comment.getPlayer().isEmpty()) {
            return;
        }

        if (comment.getGame() == null || comment.getGame().isEmpty()) {
            return;
        }

        if (comment.getComment() == null || comment.getComment().isEmpty()) {
            return;
        }

        if (comment.getCommentedAt() == null) {
            return;
        }

        commentService.addComment(comment);
    }
}
