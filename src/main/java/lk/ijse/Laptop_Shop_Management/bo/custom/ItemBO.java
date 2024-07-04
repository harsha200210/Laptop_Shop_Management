package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;


import java.sql.SQLException;

public interface ItemBO extends SuperBO {

    boolean delete(String model) throws SQLException, ClassNotFoundException;

    boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String model) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    ItemDTO search(String model) throws SQLException, ClassNotFoundException;

    ObservableList<ItemDTO> getItem() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException;

    ItemDTO getItem(int id) throws SQLException, ClassNotFoundException;

}
