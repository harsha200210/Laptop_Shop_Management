package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;

import java.sql.SQLException;

public interface GenerateQrBO extends SuperBO {
    String getFilePath() throws SQLException, ClassNotFoundException;

    int getCount() throws SQLException, ClassNotFoundException;

    boolean updateFilePath(String directoryPath) throws SQLException, ClassNotFoundException;

    boolean addFilePath(String directoryPath) throws SQLException, ClassNotFoundException;
}
