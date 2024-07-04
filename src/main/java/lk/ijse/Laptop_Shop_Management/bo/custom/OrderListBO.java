package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDetailDTO;
import lk.ijse.Laptop_Shop_Management.dto.OrderDTO;
import lk.ijse.Laptop_Shop_Management.tdm.OrderItemTm;
import lk.ijse.Laptop_Shop_Management.tdm.OrderListTm;

import java.sql.SQLException;

public interface OrderListBO extends SuperBO {

    ObservableList<ItemDetailDTO> getItems() throws SQLException, ClassNotFoundException;

    ObservableList<ItemDetailDTO> getItem(String orderId) throws SQLException, ClassNotFoundException;

    ObservableList<OrderDTO> getOrders() throws SQLException, ClassNotFoundException;
}
