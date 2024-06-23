package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Order;
import lk.ijse.Laptop_Shop_Management.tdm.OrderListTm;

import java.sql.SQLException;

public interface OrderDAO extends SuperDAO {

    int getOrderCount() throws SQLException, ClassNotFoundException;

    ObservableList<OrderListTm> getOrders() throws SQLException, ClassNotFoundException;

    boolean save(Order order) throws SQLException, ClassNotFoundException;
}
