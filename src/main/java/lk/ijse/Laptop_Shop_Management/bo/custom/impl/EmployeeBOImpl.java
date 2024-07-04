package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.EmployeeBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ConfigurationDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.EmployeeDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.QueryDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.SalaryDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.dto.EmployeeDTO;
import lk.ijse.Laptop_Shop_Management.dto.SalaryDTO;
import lk.ijse.Laptop_Shop_Management.entity.Employee;
import lk.ijse.Laptop_Shop_Management.entity.Salary;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.EmployeeTm;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO;
    QueryDAO queryDAO;
    SalaryDAO salaryDAO;
    ConfigurationDAO configurationDAO;

    public EmployeeBOImpl() {
        this.employeeDAO = (EmployeeDAO) DAOFactory.getDAO(DAOFactory.DAOType.EMPLOYEE);
        this.queryDAO = (QueryDAO) DAOFactory.getDAO(DAOFactory.DAOType.QUERY);
        this.salaryDAO = (SalaryDAO) DAOFactory.getDAO(DAOFactory.DAOType.SALARY);
        this.configurationDAO = (ConfigurationDAO) DAOFactory.getDAO(DAOFactory.DAOType.CONFIGURATION);
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(nic);
    }

    @Override
    public boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getNic(), employeeDTO.getAddress(), employeeDTO.getEmail(), employeeDTO.getTel(), employeeDTO.getUser_id(), employeeDTO.getStatus());
        return employeeDAO.save(employee);
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        return employeeDAO.checkId(nic);
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return employeeDAO.update();
    }

    @Override
    public EmployeeDTO search(String nic) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(nic);
        if (employee != null){
            return new EmployeeDTO(employee.getId(), employee.getName(), employee.getNic(), employee.getAddress(), employee.getEmail(), employee.getTel(), employee.getUser_id(), employee.getStatus());
        }
        return null;
    }

    @Override
    public ObservableList<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException {
        return queryDAO.getEmployee();
    }

    @Override
    public boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        Salary salary = new Salary(salaryDTO.getSalary(),salaryDTO.getTax(),salaryDTO.getETF(),salaryDTO.getEPF());
        return salaryDAO.save(salary);
    }

    @Override
    public double getTaxRate() throws SQLException, ClassNotFoundException {
        return configurationDAO.getTaxRate();
    }

    @Override
    public boolean update(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        Salary salary = new Salary(salaryDTO.getSalary(),salaryDTO.getTax(),salaryDTO.getETF(),salaryDTO.getEPF());
        return salaryDAO.update(salary);
    }

    @Override
    public String getSalary(int id) throws SQLException, ClassNotFoundException {
        return salaryDAO.getSalary(id);
    }

    @Override
    public boolean save(EmployeeDTO employeeDTO, SalaryDTO salaryDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            if (save(employeeDTO)){
                if (checkId(employeeDTO.getNic())){
                    if (save(salaryDTO)){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e){
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean updateSalary(SalaryDTO salaryDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (update()){
                if (update(salaryDTO)){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e){
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
