package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Customer;
import lk.ijse.Laptop_Shop_Management.model.tm.CustomerTm;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepo {

    public static Customer customer;

    public static int customerCount() throws SQLException {
        String sql = "SELECT status FROM customer";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString(1).equals("Delete")) {
                count++;
            }
        }
        return count;
    }

    public static Customer search(String nic) throws SQLException {
        customer = null;
        
        String sql = "SELECT * FROM customer WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")) {
                customer = new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel"), resultSet.getString("status"));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find customer id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find customer id!!!").show();
        }

        return customer;
    }

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (name, NIC, address, email, tel, status) VALUES  (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,customer.getName());
        preparedStatement.setObject(2,customer.getNic());
        preparedStatement.setObject(3,customer.getAddress());
        preparedStatement.setObject(4,customer.getEmail());
        preparedStatement.setObject(5,customer.getTel());
        preparedStatement.setObject(6,customer.getStatus());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkId(String nic) throws SQLException {
        String sql = "SELECT * FROM customer WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")) {
                customer = new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel"), resultSet.getString("status"));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find customer id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find customer id!!!").show();
        }
        return false;
    }

    public static boolean update() throws SQLException {
        String sql = "UPDATE customer SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,customer.getName());
        preparedStatement.setObject(2,customer.getAddress());
        preparedStatement.setObject(3,customer.getEmail());
        preparedStatement.setObject(4,customer.getTel());
        preparedStatement.setObject(5,customer.getNic());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean delete(String nic) throws SQLException {
        String sql = "UPDATE customer SET status = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,"Delete");
        preparedStatement.setObject(2,nic);

        return preparedStatement.executeUpdate() > 0;
    }

    public static ObservableList<CustomerTm> getCustomers() throws SQLException {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new CustomerTm(resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel")));
            }
        }
        return obList;
    }

    public static Customer getCustomerName(String tel) throws SQLException {
        Customer customer = new Customer();

        String sql = "SELECT customer_id, name, status FROM customer WHERE tel = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,tel);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString(3).equals("Delete")){
                customer.setId(resultSet.getInt(1));
                customer.setName(resultSet.getString(2));
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find customer!!!").show();
        }
        return customer;
    }

    public static ObservableList<DriverTm> getDeleteCustomer() throws SQLException {
        ObservableList<DriverTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }
        return list;
    }
}
