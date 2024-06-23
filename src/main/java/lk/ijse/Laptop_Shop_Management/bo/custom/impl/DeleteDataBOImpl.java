package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.*;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.*;
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
    public ObservableList<DriverTm> getDeleteDrivers() throws SQLException, ClassNotFoundException {
        return driverDAO.getDeleteDrivers();
    }

    @Override
    public ObservableList<DriverTm> getDeleteEmployee() throws SQLException, ClassNotFoundException {
        return employeeDAO.getDeleteEmployee();
    }

    @Override
    public ObservableList<DriverTm> getDeleteSupplier() throws SQLException, ClassNotFoundException {
        return supplierDAO.getDeleteSupplier();
    }

    @Override
    public ObservableList<ItemTm> getDeleteItem() throws SQLException, ClassNotFoundException {
        return itemDAO.getDeleteItem();
    }

    @Override
    public ObservableList<DriverTm> getDeleteCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.getDeleteCustomer();
    }
}
