package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Salary;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryRepo {
    public static boolean save(Salary salary) throws SQLException {
        String sql = "INSERT INTO salary VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,EmployeeRepo.employee.getId());
        preparedStatement.setObject(2,salary.getSalary());
        preparedStatement.setObject(3,salary.getTax());
        preparedStatement.setObject(4,salary.getETF());
        preparedStatement.setObject(5,salary.getEPF());

        return preparedStatement.executeUpdate()>0;
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET salary = ? , tax = ? , ETF = ? , EPF = ? WHERE employee_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(5,EmployeeRepo.employee.getId());
        preparedStatement.setObject(1,salary.getSalary());
        preparedStatement.setObject(2,salary.getTax());
        preparedStatement.setObject(3,salary.getETF());
        preparedStatement.setObject(4,salary.getEPF());

        return preparedStatement.executeUpdate() > 0;
    }

    public static String getSalary(int id) throws SQLException {
        String sql = "SELECT salary FROM salary WHERE employee_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            double salary = resultSet.getDouble("salary");
            return String.valueOf(salary);
        }
        return null;
    }
}
