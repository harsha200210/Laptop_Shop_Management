package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Item;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item> , SuperDAO {
    ObservableList<Item> getItem() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException;

    Item getItem(int id) throws SQLException, ClassNotFoundException;

    ObservableList<Item> getDeleteItem() throws SQLException, ClassNotFoundException;

    ObservableList<Item> outOfStokeItem() throws SQLException, ClassNotFoundException;

    boolean updateQty(List<ItemDetail> itemDetail) throws SQLException, ClassNotFoundException;

    boolean updateQty(ItemDetail i) throws SQLException, ClassNotFoundException;

    boolean updateSupplierQty(List<ItemSupplier> itemSupplier) throws SQLException, ClassNotFoundException;

    boolean setQty(ItemSupplier i) throws SQLException, ClassNotFoundException;
}
