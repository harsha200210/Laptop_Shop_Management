package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.OrderDAO;
import lk.ijse.Laptop_Shop_Management.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(*) FROM orders ORDER BY order_id");
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public ObservableList<Order> getOrders() throws SQLException, ClassNotFoundException {
        ObservableList<Order> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM orders");

        while (resultSet.next()){
            list.add(new Order(resultSet.getString("order_id"),resultSet.getDate("date"),resultSet.getDouble("total_amount"),resultSet.getInt("customer_id"),resultSet.getInt("user_id")));
        }
        return list;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?, ?, ?, ?, ?)",order.getOrderId(),order.getDate(),order.getPrice(),order.getCustomerId(),order.getUserId());
    }
}
