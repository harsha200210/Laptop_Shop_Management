package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.EmployeeDTO;
import lk.ijse.Laptop_Shop_Management.dto.SalaryDTO;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.EmployeeTm;

import java.sql.SQLException;

public interface EmployeeBO extends SuperBO {

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String nic) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    EmployeeDTO search(String nic) throws SQLException, ClassNotFoundException;

    ObservableList<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException;

    boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException;

    double getTaxRate() throws SQLException, ClassNotFoundException;

    boolean update(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException;

    String getSalary(int id) throws SQLException, ClassNotFoundException;

    boolean save(EmployeeDTO employeeDTO, SalaryDTO salaryDTO) throws SQLException;

    boolean updateSalary(SalaryDTO salaryDTO) throws SQLException;
}
