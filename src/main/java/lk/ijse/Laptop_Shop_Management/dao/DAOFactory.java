package lk.ijse.Laptop_Shop_Management.dao;

import lk.ijse.Laptop_Shop_Management.dao.custom.impl.*;

public class DAOFactory {

    public enum DAOType{
        CUSTOMER, DRIVER, EMPLOYEE, ITEM, SUPPLIER, QUERY, ITEMSUPPLIER, CONFIGURATION, SALARY, USER, ORDER, ITEMDETAIL, DELIVERY, PAYMENT
    }

    public static SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DRIVER:
                return new DriverDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case ITEMSUPPLIER:
                return new ItemSupplierDAOImpl();
            case CONFIGURATION:
                return new ConfigurationDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case USER:
                return new UserDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ITEMDETAIL:
                return new ItemDetailDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            default:
                return null;
        }
    }
}
