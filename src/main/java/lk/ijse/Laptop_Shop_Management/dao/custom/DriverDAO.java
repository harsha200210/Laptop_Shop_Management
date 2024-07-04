package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Driver;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public interface DriverDAO extends CrudDAO<Driver> , SuperDAO {
    ObservableList<Driver> getObject() throws SQLException, ClassNotFoundException;

    ObservableList<String> getDriverName() throws SQLException, ClassNotFoundException;

    ObservableList<Driver> getDeleteDrivers() throws SQLException, ClassNotFoundException;

    int getDriverId(String name) throws SQLException, ClassNotFoundException;
}
