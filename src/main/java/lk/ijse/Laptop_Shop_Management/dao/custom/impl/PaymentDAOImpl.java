package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.PaymentDAO;
import lk.ijse.Laptop_Shop_Management.entity.Payment;

import java.sql.SQLException;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean save(Payment payment) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment (payment_type, date, order_id) VALUES (?, ?, ?)",payment.getPaymentType(),payment.getPaymentDate(),payment.getOrderId());
    }
}
