package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.OrderListBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDetailDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.OrderDAO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDetailDTO;
import lk.ijse.Laptop_Shop_Management.dto.OrderDTO;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;
import lk.ijse.Laptop_Shop_Management.entity.Order;
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
    public ObservableList<ItemDetailDTO> getItems() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDetailDTO> list = FXCollections.observableArrayList();
        ObservableList<ItemDetail> items = itemDetailDAO.getItems();

        for (ItemDetail itemDetail : items) {
            list.add(new ItemDetailDTO(itemDetail.getItemId(),itemDetail.getOrderId(),itemDetail.getQty()));
        }
        return list;
    }

    @Override
    public ObservableList<ItemDetailDTO> getItem(String orderId) throws SQLException, ClassNotFoundException {
        ObservableList<ItemDetailDTO> list = FXCollections.observableArrayList();
        ObservableList<ItemDetail> items = itemDetailDAO.getItem(orderId);

        for (ItemDetail itemDetail : items) {
            list.add(new ItemDetailDTO(itemDetail.getItemId(),itemDetail.getOrderId(),itemDetail.getQty()));
        }
        return list;
    }

    @Override
    public ObservableList<OrderDTO> getOrders() throws SQLException, ClassNotFoundException {
        ObservableList<OrderDTO> list = FXCollections.observableArrayList();
        ObservableList<Order> orders = orderDAO.getOrders();

        for (Order order : orders) {
            list.add(new OrderDTO(order.getOrderId(),order.getDate(),order.getPrice(),order.getCustomerId(),order.getUserId()));
        }
        return list;
    }
}
