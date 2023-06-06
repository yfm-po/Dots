package sk.tuke.kpi.kp.dots.service.CommentService;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.service.DataRest.DataRest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommentServiceRestClient implements CommentService {

    private final String url = DataRest.getCommentsUrl();

    private final RestTemplate restTemplate;

    public CommentServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Adds a new comment.
     *
     * @param comment the comment to be added
     */
    @Override
    public void addComment(Comment comment) {
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

        restTemplate.postForEntity(url, comment, Comment.class);
    }

    /**
     * Retrieves the comments for a specified game.
     *
     * @param game the game for which comments should be retrieved
     * @return a list of comments
     */
    @Override
    public List<Comment> getComments(String game) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(DataRest.getCommentsWithGameUrl(), Comment[].class).getBody()));
    }

    /**
     * Reset the comments - Not supported via web service.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
