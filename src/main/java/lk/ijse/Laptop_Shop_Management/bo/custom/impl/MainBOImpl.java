package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.*;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.*;

import java.sql.SQLException;

public class MainBOImpl implements MainBO {
    CustomerDAO customerDAO;
    DriverDAO driverDAO ;
    EmployeeDAO employeeDAO;
    ItemDAO itemDAO;
    SupplierDAO supplierDAO;
    OrderDAO orderDAO;

    public MainBOImpl() {
        this.customerDAO = (CustomerDAO) DAOFactory.getDAO(DAOFactory.DAOType.CUSTOMER);
        this.driverDAO = (DriverDAO) DAOFactory.getDAO(DAOFactory.DAOType.DRIVER);
        this.employeeDAO = (EmployeeDAO) DAOFactory.getDAO(DAOFactory.DAOType.EMPLOYEE);
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
        this.supplierDAO = (SupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.SUPPLIER);
        this.orderDAO = (OrderDAO) DAOFactory.getDAO(DAOFactory.DAOType.ORDER);
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCount();
    }

    @Override
    public int customerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.count();
    }

    @Override
    public int supplierCount() throws SQLException, ClassNotFoundException {
        return supplierDAO.count();
    }

    @Override
    public int itemCount() throws SQLException, ClassNotFoundException {
        return itemDAO.count();
    }

    @Override
    public int employeeCount() throws SQLException, ClassNotFoundException {
        return employeeDAO.count();
    }

    @Override
    public int driverCount() throws SQLException, ClassNotFoundException {
        return driverDAO.count();
    }
}
