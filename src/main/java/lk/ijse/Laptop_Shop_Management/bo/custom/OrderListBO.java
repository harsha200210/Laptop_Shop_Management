package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.tdm.OrderItemTm;
import lk.ijse.Laptop_Shop_Management.tdm.OrderListTm;

import java.sql.SQLException;

public interface OrderListBO extends SuperBO {

    ObservableList<OrderItemTm> getItems() throws SQLException, ClassNotFoundException;

    ObservableList<OrderItemTm> getItem(String orderId) throws SQLException, ClassNotFoundException;

    ObservableList<OrderListTm> getOrders() throws SQLException, ClassNotFoundException;
}
