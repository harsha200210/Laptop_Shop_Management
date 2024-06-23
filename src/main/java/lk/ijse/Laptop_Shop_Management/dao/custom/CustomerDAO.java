package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Customer;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> , SuperDAO {
//    int customerCount() throws SQLException, ClassNotFoundException;
//
//    CustomerDTO search(String nic) throws SQLException, ClassNotFoundException;
//
//    boolean save(CustomerDTO customer) throws SQLException, ClassNotFoundException;
//
//    boolean checkId(String nic) throws SQLException, ClassNotFoundException;
//
//    boolean update() throws SQLException, ClassNotFoundException;
//
//    boolean delete(String nic) throws SQLException, ClassNotFoundException;
//
//    ObservableList<CustomerDTO> getCustomers() throws SQLException, ClassNotFoundException;

    ObservableList<Customer> getObject() throws SQLException, ClassNotFoundException;

    Customer getCustomerName(String tel) throws SQLException, ClassNotFoundException;

    ObservableList<DriverTm> getDeleteCustomer() throws SQLException, ClassNotFoundException;
}
