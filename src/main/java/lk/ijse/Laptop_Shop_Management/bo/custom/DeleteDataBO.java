package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.*;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public interface DeleteDataBO extends SuperBO {

    ObservableList<DriverDTO> getDeleteDrivers() throws SQLException, ClassNotFoundException;

    ObservableList<EmployeeDTO> getDeleteEmployee() throws SQLException, ClassNotFoundException;

    ObservableList<SupplierDTO> getDeleteSupplier() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDTO> getDeleteItem() throws SQLException, ClassNotFoundException;

    ObservableList<CustomerDTO> getDeleteCustomer() throws SQLException, ClassNotFoundException;
}
