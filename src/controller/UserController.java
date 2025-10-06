package controller;

import domain.User;
import service.UserService;

import javax.swing.JOptionPane;
import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void listAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados.", "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder userList = new StringBuilder("Lista de usuarios registrados:\n\n");
        for (User user : users) {
            userList.append("------------------------------------\n");
            userList.append("ID: ").append(user.getUserId()).append("\n");
            userList.append("Nombre: ".concat(user.getFullName())).append("\n");
            userList.append("Email: ").append(user.getEmail()).append("\n");
            userList.append("Rol: ").append(user.getRole().getName()).append("\n");
        }
        userList.append("------------------------------------\n");

        JOptionPane.showMessageDialog(null, userList.toString(), "Lista de Usuarios", JOptionPane.INFORMATION_MESSAGE);
    }
}
