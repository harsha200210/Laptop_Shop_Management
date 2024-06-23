package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ConfigurationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurationDAOImpl implements ConfigurationDAO {
    @Override
    public double getInsideChange() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT inside_colombo FROM configurations WHERE configuration_id = 1");

        if (resultSet.next()) {
            return resultSet.getDouble("inside_colombo");
        }
        return 0;
    }

    @Override
    public double getOutSideChage() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT out_of_colombo FROM configurations WHERE configuration_id = 1");

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM configurations ORDER BY configuration_id");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public boolean changeDeliveryCharge(double inside, double out) throws SQLException, ClassNotFoundException {
        if (getCount() != 0){
            return SQLUtil.execute("UPDATE configurations SET inside_colombo = ? , out_of_colombo = ? WHERE configuration_id = 1",inside,out);
        } else {
            return SQLUtil.execute("INSERT INTO configurations (inside_colombo, out_of_colombo) VALUES (?, ?)",inside,out);
        }

    }

    @Override
    public double getTaxRate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT tax_rate FROM configurations WHERE configuration_id = 1");

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    @Override
    public boolean changeTaxRate(double tax) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE configurations SET tax_rate = ? WHERE configuration_id = 1");
    }

    @Override
    public boolean setFirstOrderId(String firstOrderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE configurations SET first_order_id = ? WHERE configuration_id = 1");
    }

    @Override
    public String getFirstOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT first_order_id FROM configurations WHERE configuration_id = 1");

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean addFilePath(String directoryPath) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO configurations (file_path) VALUES (?)",directoryPath);
    }

    @Override
    public boolean updateFilePath(String directoryPath) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE configurations SET file_path = ? WHERE configuration_id = 1",directoryPath);
    }

    @Override
    public String getFilePath() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT file_path FROM configurations WHERE configuration_id = 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
