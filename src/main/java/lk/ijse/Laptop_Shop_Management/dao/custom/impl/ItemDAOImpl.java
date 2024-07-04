package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDAO;
import lk.ijse.Laptop_Shop_Management.entity.Item;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    public static Item item;

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT status FROM item");

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean delete(String model) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET status = ? WHERE model = ?","Delete",model);
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item (model, on_hand_qty, price, status) VALUES  (?, ?, ?, ?)", item.getModel(), item.getQty(), item.getPrice(), item.getStatus());
    }

    @Override
    public boolean checkId(String model) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item WHERE model = ?",model);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                item = new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find Item id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find Item id!!!").show();
        }
        return false;
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET model = ?, on_hand_qty = ?, price = ? WHERE item_id = ?", item.getModel(), item.getQty(), item.getPrice(), item.getId());
    }

    @Override
    public Item search(String model) throws SQLException, ClassNotFoundException {
        item = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item WHERE model = ?",model);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                item = new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find Item !!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find item !!!").show();
        }
        return item;
    }

    @Override
    public ObservableList<Item> getItem() throws SQLException, ClassNotFoundException {
        ObservableList<Item> obList = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item");

        while (resultSet.next()){
            if (!resultSet.getString(5).equals("Delete")){
                obList.add(new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4),resultSet.getString(5)));
            }
        }
        return obList;
    }

    @Override
    public ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT item_id,status FROM item");

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")){
                list.add(resultSet.getInt(1));
            }
        }
        return list;
    }

    @Override
    public Item getItem(int id) throws SQLException, ClassNotFoundException {
        item = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item WHERE item_id = ?",id);

        if (resultSet.next()){
            item = new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5));
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find Item !!!").show();
        }
        return item;
    }

    @Override
    public ObservableList<Item> getDeleteItem() throws SQLException, ClassNotFoundException {
        ObservableList<Item> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item");

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5)));
            }
        }
        return list;
    }

    @Override
    public ObservableList<Item> outOfStokeItem() throws SQLException, ClassNotFoundException {
        ObservableList<Item> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item WHERE on_hand_qty = 0");

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")) {
                list.add(new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5)));
            }
        }
        return list;
    }

    @Override
    public boolean updateQty(List<ItemDetail> itemDetail) throws SQLException, ClassNotFoundException {
        for (ItemDetail i : itemDetail){
            if (!updateQty(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateQty(ItemDetail i) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET on_hand_qty = on_hand_qty - ? WHERE item_id = ?",i.getQty(),i.getItemId());
    }

    @Override
    public boolean updateSupplierQty(List<ItemSupplier> itemSupplier) throws SQLException, ClassNotFoundException {
        for (ItemSupplier i : itemSupplier){
            if (!setQty(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean setQty(ItemSupplier i) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET on_hand_qty = on_hand_qty + ? WHERE item_id = ?",i.getQty(),i.getItemId());
    }
}
