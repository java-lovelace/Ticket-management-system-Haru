package service;

import dao.RoleDao;
import dao.UserDao;
import domain.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    // Inyeccion de dependencias via constructor
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User registerUser(String fullName, String email, int roleId) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.findAll();

        if (users.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
        } else {
            System.out.println("Usuarios obtenidos correctamente.");
        }

        return users;
    }
}
