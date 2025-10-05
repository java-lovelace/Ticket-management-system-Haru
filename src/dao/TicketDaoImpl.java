package dao;

import domain.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {

    @Override
    public void create(Ticket ticket) {
    }

    @Override
    public void update(Ticket ticket) {
    }

    @Override
    public Ticket findById(int id) {
        return null;
    }

    @Override
    public List<Ticket> findByAssignee(int assigneeId) {
        return new ArrayList<>();
    }

    @Override
    public List<Ticket> findByStatusAndCategory(String statusName, String categoryName) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> findTopCategories(int limit) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>();
    }
}
