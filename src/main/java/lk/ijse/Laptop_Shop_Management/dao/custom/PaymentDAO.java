package lk.ijse.Laptop_Shop_Management.dao.custom;

import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Payment;

import java.sql.SQLException;

public interface PaymentDAO extends SuperDAO {

    boolean save(Payment payment) throws SQLException, ClassNotFoundException;
}
