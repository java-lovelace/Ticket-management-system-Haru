package dao;

import domain.Comment;
import java.util.List;

public interface CommentDao {
    void create(Comment comment);
    List<Comment> findByTicketId(int ticketId);
}
