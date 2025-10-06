package view;

import controller.TicketController;
import domain.Ticket;

import javax.swing.*;
import java.util.List;

public class TicketListView {

    private final TicketController ticketController;

    public TicketListView(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    public void showTicketsByAssignee() {
        String input = JOptionPane.showInputDialog(null,
                "Ingrese el ID del usuario asignado:",
                "🔍 Buscar tickets por asignado",
                JOptionPane.QUESTION_MESSAGE);

        if (input == null || input.trim().isEmpty()) {
            return; // Usuario canceló o no escribió nada
        }

        try {
            int assigneeId = Integer.parseInt(input.trim());
            List<Ticket> tickets = ticketController.getTicketsByAssignee(assigneeId);

            StringBuilder report = new StringBuilder("🎫 Tickets asignados al usuario ID: " + assigneeId + "\n\n");

            if (tickets.isEmpty()) {
                report.append("No hay tickets asignados a este usuario.");
            } else {
                for (Ticket t : tickets) {
                    report.append("• [").append(t.getTicketId()).append("] ").append(t.getTitle()).append("\n")
                            .append("  📌 Reportado por: ").append(t.getReporter().getFullName()).append("\n")
                            .append("  🏷️ Categoría: ").append(t.getCategory().getName()).append("\n")
                            .append("  🔖 Estado: ").append(t.getStatus().getName()).append("\n")
                            .append("  📅 Creado: ").append(t.getCreatedAt()).append("\n\n");
                }
            }

            JOptionPane.showMessageDialog(null, report.toString(),
                    "Tickets por Asignado", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Por favor ingrese un número válido de ID.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
