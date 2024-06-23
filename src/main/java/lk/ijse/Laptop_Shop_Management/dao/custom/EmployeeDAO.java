package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Employee;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> , SuperDAO {
    ObservableList<DriverTm> getDeleteEmployee() throws SQLException, ClassNotFoundException;
}
