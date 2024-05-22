package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Driver;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverRepo {

    public static Driver driver;

    public static int driverCount() throws SQLException {
        String sql = "SELECT status FROM driver";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    public static boolean delete(String nic) throws SQLException {
        String sql = "UPDATE driver SET status = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,"Delete");
        preparedStatement.setObject(2,nic);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(Driver driver) throws SQLException {
        String sql = "INSERT INTO driver (name, NIC, address, email, tel, status) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,driver.getName());
        preparedStatement.setObject(2,driver.getNic());
        preparedStatement.setObject(3,driver.getAddress());
        preparedStatement.setObject(4,driver.getEmail());
        preparedStatement.setObject(5,driver.getTel());
        preparedStatement.setObject(6,driver.getStatus());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkId(String nic) throws SQLException {
        String sql = "SELECT * FROM driver WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

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

    public static boolean update() throws SQLException {
        String sql = "UPDATE driver SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,driver.getName());
        preparedStatement.setObject(2,driver.getAddress());
        preparedStatement.setObject(3,driver.getEmail());
        preparedStatement.setObject(4,driver.getTel());
        preparedStatement.setObject(5,driver.getNic());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Driver search(String nic) throws SQLException {
        driver = null;

        String sql = "SELECT * FROM driver WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

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

    public static ObservableList<DriverTm> getDriver() throws SQLException {
        ObservableList<DriverTm> obList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM driver";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (!resultSet.getString("status").equals("Delete")) {
                obList.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }

        return obList;
    }

    public static ObservableList<String> getDriverName() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();

        String sql = "SELECT name,status FROM driver";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")) {
                list.add(resultSet.getString(1));
            }
        }
        return list;
    }

    public static ObservableList<DriverTm> getDeleteDrivers() throws SQLException {
        ObservableList<DriverTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM driver";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }
        return list;
    }

    public static int getDriverId(String name) throws SQLException {
        String sql = "SELECT driver_id FROM driver WHERE name = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getInt("driver_id");
        }
        return -1;
    }
}
