package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface SignUpBO extends SuperBO {

    boolean save(UserDTO userDTO) throws SQLException, FileNotFoundException, ClassNotFoundException;
}
