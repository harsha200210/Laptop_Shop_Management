package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.TaxRateBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ConfigurationDAO;

import java.sql.SQLException;

public class TaxRateBOImpl implements TaxRateBO {
    ConfigurationDAO configurationDAO;

    public TaxRateBOImpl() {
        this.configurationDAO = (ConfigurationDAO) DAOFactory.getDAO(DAOFactory.DAOType.CONFIGURATION);
    }

    @Override
    public double getTaxRate() throws SQLException, ClassNotFoundException {
        return configurationDAO.getTaxRate();
    }

    @Override
    public boolean changeTaxRate(double tax) throws SQLException, ClassNotFoundException {
        return configurationDAO.changeTaxRate(tax);
    }
}
