package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.PaymentDTO;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {

    boolean save(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
}
