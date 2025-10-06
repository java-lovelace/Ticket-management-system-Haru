package controller;

import domain.User;
import service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void listAllUsers() {
        List<User> users = userService.getAllUsers();

        if (!users.isEmpty()) {
            System.out.println("\nLista de usuarios registrados:");
            for (User user : users) {
                System.out.println("------------------------------------");
                System.out.println("ID: " + user.getUserId());
                System.out.println("Nombre: " + user.getFullName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Rol: " + user.getRole().getName());
            }
            System.out.println("------------------------------------");
        }
    }
}
