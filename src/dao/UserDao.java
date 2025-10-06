package dao;

import domain.User;
import java.util.List;

public interface UserDao {
    User create(User user);
    User findById(int id);
    List<User> findAll();
    User findByEmail(String email);
}
