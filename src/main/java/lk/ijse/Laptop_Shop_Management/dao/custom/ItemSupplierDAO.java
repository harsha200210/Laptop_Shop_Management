package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemSupplierDAO extends SuperDAO {
    boolean saveItemSupplier(List<ItemSupplier> itemSupplier) throws SQLException, ClassNotFoundException;
    boolean buy(ItemSupplier i) throws SQLException, ClassNotFoundException;
    ObservableList<SupplierItemTm> getSupplierItem() throws SQLException, ClassNotFoundException;
    ObservableList<SupplierItemTm> getSupplierItem(int id) throws SQLException, ClassNotFoundException;
}
