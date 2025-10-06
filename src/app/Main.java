package app;

import controller.UserController;
import dao.*;
import service.*;
import view.UserRegisterView;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        // DAOs
        UserDao userDao = new UserDaoImpl();
        RoleDao roleDao = new RoleDaoImpl();

        // Servicios
        UserService userService = new UserServiceImpl(userDao, roleDao);

        // Controladores y vistas
        UserController userController = new UserController(userService);
        UserRegisterView userRegisterView = new UserRegisterView(userService);

        String option;
        do {
            option = JOptionPane.showInputDialog(
                null,
                "Sistema de Tickets\n" +
                "1. Registrar usuario\n" +
                "2. Listar usuarios\n" +
                "0. Salir\n\n" +
                "Selecciona una opción:",
                "Menú Principal",
                JOptionPane.PLAIN_MESSAGE
            );

            if (option == null) {
                option = "0"; // Salir si el usuario cierra el diálogo
            }

            switch (option) {
                case "1":
                    userRegisterView.showRegisterForm();
                    break;
                case "2":
                    userController.listAllUsers();
                    break;
                case "0":
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!option.equals("0"));
    }
}
