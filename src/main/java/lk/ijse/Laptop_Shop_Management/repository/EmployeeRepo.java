package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Employee;
import lk.ijse.Laptop_Shop_Management.model.Salary;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;
import lk.ijse.Laptop_Shop_Management.model.tm.EmployeeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepo {

    public static Employee employee;

    public static int employeeCount() throws SQLException {
        String sql = "SELECT status FROM employee";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    public static boolean delete(String nic) throws SQLException {
        String sql = "UPDATE employee SET status = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,"Delete");
        preparedStatement.setObject(2,nic);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee (name, NIC, address, email, tel, user_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,employee.getName());
        preparedStatement.setObject(2,employee.getNic());
        preparedStatement.setObject(3,employee.getAddress());
        preparedStatement.setObject(4,employee.getEmail());
        preparedStatement.setObject(5,employee.getTel());
        preparedStatement.setObject(6,employee.getUser_id());
        preparedStatement.setObject(7,employee.getStatus());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkId(String nic) throws SQLException {
        String sql = "SELECT * FROM employee WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                employee = new Employee(resultSet.getInt("employee_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getInt("user_id"), resultSet.getString("status"));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find employee id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find employee !!!").show();
        }
        return false;
    }

    public static boolean update() throws SQLException {
        String sql = "UPDATE employee SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,employee.getName());
        preparedStatement.setObject(2,employee.getAddress());
        preparedStatement.setObject(3,employee.getEmail());
        preparedStatement.setObject(4,employee.getTel());
        preparedStatement.setObject(5,employee.getNic());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Employee search(String nic) throws SQLException {
        employee = null;

        String sql = "SELECT * FROM employee WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                employee = new Employee(resultSet.getInt("employee_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getInt("user_id"), resultSet.getString("status"));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find employee id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find employee id!!!").show();
        }
        return employee;
    }

    public static ObservableList<EmployeeTm> getEmployee() throws SQLException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        String sql = "SELECT name, NIC, address, email, tel, status, s.salary FROM employee e JOIN salary s on e.employee_id = s.employee_id ORDER BY e.employee_id";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new EmployeeTm(resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getDouble("salary")));
            }
        }
        return obList;
    }

    public static ObservableList<DriverTm> getDeleteEmployee() throws SQLException {
        ObservableList<DriverTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }
        return list;
    }

    public static boolean save(Employee employee, Salary salary) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            if (save(employee)){
                if (checkId(employee.getNic())){
                    if (SalaryRepo.save(salary)){
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

    public static boolean update(Salary salary) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (update()){
                if (SalaryRepo.update(salary)){
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
