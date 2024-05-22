package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Payment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (payment_type, date, order_id) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,payment.getPaymentType());
        preparedStatement.setObject(2,payment.getPaymentDate());
        preparedStatement.setObject(3,payment.getOrderId());

        return  preparedStatement.executeUpdate() > 0;
    }

}
