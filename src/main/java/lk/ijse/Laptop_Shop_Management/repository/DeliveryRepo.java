package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryRepo {

    public static boolean changeDeliveryCharge(double inside, double out) throws SQLException {
        String sql = "UPDATE delivery SET inside_colombo = ?, out_of_colombo = ? WHERE delivery_id = (SELECT MAX(delivery_id))";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,inside);
        preparedStatement.setObject(2,out);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(Delivery delivery) throws SQLException {
        String sql = "INSERT INTO delivery (delivery_charge,order_id,driver_id) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,delivery.getDeliveryChage());
        preparedStatement.setObject(2,delivery.getOrderId());
        preparedStatement.setObject(3,delivery.getDriverId());

        return preparedStatement.executeUpdate() > 0;
    }

}
