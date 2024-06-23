package lk.ijse.Laptop_Shop_Management.dao.custom;

import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Delivery;

import java.sql.SQLException;

public interface DeliveryDAO extends SuperDAO {

    boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException;

    boolean save(Delivery delivery) throws SQLException, ClassNotFoundException;
}
