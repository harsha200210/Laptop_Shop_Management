package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.CustomerDTO;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {

    CustomerDTO search(String nic) throws SQLException, ClassNotFoundException;

    boolean save(CustomerDTO customer) throws SQLException, ClassNotFoundException;

    boolean checkId(String nic) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    ObservableList<CustomerDTO> getCustomers() throws SQLException, ClassNotFoundException;

}
