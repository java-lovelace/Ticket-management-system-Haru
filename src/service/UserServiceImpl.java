package service;

import dao.RoleDao;
import dao.UserDao;
import domain.Role;
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
    public User registerUser(String fullName, String email, int RoleId) {
        // Validaciones básicas
        if (fullName == null || fullName.trim().isEmpty()) {
            System.out.println("El nombre completo no puede estar vacio.");
            return null;
        }

        if (email == null || !email.contains("@")) {
            System.out.println("El correo electrónico no es valido.");
            return null;
        }

        // Buscar el rol
        Role role = roleDao.findById(RoleId);
        if (role == null) {
            System.out.println("Rol no encontrado con ID: " + RoleId);
            return null;
        }

        // Crear el objeto usuario
        User user = new User();
        user.setFullName(fullName.trim());
        user.setEmail(email.trim());
        user.setRole(role);

        // Guardar en la base de datos
        User createdUser = userDao.create(user);
        if (createdUser != null && createdUser.getUserId() > 0) {
            System.out.println("Usuario registrado correctamente.");
            return createdUser;
        } else {
            System.out.println("Error al registrar el usuario.");
            return null;
        }
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
