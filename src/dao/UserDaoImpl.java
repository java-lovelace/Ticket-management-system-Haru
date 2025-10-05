package dao;

import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void create(User user) {
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>();
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
