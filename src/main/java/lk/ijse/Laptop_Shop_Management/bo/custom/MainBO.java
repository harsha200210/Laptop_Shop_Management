package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;

import java.sql.SQLException;

public interface MainBO extends SuperBO {

    int getOrderCount() throws SQLException, ClassNotFoundException;

    int customerCount() throws SQLException, ClassNotFoundException;

    int supplierCount() throws SQLException, ClassNotFoundException;

    int itemCount() throws SQLException, ClassNotFoundException;

    int employeeCount() throws SQLException, ClassNotFoundException;

    int driverCount() throws SQLException, ClassNotFoundException;
}
