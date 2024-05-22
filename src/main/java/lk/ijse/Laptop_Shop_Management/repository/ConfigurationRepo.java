package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigurationRepo {
    public static double getInsideChange() throws SQLException {
        String sql = "SELECT inside_colombo FROM configurations WHERE configuration_id = 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("inside_colombo");
        }
        return 0;
    }

    public static double getOutSideChage() throws SQLException {
        String sql = "SELECT out_of_colombo FROM configurations WHERE configuration_id = 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public static int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM configurations ORDER BY configuration_id";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static boolean changeDeliveryCharge(double inside, double out) throws SQLException {
        if (getCount() != 0){
            String sql = "UPDATE configurations SET inside_colombo = ? , out_of_colombo = ? WHERE configuration_id = 1";

            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setObject(1,inside);
            preparedStatement.setObject(2,out);

            return preparedStatement.executeUpdate() > 0;
        } else {
            String sql = "INSERT INTO configurations (inside_colombo, out_of_colombo) VALUES (?, ?)";

            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, inside);
            preparedStatement.setDouble(2, out);

            return preparedStatement.executeUpdate() > 0;
        }

    }

    public static double getTaxRate() throws SQLException {
        String sql = "SELECT tax_rate FROM configurations WHERE configuration_id = 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }

    public static boolean changeTaxRate(double tax) throws SQLException {
        String sql = "UPDATE configurations SET tax_rate = ? WHERE configuration_id = 1";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,tax);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean setFirstOrderId(String firstOrderId) throws SQLException {
        String sql = "UPDATE configurations SET first_order_id = ? WHERE configuration_id = 1";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,firstOrderId);

        return preparedStatement.executeUpdate() > 0;
    }

    public static String getFirstOrderId() throws SQLException {
        String sql = "SELECT first_order_id FROM configurations WHERE configuration_id = 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean addFilePath(String directoryPath) throws SQLException {
        String sql = "INSERT INTO configurations (file_path) VALUES (?)";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1,directoryPath);
        return statement.executeUpdate() > 0;
    }

    public static boolean updateFilePath(String directoryPath) throws SQLException {
        String sql = "UPDATE configurations SET file_path = ? WHERE configuration_id = 1";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1,directoryPath);
        return statement.executeUpdate() > 0;
    }

    public static String getFilePath() throws SQLException {
        String sql = "SELECT file_path FROM configurations WHERE configuration_id = 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
