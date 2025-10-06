package view;

import domain.User;
import service.UserService;

import javax.swing.JOptionPane;

public class UserRegisterView {

    private final UserService userService;

    public UserRegisterView(UserService userService) {
        this.userService = userService;
    }

    public void showRegisterForm() {
        String fullName = JOptionPane.showInputDialog(null, "Ingrese nombre completo:", "Registro de Usuario", JOptionPane.PLAIN_MESSAGE);
        if (fullName == null || fullName.trim().isEmpty()) {
            return; // El usuario canceló o no ingresó nada
        }

        String email = JOptionPane.showInputDialog(null, "Ingrese correo electrónico:", "Registro de Usuario", JOptionPane.PLAIN_MESSAGE);
        if (email == null || email.trim().isEmpty()) {
            return; // El usuario canceló o no ingresó nada
        }

        String roleIdStr = JOptionPane.showInputDialog(null, "Ingrese ID de rol (1: Reporter , 2: Operator, 3: Coordinator):", "Registro de Usuario", JOptionPane.PLAIN_MESSAGE);
        if (roleIdStr == null || roleIdStr.trim().isEmpty()) {
            return; // El usuario canceló o no ingresó nada
        }

        try {
            int roleId = Integer.parseInt(roleIdStr.trim());
            User user = userService.registerUser(fullName.trim(), email.trim(), roleId);

            if (user == null || user.getUserId() == 0) {
                 JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario. Verifique los datos.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID de rol debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error durante el registro: " + e.getMessage(), "Error General", JOptionPane.ERROR_MESSAGE);
        }
    }
}
