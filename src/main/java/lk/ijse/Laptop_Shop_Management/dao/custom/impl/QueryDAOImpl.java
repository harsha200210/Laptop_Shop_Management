package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.QueryDAO;
import lk.ijse.Laptop_Shop_Management.tdm.EmployeeTm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ObservableList<EmployeeTm> getEmployee() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT name, NIC, address, email, tel, status, s.salary FROM employee e JOIN salary s on e.employee_id = s.employee_id ORDER BY e.employee_id");

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new EmployeeTm(resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel"), resultSet.getDouble("salary")));
            }
        }
        return obList;
    }
}
