package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;

import java.sql.SQLException;

public interface BuyingListBO extends SuperBO {

    ObservableList<ItemSupplierDTO> getSupplierItem() throws SQLException, ClassNotFoundException;

    ObservableList<ItemSupplierDTO> getSupplierItem(int id) throws SQLException, ClassNotFoundException;
}
