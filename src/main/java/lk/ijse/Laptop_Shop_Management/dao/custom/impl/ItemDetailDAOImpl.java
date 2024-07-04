package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDetailDAO;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDetailDAOImpl implements ItemDetailDAO {
    @Override
    public ObservableList<ItemDetail> getItems() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDetail> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item_detail");

        while (resultSet.next()){
            list.add(new ItemDetail(resultSet.getInt("item_id"),resultSet.getString("order_id"),resultSet.getInt("qty")));
        }
        return list;
    }

    @Override
    public boolean save(List<ItemDetail> itemDetail) throws SQLException, ClassNotFoundException {
        for (ItemDetail od : itemDetail) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(ItemDetail i) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item_detail VALUES (?, ?, ?)",i.getItemId(),i.getOrderId(),i.getQty());
    }

    @Override
    public ObservableList<ItemDetail> getItem(String orderId) throws SQLException, ClassNotFoundException {
        ObservableList<ItemDetail> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item_detail WHERE order_id = ?",orderId);

        while (resultSet.next()){
            list.add(new ItemDetail(resultSet.getInt("item_id"),resultSet.getString("order_id"),resultSet.getInt("qty")));
        }
        return list;
    }
}
