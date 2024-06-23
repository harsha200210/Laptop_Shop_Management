package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.GenerateQrBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ConfigurationDAO;

import java.sql.SQLException;

public class GenerateQrBOImpl implements GenerateQrBO {
    ConfigurationDAO configurationDAO;

    public GenerateQrBOImpl() {
        this.configurationDAO = (ConfigurationDAO) DAOFactory.getDAO(DAOFactory.DAOType.CONFIGURATION);
    }

    @Override
    public String getFilePath() throws SQLException, ClassNotFoundException {
        return configurationDAO.getFilePath();
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return configurationDAO.getCount();
    }

    @Override
    public boolean updateFilePath(String directoryPath) throws SQLException, ClassNotFoundException {
        return configurationDAO.updateFilePath(directoryPath);
    }

    @Override
    public boolean addFilePath(String directoryPath) throws SQLException, ClassNotFoundException {
        return configurationDAO.addFilePath(directoryPath);
    }
}
