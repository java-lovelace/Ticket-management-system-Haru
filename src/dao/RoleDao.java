package dao;

import domain.Role;
import java.util.List;

public interface RoleDao {
    Role findById(int id);
    List<Role> findAll();
}
