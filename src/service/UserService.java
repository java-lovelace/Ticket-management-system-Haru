package service;

import domain.User;

import java.util.List;

public interface UserService {
    User registerUser(String fullName, String email, int roleId);
    User getUserById(int userId);
    List<User> getAllUsers();
}
