package dao;

import domain.Status;
import java.util.List;

public interface StatusDao {
    Status findById(int id);
    List<Status> findAll();
}
