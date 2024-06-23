package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.CustomerDAO;
import lk.ijse.Laptop_Shop_Management.entity.Customer;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO {
    public static Customer customer;

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT status FROM customer");

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString(1).equals("Delete")) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Customer search(String nic) throws SQLException, ClassNotFoundException {
        customer = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE NIC = ?",nic);

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

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer (name, NIC, address, email, tel, status) VALUES  (?, ?, ?, ?, ?, ?)",customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus());
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE NIC = ?",nic);

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

    public boolean update() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?",customer.getName(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getNic());
    }

    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET status = ? WHERE NIC = ?","Delete",nic);
    }

    public ObservableList<Customer> getObject() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> obList = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new Customer(resultSet.getInt("customer_id"), resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel"), resultSet.getString("status")));
            }
        }
        return obList;
    }

    public Customer getCustomerName(String tel) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();

        ResultSet resultSet = SQLUtil.execute("SELECT customer_id, name, status FROM customer WHERE tel = ?",tel);

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

    public ObservableList<DriverTm> getDeleteCustomer() throws SQLException, ClassNotFoundException {
        ObservableList<DriverTm> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }
        return list;
    }
}
