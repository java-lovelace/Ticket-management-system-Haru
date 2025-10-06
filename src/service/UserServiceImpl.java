package service;

import dao.RoleDao;
import dao.UserDao;
import domain.Role;
import domain.User;

import javax.swing.JOptionPane;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User registerUser(String fullName, String email, int roleId) {
        if (fullName == null || fullName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre completo no puede estar vacío.", "Dato no Válido", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (email == null || !email.contains("@")) {
            JOptionPane.showMessageDialog(null, "El correo electrónico no es válido.", "Dato no Válido", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Role role = roleDao.findById(roleId);
        if (role == null) {
            JOptionPane.showMessageDialog(null, "El rol con ID " + roleId + " no existe.", "Dato no Válido", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim());
        user.setRole(role);

        return userDao.create(user);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
