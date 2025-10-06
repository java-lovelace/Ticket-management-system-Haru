package view;

import domain.User;
import service.UserService;

import java.util.Scanner;

public class UserRegisterView {

    private final UserService userService;
    private final Scanner scanner;

    // Constructor que recibe el servicio
    public UserRegisterView(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showRegisterForm() {
        System.out.println(" Registro de Usuario ");

        System.out.print("Ingrese nombre completo: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Ingrese correo electronico: ");
        String email = scanner.nextLine().trim();

        System.out.print("Ingrese ID de rol: ");
        int roleId = Integer.parseInt(scanner.nextLine().trim());

        try {
            User user = userService.registerUser(fullName, email, roleId);
            if (user != null) {
                System.out.println("Usuario registrado con exito:");
                System.out.println(user);
            } else {
                System.out.println("No se pudo registrar el usuario. Verifique los datos.");
            }
        } catch (Exception e) {
            System.out.println("Error durante el registro: " + e.getMessage());
        }
    }
}
