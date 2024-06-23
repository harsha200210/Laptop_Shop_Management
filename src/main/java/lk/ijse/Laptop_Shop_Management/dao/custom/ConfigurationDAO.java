package lk.ijse.Laptop_Shop_Management.dao.custom;

import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;

import java.sql.SQLException;

public interface ConfigurationDAO extends SuperDAO {
     double getInsideChange() throws SQLException, ClassNotFoundException;

     double getOutSideChage() throws SQLException, ClassNotFoundException;

     int getCount() throws SQLException, ClassNotFoundException;

     boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException;

     double getTaxRate() throws SQLException, ClassNotFoundException;

     boolean changeTaxRate(double tax) throws SQLException, ClassNotFoundException;

     boolean setFirstOrderId(String firstOrderId) throws SQLException, ClassNotFoundException;

     String getFirstOrderId() throws SQLException, ClassNotFoundException;

     boolean addFilePath(String directoryPath) throws SQLException, ClassNotFoundException;

     boolean updateFilePath(String directoryPath) throws SQLException, ClassNotFoundException;

     String getFilePath() throws SQLException, ClassNotFoundException;
}
