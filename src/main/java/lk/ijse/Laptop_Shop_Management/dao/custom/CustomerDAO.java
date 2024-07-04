package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> , SuperDAO {

    ObservableList<Customer> getObject() throws SQLException, ClassNotFoundException;

    Customer getCustomerName(String tel) throws SQLException, ClassNotFoundException;

    ObservableList<Customer> getDeleteCustomer() throws SQLException, ClassNotFoundException;
}
