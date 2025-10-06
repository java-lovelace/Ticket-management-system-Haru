package dao;

import config.ConfigDb;
import domain.Role;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final RoleDao roleDao = new RoleDaoImpl();

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (full_name, email, role_id) VALUES (?, ?, ?)";

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Role role = roleDao.findByName(user.getRole().getName());
            if (role == null) {
                // Asignar rol por defecto si no se encuentra (ID 1 = Cliente)
                role = roleDao.findById(1);
            }
            user.setRole(role);

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getRole().getRoleId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                System.out.println("Usuario registrado correctamente: " + user);
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT u.user_id, u.full_name, u.email, " +
                "r.role_id, r.name AS role_name, r.description AS role_description " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.role_id " +
                "WHERE u.user_id = ?";
        User user = null;

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");

                    int roleId = rs.getInt("role_id");
                    String roleName = rs.getString("role_name");
                    String roleDesc = rs.getString("role_description");

                    Role role = new Role(roleId, roleName, roleDesc);
                    user = new User(userId, fullName, email, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por id: " + e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, " +
                "r.role_id, r.name AS role_name, r.description AS role_description " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.role_id " +
                "ORDER BY u.user_id";

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");

                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                String roleDesc = rs.getString("role_description");

                Role role = new Role(roleId, roleName, roleDesc);
                User user = new User(userId, fullName, email, role);
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }

        return users;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT u.user_id, u.full_name, u.email, " +
                "r.role_id, r.name AS role_name, r.description AS role_description " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.role_id " +
                "WHERE u.email = ?";
        User user = null;

        try (Connection conn = ConfigDb.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String fullName = rs.getString("full_name");
                    String userEmail = rs.getString("email");

                    int roleId = rs.getInt("role_id");
                    String roleName = rs.getString("role_name");
                    String roleDesc = rs.getString("role_description");

                    Role role = new Role(roleId, roleName, roleDesc);
                    user = new User(userId, fullName, userEmail, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por email: " + e.getMessage());
        }
        return user;
    }
}
