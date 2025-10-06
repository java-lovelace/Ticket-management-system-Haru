package dao;

import config.ConfigDb;
import domain.Comment;
import domain.Ticket;
import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    @Override
    public void create(Comment comment) {

        String sql = "INSERT INTO comments (ticket_id, user_id, content) VALUES (?, ?, ?)";
        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getTicket().getTicketId());
            pstmt.setInt(2, comment.getUser().getUserId());
            pstmt.setString(3, comment.getContent());

            pstmt.executeUpdate();

            System.out.println(" Comentario creado exitosamente.");

        } catch (SQLException e) {
            // 5. Si ocurre un error, lo imprimimos en la consola.
            System.err.println("Error al crear el comentario: " + e.getMessage());
        }
    }

    @Override
    public List<Comment> findByTicketId(int ticketId) {
        return new ArrayList<>();
    }
}
