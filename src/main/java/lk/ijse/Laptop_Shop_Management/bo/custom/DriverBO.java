package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.DriverDTO;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public interface DriverBO extends SuperBO {
    int driverCount() throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    boolean save(DriverDTO driverDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String nic) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    DriverDTO search(String nic) throws SQLException, ClassNotFoundException;

    ObservableList<DriverDTO> getDriver() throws SQLException, ClassNotFoundException;

    ObservableList<String> getDriverName() throws SQLException, ClassNotFoundException;

    ObservableList<DriverTm> getDeleteDrivers() throws SQLException, ClassNotFoundException;

    int getDriverId(String name) throws SQLException, ClassNotFoundException;
}
