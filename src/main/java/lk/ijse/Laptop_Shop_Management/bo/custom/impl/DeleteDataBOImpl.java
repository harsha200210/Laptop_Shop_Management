package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.*;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.*;
import lk.ijse.Laptop_Shop_Management.dto.*;
import lk.ijse.Laptop_Shop_Management.entity.*;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;

public class DeleteDataBOImpl implements DeleteDataBO {
    CustomerDAO customerDAO;
    DriverDAO driverDAO;
    EmployeeDAO employeeDAO;
    ItemDAO itemDAO;
    SupplierDAO supplierDAO;

    public DeleteDataBOImpl() {
        this.customerDAO = (CustomerDAO) DAOFactory.getDAO(DAOFactory.DAOType.CUSTOMER);
        this.driverDAO = (DriverDAO) DAOFactory.getDAO(DAOFactory.DAOType.DRIVER);
        this.employeeDAO = (EmployeeDAO) DAOFactory.getDAO(DAOFactory.DAOType.EMPLOYEE);
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
        this.supplierDAO = (SupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.SUPPLIER);
    }

    @Override
    public ObservableList<DriverDTO> getDeleteDrivers() throws SQLException, ClassNotFoundException {
        ObservableList<DriverDTO> list = FXCollections.observableArrayList();
        ObservableList<Driver> deleteDrivers = driverDAO.getDeleteDrivers();
        for (Driver driver : deleteDrivers) {
            list.add(new DriverDTO(driver.getId(),driver.getName(),driver.getNic(),driver.getAddress(),driver.getEmail(),driver.getTel(),driver.getStatus()));
        }
        return list;
    }

    @Override
    public ObservableList<EmployeeDTO> getDeleteEmployee() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeDTO> list = FXCollections.observableArrayList();
        ObservableList<Employee> deleteEmployee = employeeDAO.getDeleteEmployee();
        for (Employee employee : deleteEmployee) {
            list.add(new EmployeeDTO(employee.getId(),employee.getName(),employee.getNic(),employee.getAddress(),employee.getEmail(),employee.getTel(),employee.getUser_id(),employee.getStatus()));
        }
        return list;
    }

    @Override
    public ObservableList<SupplierDTO> getDeleteSupplier() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierDTO> list = FXCollections.observableArrayList();
        ObservableList<Supplier> deleteSupplier = supplierDAO.getDeleteSupplier();
        for (Supplier supplier : deleteSupplier) {
            list.add(new SupplierDTO(supplier.getId(),supplier.getName(),supplier.getNic(),supplier.getAddress(),supplier.getEmail(),supplier.getTel(),supplier.getStatus()));
        }
        return list;
    }

    @Override
    public ObservableList<ItemDTO> getDeleteItem() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDTO> list = FXCollections.observableArrayList();
        ObservableList<Item> deleteItem = itemDAO.getDeleteItem();

        for (Item item : deleteItem) {
            list.add(new ItemDTO(item.getId(),item.getModel(),item.getQty(),item.getPrice(),item.getStatus()));
        }
        return list;
    }

    @Override
    public ObservableList<CustomerDTO> getDeleteCustomer() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerDTO> list = FXCollections.observableArrayList();
        ObservableList<Customer> deleteCustomer = customerDAO.getDeleteCustomer();
        for (Customer customer : deleteCustomer) {
            list.add(new CustomerDTO(customer.getId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus()));
        }
        return list;
    }
}
