package dao;

import config.ConfigDb;
import domain.*;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {

    @Override
    public void create(Ticket ticket) {
        String sql = "INSERT INTO tickets (title, description, reporter_id, category_id, status_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConfigDb.openConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getTitle());
            ps.setString(2, ticket.getDescription());
            ps.setInt(3, ticket.getReporter().getUserId());
            ps.setInt(4, ticket.getCategory().getCategoryId());
            ps.setInt(5, ticket.getStatus().getStatusId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating ticket failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setTicketId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating ticket failed, no ID obtained.");
                }
            }
            JOptionPane.showMessageDialog(null, "Ticket creado correctamente con el id: " + ticket.getTicketId());

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Error al crear el ticket: " + error.getMessage());
        }
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE tickets SET title = ?, description = ?, assignee_id = ?, category_id = ?, status_id = ?, updated_at = CURRENT_TIMESTAMP WHERE ticket_id = ?";

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getTitle());
            pstmt.setString(2, ticket.getDescription());
            if (ticket.getAssignee() != null) {
                pstmt.setInt(3, ticket.getAssignee().getUserId());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            pstmt.setInt(4, ticket.getCategory().getCategoryId());
            pstmt.setInt(5, ticket.getStatus().getStatusId());
            pstmt.setInt(6, ticket.getTicketId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Ticket actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el ticket para actualizar.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el ticket: " + e.getMessage());
        }
    }

    @Override
    public Ticket findById(int id) {
        String sql = "SELECT t.*, c.name as category_name, s.name as status_name, " +
                "ur.full_name as reporter_name, ur.email as reporter_email, " +
                "ua.full_name as assignee_name, ua.email as assignee_email " +
                "FROM tickets t " +
                "JOIN categories c ON t.category_id = c.category_id " +
                "JOIN statuses s ON t.status_id = s.status_id " +
                "JOIN users ur ON t.reporter_id = ur.user_id " +
                "LEFT JOIN users ua ON t.assignee_id = ua.user_id " +
                "WHERE t.ticket_id = ?";
        Ticket ticket = null;

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setTitle(rs.getString("title"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setCreatedAt(rs.getTimestamp("created_at"));
                    ticket.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User reporter = new User();
                    reporter.setUserId(rs.getInt("reporter_id"));
                    reporter.setFullName(rs.getString("reporter_name"));
                    reporter.setEmail(rs.getString("reporter_email"));
                    ticket.setReporter(reporter);

                    if (rs.getObject("assignee_id") != null) {
                        User assignee = new User();
                        assignee.setUserId(rs.getInt("assignee_id"));
                        assignee.setFullName(rs.getString("assignee_name"));
                        assignee.setEmail(rs.getString("assignee_email"));
                        ticket.setAssignee(assignee);
                    }

                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    ticket.setCategory(category);

                    Status status = new Status();
                    status.setStatusId(rs.getInt("status_id"));
                    status.setName(rs.getString("status_name"));
                    ticket.setStatus(status);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el ticket: " + e.getMessage());
        }

        return ticket;
    }

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
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setTitle(rs.getString("title"));
                    ticket.setDescription(rs.getString("description"));
                    ticket.setCreatedAt(rs.getTimestamp("created_at"));
                    ticket.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User reporter = new User();
                    reporter.setUserId(rs.getInt("reporter_id"));
                    reporter.setFullName(rs.getString("reporter_name"));
                    reporter.setEmail(rs.getString("reporter_email"));
                    ticket.setReporter(reporter);

                    if (rs.getObject("assignee_id") != null) {
                        User assignee = new User();
                        assignee.setUserId(rs.getInt("assignee_id"));
                        assignee.setFullName(rs.getString("assignee_name"));
                        assignee.setEmail(rs.getString("assignee_email"));
                        ticket.setAssignee(assignee);
                    }

                    Category category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    ticket.setCategory(category);

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
