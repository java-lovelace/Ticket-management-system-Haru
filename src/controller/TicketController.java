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

    public void createTicket() {
        try {
            String title = JOptionPane.showInputDialog(null, "Ingrese el título del ticket:");
            String description = JOptionPane.showInputDialog(null, "Ingrese la descripción del ticket:");
            String reporterIdStr = JOptionPane.showInputDialog(null, "Ingrese su ID de usuario (reportador):");
            String categoryIdStr = JOptionPane.showInputDialog(null, "Ingrese el ID de la categoría:");

            if (title == null || description == null || reporterIdStr == null || categoryIdStr == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                return;
            }

            int reporterId = Integer.parseInt(reporterIdStr);
            int categoryId = Integer.parseInt(categoryIdStr);

            Ticket newTicket = ticketService.createTicket(title, description, reporterId, categoryId);

            if (newTicket != null) {
                JOptionPane.showMessageDialog(null, "Ticket creado exitosamente con ID: " + newTicket.getTicketId());
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo crear el ticket. Verifique que el usuario y la categoría existan.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Los IDs deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void assignTicket() {
        try {
            String ticketIdStr = JOptionPane.showInputDialog(null, "Ingrese el ID del ticket a asignar:");
            String assigneeIdStr = JOptionPane.showInputDialog(null, "Ingrese el ID del usuario asignado:");

            if (ticketIdStr == null || assigneeIdStr == null) {
                JOptionPane.showMessageDialog(null, "Operación cancelada.");
                return;
            }

            int ticketId = Integer.parseInt(ticketIdStr);
            int assigneeId = Integer.parseInt(assigneeIdStr);

            Ticket updatedTicket = ticketService.assignTicket(ticketId, assigneeId);

            if (updatedTicket != null) {
                JOptionPane.showMessageDialog(null, "Ticket " + ticketId + " asignado exitosamente al usuario " + assigneeId + ".");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar el ticket. Verifique que el ticket y el usuario existan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Los IDs deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeTicketStatus() {
        try {
            String ticketIdStr = JOptionPane.showInputDialog(null, "Ingrese el ID del ticket:");
            String statusIdStr = JOptionPane.showInputDialog(null, "Ingrese el nuevo ID de estado:");

            int ticketId = Integer.parseInt(ticketIdStr);
            int statusId = Integer.parseInt(statusIdStr);

            Ticket updatedTicket = ticketService.changeTicketStatus(ticketId, statusId);

            if (updatedTicket != null) {
                JOptionPane.showMessageDialog(null, "El estado del ticket " + ticketId + " ha sido actualizado.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado. Verifique que el ticket y el estado existan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Los IDs deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        }
    }

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
                System.out.println("  📅 Creado: " + t.getCreated_at());
                System.out.println();
            }

        }
    }
}
