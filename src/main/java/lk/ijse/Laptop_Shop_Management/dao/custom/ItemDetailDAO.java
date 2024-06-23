package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;
import lk.ijse.Laptop_Shop_Management.tdm.OrderItemTm;

import java.sql.SQLException;
import java.util.List;

public interface ItemDetailDAO extends SuperDAO {

    ObservableList<OrderItemTm> getItems() throws SQLException, ClassNotFoundException;

    boolean save(List<ItemDetail> itemDetail) throws SQLException, ClassNotFoundException;

    boolean save(ItemDetail i) throws SQLException, ClassNotFoundException;

    ObservableList<OrderItemTm> getItem(String orderId) throws SQLException, ClassNotFoundException;
}
