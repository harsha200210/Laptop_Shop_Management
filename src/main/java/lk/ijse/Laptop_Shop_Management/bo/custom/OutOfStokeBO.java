package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;

public interface OutOfStokeBO extends SuperBO {

    ObservableList<ItemDTO> outOfStokeItem() throws SQLException, ClassNotFoundException;
}
