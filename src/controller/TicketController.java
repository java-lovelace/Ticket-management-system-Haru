package controller;

import domain.Ticket;
import service.TicketService;

import java.util.List;
import java.util.Map;

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

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
