package sk.tuke.kpi.kp.dots.service.CommentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.dots.entity.Comment;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {
    @PersistenceContext
    public EntityManager entityManager;

    /**
     * Adds a new comment.
     *
     * @param comment the comment to be added
     */
    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    /**
     * Retrieves the comments for a specified game.
     *
     * @param game the game for which comments should be retrieved
     * @return a list of comments
     */
    @Override
    public List<Comment> getComments(String game) {
        return entityManager.createQuery("select c from Comment c where c.game = :game order by c.commentedAt desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * Resets the comments by deleting all records.
     */
    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
