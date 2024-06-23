package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;

import java.sql.SQLException;

public interface TaxRateBO extends SuperBO {

    double getTaxRate() throws SQLException, ClassNotFoundException;

    boolean changeTaxRate(double tax) throws SQLException, ClassNotFoundException;
}
