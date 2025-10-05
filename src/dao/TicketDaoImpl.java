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
        return new ArrayList<>();
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
