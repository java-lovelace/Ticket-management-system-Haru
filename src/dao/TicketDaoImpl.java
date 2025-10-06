package dao;

import config.ConfigDb;
import domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {


    @Override
    public List<Ticket> findByStatusAndCategory(String statusName, String categoryName) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.*, c.name as category_name, s.name as status_name, " +
                     "ur.full_name as reporter_name, ur.email as reporter_email, " +
                     "ua.full_name as assignee_name, ua.email as assignee_email " +
                     "FROM tickets t " +
                     "JOIN categories c ON t.category_id = c.category_id " +
                     "JOIN statuses s ON t.status_id = s.status_id " +
                     "JOIN users ur ON t.reporter_id = ur.user_id " +
                     "LEFT JOIN users ua ON t.assignee_id = ua.user_id " +
                     "WHERE s.name = ? AND c.name = ?";

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, statusName);
            pstmt.setString(2, categoryName);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Por cada fila, construimos el objeto Ticket completo
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setTitle(rs.getString("title"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setCreatedAt(rs.getTimestamp("created_at"));
                    ticket.setUpdatedAt(rs.getTimestamp("updated_at"));

                    // Construimos el objeto Reporter
                    User reporter = new User();
                    reporter.setUserId(rs.getInt("reporter_id"));
                    reporter.setFullName(rs.getString("reporter_name"));
                    reporter.setEmail(rs.getString("reporter_email"));
                    ticket.setReporter(reporter);

                    // Construimos el objeto Assignee
                    if (rs.getObject("assignee_id") != null) {
                        User assignee = new User();
                        assignee.setUserId(rs.getInt("assignee_id"));
                        assignee.setFullName(rs.getString("assignee_name"));
                        assignee.setEmail(rs.getString("assignee_email"));
                        ticket.setAssignee(assignee);
                    }

                    // Construimos el objeto Category
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    ticket.setCategory(category);

                    // Construimos el objeto Status
                    Status status = new Status();
                    status.setStatusId(rs.getInt("status_id"));
                    status.setName(rs.getString("status_name"));
                    ticket.setStatus(status);

                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            System.err.println(" Error al buscar tickets: " + e.getMessage());
        }

        return tickets;
    }

    //Metodos restantes de la interfaz (aún sin implementar)
    @Override
    public void create(Ticket ticket) {}

    @Override
    public void update(Ticket ticket) {}

    @Override
    public Ticket findById(int id) {
        return null;
    }

    @Override
    public List<Ticket> findByAssignee(int assigneeId) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> findTopCategories(int limit) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>();
    }
}
