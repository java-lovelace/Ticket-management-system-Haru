package view;

import controller.TicketController;
import javax.swing.JOptionPane;

public class TicketOperationsView {

    private final TicketController ticketController;

    public TicketOperationsView(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    public void showTicketOperationsMenu() {
        boolean running = true;
        while (running) {
            String[] options = {
                    "Crear Ticket",
                    "Asignar Ticket",
                    "Cambiar Estado de Ticket",
                    "Volver al Menú Principal"
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una operación de ticket:",
                    "Gestión de Tickets",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> ticketController.createTicket();
                case 1 -> ticketController.assignTicket();
                case 2 -> ticketController.changeTicketStatus();
                case 3, -1 -> running = false; // Salir si elige "Volver" o cierra la ventana
            }
        }
    }
}