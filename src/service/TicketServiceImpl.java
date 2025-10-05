package service;

import dao.*;
import domain.Ticket;

import java.util.List;
import java.util.Map;

public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final StatusDao statusDao;
    private final CommentDao commentDao;

    // Inyección de dependencias vía constructor
    public TicketServiceImpl(TicketDao ticketDao, UserDao userDao, CategoryDao categoryDao, StatusDao statusDao, CommentDao commentDao) {
        this.ticketDao = ticketDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.statusDao = statusDao;
        this.commentDao = commentDao;
    }

    @Override
    public Ticket createTicket(String title, String description, int reporterId, int categoryId) {
        return null;
    }

    @Override
    public Ticket assignTicket(int ticketId, int assigneeId) {
        return null;
    }

    @Override
    public Ticket changeTicketStatus(int ticketId, int statusId) {
        return null;
    }

    @Override
    public void addCommentToTicket(int ticketId, int userId, String content) {

    }

    @Override
    public List<Ticket> findTicketsByStatusAndCategory(String statusName, String categoryName) {
        return List.of();
    }

    @Override
    public List<Ticket> findTicketsByAssignee(int assigneeId) {
        return ticketDao.findByAssignee(assigneeId);
    }

    @Override
    public Map<String, Integer> getTopCategories(int limit) {
        return ticketDao.findTopCategories(limit);
    }

    @Override
    public Ticket getTicketById(int ticketId) {
        return null;
    }
}
