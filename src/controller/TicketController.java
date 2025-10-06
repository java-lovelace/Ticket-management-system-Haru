package controller;

import domain.Ticket;
import service.TicketService;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Map;

public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void addComment() {
        try {
            String ticketIdStr = JOptionPane.showInputDialog(null, "Ingrese el ID del Ticket:");
            String userIdStr = JOptionPane.showInputDialog(null, "Ingrese su ID de Usuario:");
            String content = JOptionPane.showInputDialog(null, "Escriba su comentario:");

            if (ticketIdStr == null || userIdStr == null || content == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                return;
            }

            int ticketId = Integer.parseInt(ticketIdStr);
            int userId = Integer.parseInt(userIdStr);

            ticketService.addCommentToTicket(ticketId, userId, content);

            JOptionPane.showMessageDialog(null, "Comentario añadido exitosamente");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El ID del ticket y del usuario deben ser números", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void findTicketsByStatusAndCategory() {
        try {
            //Pedimos los filtros al usuario
            String status = JOptionPane.showInputDialog(null, "Ingrese el estado a buscar (ej: Abierto, En Proceso):");
            String category = JOptionPane.showInputDialog(null, "Ingrese la categoría a buscar (ej: Aplicacion, Infraestructura):");

            if (status == null || category == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada");
                return;
            }

            //Llamamos al servicio para obtener la lista de tickets.
            List<Ticket> tickets = ticketService.findTicketsByStatusAndCategory(status, category);

            //Preparamos un mensaje para mostrar los resultados.
            if (tickets.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron tickets con esos criterios");
            } else {
                StringBuilder sb = new StringBuilder("Tickets encontrados:\n\n");
                for (Ticket ticket : tickets) {
                    sb.append("ID: ").append(ticket.getTicketId()).append("\n");
                    sb.append("Título: ").append(ticket.getTitle()).append("\n");
                    sb.append("Reporter: ").append(ticket.getReporter().getFullName()).append("\n");
                    sb.append("Asignado a: ").append(ticket.getAssignee() != null ? ticket.getAssignee().getFullName() : "N/A").append("\n");
                    sb.append("--------------------\n");
                }
                // Mostramos todos los tickets en una sola ventana
                JOptionPane.showMessageDialog(null, sb.toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al buscar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

    // --- US 5.3: Top categorías ---
    public Map<String, Integer> getTopCategories(int limit) {
        return ticketService.getTopCategories(limit);
    }

    public void showTopCategoriesReport() {
        Map<String, Integer> top = ticketService.getTopCategories(3);
        System.out.println("\n📊 Top 3 categorías con más tickets:");
        if (top.isEmpty()) {
            System.out.println("No hay tickets registrados aún.");
        } else {
            top.forEach((category, count) ->
                    System.out.println("- " + category + ": " + count + " tickets")
            );
        }
    }

    // --- US 5.2: Listar tickets por asignado ---
    public List<Ticket> getTicketsByAssignee(int assigneeId) {
        return ticketService.findTicketsByAssignee(assigneeId);
    }

    public void showTicketsByAssignee(int assigneeId) {
        var tickets = ticketService.findTicketsByAssignee(assigneeId);

        System.out.println("\n🎫 Tickets asignados al usuario ID: " + assigneeId);
        if (tickets.isEmpty()) {
            System.out.println("No hay tickets asignados a este usuario.");
        } else {
            for (var t : tickets) {
                System.out.println("- [" + t.getTicketId() + "] " + t.getTitle());
                System.out.println("  📌 Reportado por: " + t.getReporter().getFullName());
                System.out.println("  🏷️ Categoría: " + t.getCategory().getName());
                System.out.println("  🔖 Estado: " + t.getStatus().getName());
                System.out.println("  📅 Creado: " + t.getCreatedAt());
                System.out.println();
            }

        }
    }
}
