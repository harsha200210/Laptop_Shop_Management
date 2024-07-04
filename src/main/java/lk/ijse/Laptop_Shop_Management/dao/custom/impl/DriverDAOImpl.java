package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.DriverDAO;
import lk.ijse.Laptop_Shop_Management.entity.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDAOImpl implements DriverDAO {
    public static Driver driver;

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT status FROM driver");

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE driver SET status = ? WHERE NIC = ?","Delete",nic);
    }

    @Override
    public boolean save(Driver driver) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO driver (name, NIC, address, email, tel, status) VALUES (?, ?, ?, ?, ?, ?)", driver.getName(), driver.getNic(), driver.getAddress(), driver.getEmail(), driver.getTel(), driver.getStatus());
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE NIC = ?",nic);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                driver = new Driver(resultSet.getInt("driver_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getString("status"));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find driver id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find driver id!!!").show();
        }
        return false;
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE driver SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?", driver.getName(), driver.getAddress(), driver.getEmail(), driver.getTel(), driver.getNic());
    }

    @Override
    public Driver search(String nic) throws SQLException, ClassNotFoundException {
        driver = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver WHERE NIC = ?",nic);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                driver = new Driver(resultSet.getInt("driver_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getString("status"));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find driver id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find driver id!!!").show();
        }
        return driver;
    }

    @Override
    public ObservableList<Driver> getObject() throws SQLException, ClassNotFoundException {
        ObservableList<Driver> obList = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver");

        while (resultSet.next()) {
            if (!resultSet.getString("status").equals("Delete")) {
                obList.add(new Driver(resultSet.getInt("driver_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getString("status")));
            }
        }

        return obList;
    }

    @Override
    public ObservableList<String> getDriverName() throws SQLException, ClassNotFoundException {
        ObservableList<String> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT name,status FROM driver");

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")) {
                list.add(resultSet.getString(1));
            }
        }
        return list;
    }

    @Override
    public ObservableList<Driver> getDeleteDrivers() throws SQLException, ClassNotFoundException {
        ObservableList<Driver> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM driver");

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new Driver(resultSet.getInt("driver_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getString("status")));
            }
        }
        return list;
    }

    @Override
    public int getDriverId(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT driver_id FROM driver WHERE name = ?",name);

        if (resultSet.next()){
            return resultSet.getInt("driver_id");
        }
        return -1;
    }
}
