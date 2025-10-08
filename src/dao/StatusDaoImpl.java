package dao;

import config.ConfigDb;
import domain.Role;
import domain.Status;
import domain.User;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusDaoImpl implements StatusDao {

    @Override
    public Status findById(int id) {
        String sql = "SELECT * FROM statuses WHERE status_id = ?";
        Status status = null;
        try(Connection connection = ConfigDb.openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    status = new Status();
                    int statusId = rs.getInt("status_id");
                    String name = rs.getString("name");
                    status.setStatusId(statusId);
                    status.setName(name);

                }
            }
        } catch(SQLException error){
            JOptionPane.showMessageDialog(null, "Error al buscar el estado: " + error.getMessage());
        }
        return status;
    }

    @Override
    public List<Status> findAll() {
        String sql = "SELECT * FROM statuses";
        List<Status> statuses = new ArrayList<>();
        try(Connection connection = ConfigDb.openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Status status = new Status();
                status.setStatusId(rs.getInt("status_id"));
                status.setName(rs.getString("name"));
                statuses.add(status);
            }
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null,"Error al obtener los estados: " + error.getMessage());
        }
        return statuses;
    }
}
