package app;

import controller.UserController;
import dao.*;
import service.*;
import view.UserRegisterView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // DAOs
        UserDao userDao = new UserDaoImpl();
        RoleDao roleDao = new RoleDaoImpl();

        // Servicios
        UserService userService = new UserServiceImpl(userDao, roleDao);

        // Controladores y vistas
        UserController userController = new UserController(userService);
        UserRegisterView userRegisterView = new UserRegisterView(userService);

        int option;
        do {
            System.out.println("\n Sistema de Tickets ");
            System.out.println("Registrar usuario");
            System.out.println("Listar usuarios");
            System.out.println("Salir");
            System.out.print("Selecciona una opcion: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    userRegisterView.showRegisterForm();
                    break;
                case 2:
                    userController.listAllUsers();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (option != 0);
    }
}
