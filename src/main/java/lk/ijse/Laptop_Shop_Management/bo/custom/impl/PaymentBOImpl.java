package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.PaymentBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.PaymentDAO;
import lk.ijse.Laptop_Shop_Management.dto.PaymentDTO;
import lk.ijse.Laptop_Shop_Management.entity.Payment;

import java.sql.SQLException;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO;

    public PaymentBOImpl() {
        this.paymentDAO = (PaymentDAO) DAOFactory.getDAO(DAOFactory.DAOType.PAYMENT);
    }
    @Override
    public boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(paymentDTO.getPaymentType(),paymentDTO.getPaymentDate(),paymentDTO.getOrderId());
        return paymentDAO.save(payment);
    }
}
