package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.DeliveryDAO;
import lk.ijse.Laptop_Shop_Management.entity.Delivery;

import java.sql.SQLException;

public class DeliveryDAOImpl implements DeliveryDAO {
    @Override
    public boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE delivery SET inside_colombo = ?, out_of_colombo = ? WHERE delivery_id = (SELECT MAX(delivery_id))",inside,out);
    }

    @Override
    public boolean save(Delivery delivery) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO delivery (delivery_charge,order_id,driver_id) VALUES (?, ?, ?)",delivery.getDeliveryCharge(),delivery.getOrderId(),delivery.getDriverId());
    }
}
