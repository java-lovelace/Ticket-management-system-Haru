package service;

import domain.Ticket;
import domain.User;

import java.util.List;
import java.util.Map;

public interface TicketService {
    Ticket createTicket(String title, String description, int reporterId, int categoryId);
    Ticket assignTicket(int ticketId, int assigneeId);
    Ticket changeTicketStatus(int ticketId, int statusId);
    void addCommentToTicket(int ticketId, int userId, String content);
    List<Ticket> findTicketsByStatusAndCategory(String statusName, String categoryName);
    List<Ticket> findTicketsByAssignee(int assigneeId);
    Map<String, Integer> getTopCategories(int limit);
    Ticket getTicketById(int ticketId);
}
