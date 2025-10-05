package dao;

import domain.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDaoImpl implements StatusDao {

    @Override
    public Status findById(int id) {
        return null;
    }

    @Override
    public List<Status> findAll() {
        return new ArrayList<>();
    }
}
