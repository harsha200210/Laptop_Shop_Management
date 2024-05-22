package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.model.tm.SupplierItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ItemSupplierRepo {
    public static ObservableList<SupplierItemTm> getSupplierItem() throws SQLException {
        ObservableList<SupplierItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item_supplier_detail";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            list.add(new SupplierItemTm(resultSet.getInt(1),resultSet.getInt(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getDouble(5)));
        }
        return list;
    }

    public static boolean saveItemSupplier(List<ItemSupplier> itemSupplier) throws SQLException {
        for (ItemSupplier i : itemSupplier){
            if (!buy(i)){
                return false;
            }
        }
        return true;
    }

    private static boolean buy(ItemSupplier i) throws SQLException {
        String sql = "INSERT INTO item_supplier_detail VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,i.getItemId());
        preparedStatement.setObject(2,i.getSupplierId());
        preparedStatement.setObject(3,i.getDate());
        preparedStatement.setObject(4,i.getQty());
        preparedStatement.setObject(5,i.getPrice());

        return preparedStatement.executeUpdate() > 0;
    }

    public static ObservableList<SupplierItemTm> getSupplierItem(int id) throws SQLException {
        ObservableList<SupplierItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item_supplier_detail WHERE item_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(new SupplierItemTm(resultSet.getInt(1),resultSet.getInt(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getDouble(5)));
        }
        return list;
    }
}
