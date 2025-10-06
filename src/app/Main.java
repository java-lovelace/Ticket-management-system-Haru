package app;

import controller.*;
import dao.*;
import service.*;
import view.*;


import javax.swing.JOptionPane;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        // DAOs
        UserDao userDao = new UserDaoImpl();
        RoleDao roleDao = new RoleDaoImpl();
        TicketDao ticketDao = new TicketDaoImpl();
        CategoryDao categoryDao = new CategoryDaoImpl();
        StatusDao statusDao = new StatusDaoImpl();
        CommentDao commentDao = new CommentDaoImpl();

        // Servicios
        UserService userService = new UserServiceImpl(userDao, roleDao);
        TicketService ticketService = new TicketServiceImpl(ticketDao, userDao, categoryDao, statusDao, commentDao);

        // Controladores y vistas
        UserController userController = new UserController(userService);
        TicketController ticketController = new TicketController(ticketService);
        UserRegisterView userRegisterView = new UserRegisterView(userService);
        TicketListView ticketListView = new TicketListView(ticketController);
        TicketReportView ticketReportView = new TicketReportView(ticketController);

        boolean running = true;

        while (running) {
            String[] options = {
                    "Registrar usuario",
                    "Listar usuarios",
                    "Listar tickets por asignado",
                    "Ver reporte de Top 3 categorías",
                    "Añadir comentario a ticket",
                    "Buscar tickets por estado y categoría",
                    "Salir"
            };


            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opción:",
                    "🎟️ Sistema de Tickets",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == -1) { // Si el usuario cierra la ventana
                running = false;
            } else {
                switch (choice) {
                    case 0 -> userRegisterView.showRegisterForm();
                    case 1 -> userController.listAllUsers();
                    case 2 -> ticketListView.showTicketsByAssignee();
                    case 3 -> ticketReportView.showTopCategoriesReport();
                    case 4 -> ticketController.addComment();
                    case 5 -> ticketController.findTicketsByStatusAndCategory();
                    case 6 -> running = false;
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            }
        }

        JOptionPane.showMessageDialog(null, "👋 Saliendo del sistema...", "Salir", JOptionPane.INFORMATION_MESSAGE);

    }
}
