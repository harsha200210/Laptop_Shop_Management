package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.OrderListBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDetailDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.OrderDAO;
import lk.ijse.Laptop_Shop_Management.tdm.OrderItemTm;
import lk.ijse.Laptop_Shop_Management.tdm.OrderListTm;

import java.sql.SQLException;

public class OrderListBOImpl implements OrderListBO {
    ItemDetailDAO itemDetailDAO;
    OrderDAO orderDAO;

    public OrderListBOImpl() {
        this.itemDetailDAO = (ItemDetailDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEMDETAIL);
        this.orderDAO = (OrderDAO) DAOFactory.getDAO(DAOFactory.DAOType.ORDER);
    }

    @Override
    public ObservableList<OrderItemTm> getItems() throws SQLException, ClassNotFoundException {
        return itemDetailDAO.getItems();
    }

    @Override
    public ObservableList<OrderItemTm> getItem(String orderId) throws SQLException, ClassNotFoundException {
        return itemDetailDAO.getItem(orderId);
    }

    @Override
    public ObservableList<OrderListTm> getOrders() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrders();
    }
}
