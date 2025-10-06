package dao;

import domain.Role;
import config.ConfigDb;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    @Override
    public Role findById(int id) {
        Role role = null;
        String sql = "SELECT * FROM roles WHERE role_id = ?";
        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    role.setRoleId(rs.getInt("role_id"));
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el rol: " + e.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
        }
        return role;
    }

    @Override
    public Role findByName(String name) {
        Role role = null;
        String sql = "SELECT * FROM roles WHERE name = ?";
        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = new Role();
                    role.setRoleId(rs.getInt("role_id"));
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el rol por nombre: " + e.getMessage(), "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                roles.add(role);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar los roles: " + e.getMessage(), "Error de Listado", JOptionPane.ERROR_MESSAGE);
        }
        return roles;
    }
}
