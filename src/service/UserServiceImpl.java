package service;

import dao.RoleDao;
import dao.UserDao;
import domain.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    // Inyección de dependencias vía constructor
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
        return List.of();
    }
}
