package service;

import dao.*;
import domain.*;

import java.util.List;
import java.util.Map;

public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final StatusDao statusDao;
    private final CommentDao commentDao;

    private static final String INITIAL_STATUS_NAME = "Abierto";

    public TicketServiceImpl(TicketDao ticketDao, UserDao userDao, CategoryDao categoryDao, StatusDao statusDao, CommentDao commentDao) {
        this.ticketDao = ticketDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.statusDao = statusDao;
        this.commentDao = commentDao;
    }

    @Override
    public void addCommentToTicket(int ticketId, int userId, String content) {
        User user = new User();
        user.setUserId(userId);
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        Comment newComment = new Comment();
        newComment.setTicket(ticket);
        newComment.setUser(user);
        newComment.setContent(content);
        commentDao.create(newComment);
    }

    @Override
    public List<Ticket> findTicketsByStatusAndCategory(String statusName, String categoryName) {        
        return ticketDao.findByStatusAndCategory(statusName, categoryName);
    }



    @Override
    public Ticket createTicket(String title, String description, int reporterId, int categoryId) {
        User reporter = userDao.findById(reporterId);
        if (reporter == null) {
            // Opcional: lanzar una excepción personalizada
            System.err.println("Error: El usuario reportador con ID " + reporterId + " no existe.");
            return null;
        }

        Category category = categoryDao.findById(categoryId);
        if (category == null) {
            System.err.println("Error: La categoría con ID " + categoryId + " no existe.");
            return null;
        }

        // Asumimos que un estado inicial "Abierto" existe.
        // Buscamos el estado inicial iterando sobre todos los estados disponibles.
        Status initialStatus = statusDao.findAll().stream()
                .filter(status -> INITIAL_STATUS_NAME.equalsIgnoreCase(status.getName()))
                .findFirst()
                .orElse(null);
        if (initialStatus == null) {
            System.err.println("Error: El estado inicial '" + INITIAL_STATUS_NAME + "' no se encuentra en la base de datos.");
            return null;
        }

        Ticket newTicket = new Ticket();
        newTicket.setTitle(title);
        newTicket.setDescription(description);
        newTicket.setReporter(reporter);
        newTicket.setCategory(category);
        newTicket.setStatus(initialStatus);

        ticketDao.create(newTicket);
        return newTicket;
    }

    @Override
    public Ticket assignTicket(int ticketId, int assigneeId) {
        Ticket ticket = ticketDao.findById(ticketId);
        if (ticket == null) {
            System.err.println("Error: No se pudo asignar. El ticket con ID " + ticketId + " no existe.");
            return null;
        }

        User assignee = userDao.findById(assigneeId);
        if (assignee == null) {
            System.err.println("Error: No se pudo asignar. El usuario con ID " + assigneeId + " no existe.");
            return null;
        }

        ticket.setAssignee(assignee);
        ticketDao.update(ticket);
        return ticket;
    }

    @Override
    public Ticket changeTicketStatus(int ticketId, int statusId) {
        Ticket ticket = ticketDao.findById(ticketId);
        if (ticket == null) {
            System.err.println("Error: No se pudo cambiar el estado. El ticket con ID " + ticketId + " no existe.");
            return null;
        }

        Status newStatus = statusDao.findById(statusId);
        if (newStatus == null) {
            System.err.println("Error: No se pudo cambiar el estado. El estado con ID " + statusId + " no existe.");
            return null;
        }

        // Si ambas entidades existen, se procede con la actualización.
        ticket.setStatus(newStatus);
        ticketDao.update(ticket);
        return ticket;
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
        return ticketDao.findById(ticketId);
    }
}
