package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;

import java.sql.SQLException;

public interface BuyingListBO extends SuperBO {
    ObservableList<SupplierItemTm> getSupplierItem() throws SQLException, ClassNotFoundException;
    ObservableList<SupplierItemTm> getSupplierItem(int id) throws SQLException, ClassNotFoundException;
}
