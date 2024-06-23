package lk.ijse.Laptop_Shop_Management.dao.custom;

import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Salary;

import java.sql.SQLException;

public interface SalaryDAO extends SuperDAO {
    boolean save(Salary salary) throws SQLException, ClassNotFoundException;

    boolean update(Salary salary) throws SQLException, ClassNotFoundException;

    String getSalary(int id) throws SQLException, ClassNotFoundException;
}
