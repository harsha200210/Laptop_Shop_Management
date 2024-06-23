package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;

import java.sql.SQLException;

public interface DeliverySettingBO extends SuperBO {
    double getInsideChange() throws SQLException, ClassNotFoundException;
    double getOutSideChage() throws SQLException, ClassNotFoundException;
    boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException;
}
