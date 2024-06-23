package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDetailDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    int itemCount() throws SQLException, ClassNotFoundException;

    boolean delete(String model) throws SQLException, ClassNotFoundException;

    boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String model) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    ItemDTO search(String model) throws SQLException, ClassNotFoundException;

    ObservableList<ItemTm> getItem() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getOrderItem() throws SQLException, ClassNotFoundException;

    ItemDTO getItem(int id) throws SQLException, ClassNotFoundException;

    ObservableList<ItemTm> getDeleteItem() throws SQLException, ClassNotFoundException;

    ObservableList<ItemTm> outOfStokeItem() throws SQLException, ClassNotFoundException;

    boolean updateQty(List<ItemDetailDTO> itemDetailDTO) throws SQLException, ClassNotFoundException;

    boolean updateQty(ItemDetailDTO i) throws SQLException, ClassNotFoundException;

    boolean updateSupplierQty(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException;

    boolean setQty(ItemSupplierDTO i) throws SQLException, ClassNotFoundException;
}
