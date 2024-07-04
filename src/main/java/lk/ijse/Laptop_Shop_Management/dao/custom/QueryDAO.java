package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.tdm.EmployeeTm;

import java.sql.SQLException;

public interface QueryDAO extends SuperDAO {

    ObservableList<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException;
}
