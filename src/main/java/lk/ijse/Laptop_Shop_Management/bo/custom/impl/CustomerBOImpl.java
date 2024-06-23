package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.CustomerBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.CustomerDAO;
import lk.ijse.Laptop_Shop_Management.dto.CustomerDTO;
import lk.ijse.Laptop_Shop_Management.entity.Customer;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO;

    public CustomerBOImpl() {
        this.customerDAO = (CustomerDAO) DAOFactory.getDAO(DAOFactory.DAOType.CUSTOMER);
    }

    @Override
    public int customerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.count();
    }

    @Override
    public CustomerDTO search(String nic) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(nic);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus());
    }

    @Override
    public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getNic(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getTel(),customerDTO.getStatus());
        return customerDAO.save(customer);
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.checkId(nic);
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return customerDAO.update();
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(nic);
    }

    @Override
    public ObservableList<CustomerDTO> getCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerDTO> list = FXCollections.observableArrayList();
        ObservableList<Customer> customers = customerDAO.getObject();
        for (Customer customer : customers) {
            list.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus()));
        }
        return list;
    }

    @Override
    public CustomerDTO getCustomerName(String tel) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.getCustomerName(tel);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus());
    }

    @Override
    public ObservableList<DriverTm> getDeleteCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.getDeleteCustomer();
    }
}
