package view;

import controller.TicketController;
import javax.swing.*;
import java.util.Map;

public class TicketReportView {

    private final TicketController ticketController;

    public TicketReportView(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    public void showTopCategoriesReport() {
        Map<String, Integer> topCategories = ticketController.getTopCategories(3);

        StringBuilder report = new StringBuilder("📊 Top 3 categorías con más tickets:\n\n");

        if (topCategories.isEmpty()) {
            report.append("No hay tickets registrados aún.");
        } else {
            topCategories.forEach((category, count) ->
                    report.append("- ").append(category)
                            .append(": ").append(count)
                            .append(" tickets\n")
            );
        }

        JOptionPane.showMessageDialog(null, report.toString(),
                "Reporte de Categorías", JOptionPane.INFORMATION_MESSAGE);
    }
}
