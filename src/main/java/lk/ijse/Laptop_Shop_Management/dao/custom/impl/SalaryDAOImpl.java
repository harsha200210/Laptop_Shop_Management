package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.SalaryDAO;
import lk.ijse.Laptop_Shop_Management.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public boolean save(Salary salary) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO salary VALUES (?,?,?,?,?)",EmployeeDAOImpl.employee.getId(),salary.getSalary(),salary.getTax(),salary.getETF(),salary.getEPF());
    }

    @Override
    public boolean update(Salary salary) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE salary SET salary = ? , tax = ? , ETF = ? , EPF = ? WHERE employee_id = ?",salary.getSalary(),salary.getTax(),salary.getETF(),salary.getEPF(),EmployeeDAOImpl.employee.getId());
    }

    @Override
    public String getSalary(int id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT salary FROM salary WHERE employee_id = ?",id);

        if (resultSet.next()){
            double salary = resultSet.getDouble("salary");
            return String.valueOf(salary);
        }
        return null;
    }
}
