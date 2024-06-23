package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.DeliverySettingBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ConfigurationDAO;

import java.sql.SQLException;

public class DeliverySettingBOImpl implements DeliverySettingBO {
    ConfigurationDAO configurationDAO;

    public DeliverySettingBOImpl() {
        this.configurationDAO = (ConfigurationDAO) DAOFactory.getDAO(DAOFactory.DAOType.CONFIGURATION);
    }

    @Override
    public double getInsideChange() throws SQLException, ClassNotFoundException {
        return configurationDAO.getInsideChange();
    }

    @Override
    public double getOutSideChage() throws SQLException, ClassNotFoundException {
        return configurationDAO.getOutSideChage();
    }

    @Override
    public boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException {
        return configurationDAO.changeDeliveryCharge(inside, out);
    }
}
