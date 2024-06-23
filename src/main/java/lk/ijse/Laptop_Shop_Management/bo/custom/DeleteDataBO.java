package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;

public interface DeleteDataBO extends SuperBO {
    ObservableList<DriverTm> getDeleteDrivers() throws SQLException, ClassNotFoundException;
    ObservableList<DriverTm> getDeleteEmployee() throws SQLException, ClassNotFoundException;
    ObservableList<DriverTm> getDeleteSupplier() throws SQLException, ClassNotFoundException;
    ObservableList<ItemTm> getDeleteItem() throws SQLException, ClassNotFoundException;
    ObservableList<DriverTm> getDeleteCustomer() throws SQLException, ClassNotFoundException;
}
