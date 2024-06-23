package lk.ijse.Laptop_Shop_Management.dao.custom;

import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.User;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    boolean save(User user) throws SQLException, FileNotFoundException, ClassNotFoundException;

    boolean checkUser(User user) throws SQLException, ClassNotFoundException;

    String getOwner() throws SQLException, ClassNotFoundException;

    boolean updateUser(User user) throws SQLException, ClassNotFoundException;
}
