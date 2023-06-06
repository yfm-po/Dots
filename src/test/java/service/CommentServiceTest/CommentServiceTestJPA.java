package service.CommentServiceTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentServiceJPA;

import java.util.List;

import static org.mockito.Mockito.*;

public class CommentServiceTestJPA {

    private CommentServiceJPA commentService;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        commentService = new CommentServiceJPA();
        commentService.entityManager = entityManager;
    }

    @Test
    void addComment() {
        Comment comment = new Comment();
        commentService.addComment(comment);

        verify(entityManager, times(1)).persist(comment);
    }

    @Test
    void getComments() {
        Query query = mock(Query.class);
        when(entityManager.createQuery("select c from Comment c where c.game = :game order by c.commentedAt desc")).thenReturn(query);
        when(query.setParameter("game", "game")).thenReturn(query);
        when(query.setMaxResults(10)).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Comment()));

        commentService.getComments("game");

        verify(entityManager, times(1)).createQuery("select c from Comment c where c.game = :game order by c.commentedAt desc");
        verify(query, times(1)).setParameter("game", "game");
        verify(query, times(1)).setMaxResults(10);
        verify(query, times(1)).getResultList();
    }

    @Test
    void reset() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery("DELETE FROM comment")).thenReturn(query);

        commentService.reset();

        verify(entityManager, times(1)).createNativeQuery("DELETE FROM comment");
        verify(query, times(1)).executeUpdate();
    }
}
