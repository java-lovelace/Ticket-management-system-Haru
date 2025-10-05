package dao;


import domain.Category;
import java.util.List;

public interface CategoryDao {
    Category findById(int id);
    List<Category> findAll();
}
