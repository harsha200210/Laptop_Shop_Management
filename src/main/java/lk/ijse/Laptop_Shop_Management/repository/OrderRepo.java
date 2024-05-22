package lk.ijse.Laptop_Shop_Management.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Order;
import lk.ijse.Laptop_Shop_Management.model.tm.OrderItemTm;
import lk.ijse.Laptop_Shop_Management.model.tm.OrderListTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {

    public static int getOrderCount() throws SQLException {
        String sql = "SELECT count(*) FROM orders ORDER BY order_id";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static ObservableList<OrderListTm> getOrders() throws SQLException {
        ObservableList<OrderListTm> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        while (resultSet.next()){
            list.add(new OrderListTm(resultSet.getString("order_id"),resultSet.getInt("customer_id"),resultSet.getDate("date"),resultSet.getDouble("total_amount"),resultSet.getInt("user_id")));
        }
        return list;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,order.getOrderId());
        preparedStatement.setObject(2,order.getDate());
        preparedStatement.setObject(3,order.getPrice());
        preparedStatement.setObject(4,order.getCustomerId());
        preparedStatement.setObject(5,order.getUserId());

        return preparedStatement.executeUpdate() > 0;
    }
}
