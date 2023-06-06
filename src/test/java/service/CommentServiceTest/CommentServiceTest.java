package service.CommentServiceTest;

import org.junit.Test;
import sk.tuke.kpi.kp.dots.entity.Comment;
import sk.tuke.kpi.kp.dots.service.CommentService.CommentService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceTest {
    protected CommentService commentService;

    @Test
    public void testDbInit() throws Exception {
        assertEquals(0, commentService.getComments("dots").size());
    }

    @Test
    public void testAddComment() throws Exception {
        commentService.addComment(new Comment("Janko", "dots", "Hello", new Date()));
        assertEquals(1, commentService.getComments("dots").size());
    }

    @Test
    public void testGetComments() throws Exception {
        Comment comment1 = new Comment("Dog", "dots", "Hello", new Date());
        Comment comment2 = new Comment("Cat", "dots", "Hello", new Date());

        commentService.addComment(comment1);
        commentService.addComment(comment2);

        List<Comment> comments = commentService.getComments("dots");
        assertEquals(comment1.getComment(), comments.get(0).getComment());
        assertEquals(comment2.getPlayer(), comments.get(1).getPlayer());
    }
}
