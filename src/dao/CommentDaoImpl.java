package dao;

import domain.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    @Override
    public void create(Comment comment) {
    }

    @Override
    public List<Comment> findByTicketId(int ticketId) {
        return new ArrayList<>();
    }
}
