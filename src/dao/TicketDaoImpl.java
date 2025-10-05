package dao;

import config.ConfigDb;
import domain.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {

    @Override
    public void create(Ticket ticket) {
    }

    @Override
    public void update(Ticket ticket) {
    }

    @Override
    public Ticket findById(int id) {
        return null;
    }

    @Override
    public List<Ticket> findByAssignee(int assigneeId) {
        List<Ticket> tickets = new ArrayList<>();

        String sql = """
        SELECT 
            t.ticket_id, t.title, t.description, t.created_at, t.updated_at,
            r.user_id AS reporter_id, r.full_name AS reporter_name,
            a.user_id AS assignee_id, a.full_name AS assignee_name,
            c.category_id, c.name AS category_name,
            s.status_id, s.name AS status_name
        FROM tickets t
        JOIN users r ON t.reporter_id = r.user_id
        LEFT JOIN users a ON t.assignee_id = a.user_id
        JOIN categories c ON t.category_id = c.category_id
        JOIN statuses s ON t.status_id = s.status_id
        WHERE t.assignee_id = ?
        ORDER BY t.created_at DESC
    """;

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assigneeId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    domain.User reporter = new domain.User();
                    reporter.setUserId(rs.getInt("reporter_id"));
                    reporter.setFullName(rs.getString("reporter_name"));

                    domain.User assignee = new domain.User();
                    assignee.setUserId(rs.getInt("assignee_id"));
                    assignee.setFullName(rs.getString("assignee_name"));

                    domain.Category category = new domain.Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));

                    domain.Status status = new domain.Status();
                    status.setStatusId(rs.getInt("status_id"));
                    status.setName(rs.getString("status_name"));

                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setTitle(rs.getString("title"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setReporter(reporter);
                    ticket.setAssignee(assignee);
                    ticket.setCategory(category);
                    ticket.setStatus(status);
                    ticket.setCreatedAt(rs.getTimestamp("created_at"));
                    ticket.setUpdatedAt(rs.getTimestamp("updated_at"));

                    tickets.add(ticket);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar tickets por asignado: " + e.getMessage());
        }

        return tickets;
    }

    @Override
    public List<Ticket> findByStatusAndCategory(String statusName, String categoryName) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> findTopCategories(int limit) {
        Map<String, Integer> topCategories = new LinkedHashMap<>();

        String sql = """
            SELECT c.name AS category_name, COUNT(t.ticket_id) AS total_tickets
            FROM categories c
            LEFT JOIN tickets t ON c.category_id = t.category_id
            GROUP BY c.category_id, c.name
            ORDER BY total_tickets DESC
            LIMIT ?
        """;

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    topCategories.put(
                            rs.getString("category_name"),
                            rs.getInt("total_tickets")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el reporte de categorías: " + e.getMessage());
        }

        return topCategories;
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>();
    }
}
