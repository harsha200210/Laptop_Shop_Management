package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Supplier;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;
import lk.ijse.Laptop_Shop_Management.model.tm.SupplierTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRepo {

    public static Supplier supplier;

    public static int suppierCount() throws SQLException {
        String sql = "SELECT status FROM supplier";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    public static boolean delete(String nic) throws SQLException {
        String sql = "UPDATE supplier SET status = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,"Delete");
        preparedStatement.setObject(2,nic);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier (name, NIC, address, email, tel, status) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,supplier.getName());
        preparedStatement.setObject(2,supplier.getNic());
        preparedStatement.setObject(3,supplier.getAddress());
        preparedStatement.setObject(4,supplier.getEmail());
        preparedStatement.setObject(5,supplier.getTel());
        preparedStatement.setObject(6,supplier.getStatus());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkId(String nic) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return false;
    }

    public static boolean update() throws SQLException {
        String sql = "UPDATE supplier SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,supplier.getName());
        preparedStatement.setObject(2,supplier.getAddress());
        preparedStatement.setObject(3,supplier.getEmail());
        preparedStatement.setObject(4,supplier.getTel());
        preparedStatement.setObject(5,supplier.getNic());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Supplier search(String nic) throws SQLException {
        supplier = null;

        String sql = "SELECT * FROM supplier WHERE NIC = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,nic);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return supplier;
    }

    public static ObservableList<SupplierTm> getSupplier() throws SQLException {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM supplier";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new SupplierTm(resultSet.getString("name"),resultSet.getString("NIC"),resultSet.getString("address"),resultSet.getString("email"),resultSet.getInt("tel")));
            }
        }
        return obList;
    }

    public static ObservableList<Integer> getSupplierID() throws SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        String sql = "SELECT supplier_id,status FROM supplier";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")){
                list.add(resultSet.getInt(1));
            }
        }
        return list;
    }

    public static ObservableList<DriverTm> getDeleteSupplier() throws SQLException {
        ObservableList<DriverTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM supplier";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new DriverTm(resultSet.getString("name"), resultSet.getString("NIC"), resultSet.getString("address"), resultSet.getString("email"), resultSet.getInt("tel")));
            }
        }
        return list;
    }

    public static Supplier searchSupplier(int tel) throws SQLException {
        supplier = null;

        String sql = "SELECT * FROM supplier WHERE tel = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,tel);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return supplier;
    }
}
