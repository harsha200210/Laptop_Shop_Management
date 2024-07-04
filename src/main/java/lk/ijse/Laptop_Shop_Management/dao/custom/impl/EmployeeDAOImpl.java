package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.EmployeeDAO;
import lk.ijse.Laptop_Shop_Management.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO {
    public static Employee employee;

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT status FROM employee");

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET status = ? WHERE NIC = ?","Delete",nic);
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee (name, NIC, address, email, tel, user_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)", employee.getName(), employee.getNic(), employee.getAddress(), employee.getEmail(), employee.getTel(), employee.getUser_id(), employee.getStatus());
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE NIC = ?",nic);

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

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?", employee.getName(), employee.getAddress(), employee.getEmail(), employee.getTel(), employee.getNic());
    }

    @Override
    public Employee search(String nic) throws SQLException, ClassNotFoundException {
        employee = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE NIC = ?",nic);

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

    @Override
    public ObservableList<Employee> getDeleteEmployee() throws SQLException, ClassNotFoundException {
        ObservableList<Employee> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new Employee(resultSet.getInt("employee_id"),resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getInt("user_id"), resultSet.getString("status")));
            }
        }
        return list;
    }
}
