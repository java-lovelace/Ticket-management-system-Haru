package dao;

import domain.Category;
import config.ConfigDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public Category findById(int id) {

        return null;
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>();
    }
}
