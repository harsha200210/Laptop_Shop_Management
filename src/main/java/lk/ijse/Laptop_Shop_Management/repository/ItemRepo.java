package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Item;
import lk.ijse.Laptop_Shop_Management.model.ItemDetail;
import lk.ijse.Laptop_Shop_Management.model.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;
import lk.ijse.Laptop_Shop_Management.model.tm.ItemTm;
import lk.ijse.Laptop_Shop_Management.model.tm.SupplierItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemRepo {

    public static Item item;

    public static int itemCount() throws SQLException {
        String sql = "SELECT status FROM item";

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

    public static boolean delete(String model) throws SQLException {
        String sql = "UPDATE item SET status = ? WHERE model = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,"Delete");
        preparedStatement.setObject(2,model);

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item (model, on_hand_qty, price, status) VALUES  (?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,item.getModel());
        preparedStatement.setObject(2,item.getQty());
        preparedStatement.setObject(3,item.getPrice());
        preparedStatement.setObject(4,item.getStatus());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkId(String model) throws SQLException {
        String sql = "SELECT * FROM item WHERE model = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,model);
        ResultSet resultSet = preparedStatement.executeQuery();

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

    public static boolean update() throws SQLException {
        String sql = "UPDATE item SET model = ?, on_hand_qty = ?, price = ? WHERE item_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,item.getModel());
        preparedStatement.setObject(2,item.getQty());
        preparedStatement.setObject(3,item.getPrice());
        preparedStatement.setObject(4,item.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Item search(String model) throws SQLException {
        item = null;

        String sql = "SELECT * FROM item WHERE model = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,model);
        ResultSet resultSet = preparedStatement.executeQuery();

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

    public static ObservableList<ItemTm> getItem() throws SQLException {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString(5).equals("Delete")){
                obList.add(new ItemTm(resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4)));
            }
        }
        return obList;
    }

    public static ObservableList<Integer> getItemId() throws SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        String sql = "SELECT item_id,status FROM item";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")){
                list.add(resultSet.getInt(1));
            }
        }
        return list;
    }

    public static ObservableList<Integer> getOrderItem() throws SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        String sql = "SELECT item_id,status,on_hand_qty FROM item";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString(2).equals("Delete")){
                if (resultSet.getInt(3) != 0){
                    list.add(resultSet.getInt(1));
                }
            }
        }
        return list;
    }

    public static Item getItem(int id) throws SQLException {
        item = null;

        String sql = "SELECT * FROM item WHERE item_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            item = new Item(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5));
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find Item !!!").show();
        }
        return item;
    }

    public static ObservableList<ItemTm> getDeleteItem() throws SQLException {
        ObservableList<ItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new ItemTm(resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4)));
            }
        }
        return list;
    }

    public static ObservableList<ItemTm> outOfStokeItem() throws SQLException {
        ObservableList<ItemTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM item WHERE on_hand_qty = 0";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")) {
                list.add(new ItemTm(resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4)));
            }
        }
        return list;
    }

    public static boolean updateQty(List<ItemDetail> itemDetail) throws SQLException {
        for (ItemDetail i : itemDetail){
            if (!updateQty(i)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(ItemDetail i) throws SQLException {
        String sql = "UPDATE item SET on_hand_qty = on_hand_qty - ? WHERE item_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,i.getQty());
        preparedStatement.setObject(2,i.getItemId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean updateSupplierQty(List<ItemSupplier> itemSupplier) throws SQLException {
        for (ItemSupplier i : itemSupplier){
            if (!setQty(i)){
                return false;
            }
        }
        return true;
    }

    private static boolean setQty(ItemSupplier i) throws SQLException {
        String sql = "UPDATE item SET on_hand_qty = on_hand_qty + ? WHERE item_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,i.getQty());
        preparedStatement.setObject(2,i.getItemId());

        return preparedStatement.executeUpdate() > 0;
    }
}
