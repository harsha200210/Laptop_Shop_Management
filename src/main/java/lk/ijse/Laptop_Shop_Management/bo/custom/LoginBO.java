package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;

import java.sql.SQLException;

public interface LoginBO extends SuperBO {
    boolean checkUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
}
