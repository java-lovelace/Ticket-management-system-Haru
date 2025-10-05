package dao;

import domain.Ticket;
import java.util.List;
import java.util.Map;

public interface TicketDao {
    void create(Ticket ticket);
    void update(Ticket ticket);
    Ticket findById(int id);
    List<Ticket> findByAssignee(int assigneeId);
    List<Ticket> findByStatusAndCategory(String statusName, String categoryName);
    Map<String, Integer> findTopCategories(int limit);
    List<Ticket> findAll();
}
