package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemSupplierDAO;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemSupplierDAOImpl implements ItemSupplierDAO {
    @Override
    public boolean saveItemSupplier(List<ItemSupplier> itemSupplier) throws SQLException, ClassNotFoundException {
        for (ItemSupplier i : itemSupplier){
            if (!buy(i)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean buy(ItemSupplier i) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item_supplier_detail VALUES (?, ?, ?, ?, ?)",i.getItemId(),i.getSupplierId(),i.getDate(),i.getQty(),i.getPrice());
    }

    @Override
    public ObservableList<SupplierItemTm> getSupplierItem() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierItemTm> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item_supplier_detail");

        while (resultSet.next()){
            list.add(new SupplierItemTm(resultSet.getInt(1),resultSet.getInt(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getDouble(5)));
        }
        return list;
    }

    @Override
    public ObservableList<SupplierItemTm> getSupplierItem(int id) throws SQLException, ClassNotFoundException {
        ObservableList<SupplierItemTm> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item_supplier_detail WHERE item_id = ?",id);

        while (resultSet.next()){
            list.add(new SupplierItemTm(resultSet.getInt(1),resultSet.getInt(2),resultSet.getDate(3),resultSet.getInt(4),resultSet.getDouble(5)));
        }
        return list;
    }
}
