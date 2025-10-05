package controller;

import service.TicketService;
import java.util.Map;

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
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
}
