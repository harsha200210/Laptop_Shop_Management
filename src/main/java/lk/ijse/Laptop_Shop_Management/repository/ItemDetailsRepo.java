package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.ItemDetail;
import lk.ijse.Laptop_Shop_Management.model.tm.OrderItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDetailsRepo {
    public static ObservableList<OrderItemTm> getItems() throws SQLException {
        ObservableList<OrderItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item_detail";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            list.add(new OrderItemTm(resultSet.getString("order_id"),resultSet.getInt("item_id"),resultSet.getInt("qty")));
        }
        return list;
    }

    public static boolean save(List<ItemDetail> itemDetail) throws SQLException {
        for (ItemDetail od : itemDetail) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(ItemDetail i)  {
        String sql = "INSERT INTO item_detail VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setObject(1,i.getItemId());
            preparedStatement.setObject(2,i.getOrderId());
            preparedStatement.setObject(3,i.getQty());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
        return false;
    }

    public static ObservableList<OrderItemTm> getItem(String orderId) throws SQLException {
        ObservableList<OrderItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item_detail WHERE order_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,orderId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(new OrderItemTm(resultSet.getString("order_id"),resultSet.getInt("item_id"),resultSet.getInt("qty")));
        }
        return list;
    }
}
