package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.OrderBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.*;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.dto.*;
import lk.ijse.Laptop_Shop_Management.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    CustomerDAO customerDAO;
    DriverDAO driverDAO;
    ItemDAO itemDAO;
    OrderDAO orderDAO;
    ConfigurationDAO configurationDAO;
    UserDAO userDAO;
    ItemDetailDAO itemDetailDAO;
    DeliveryDAO deliveryDAO;

    public OrderBOImpl() {
        this.customerDAO = (CustomerDAO) DAOFactory.getDAO(DAOFactory.DAOType.CUSTOMER);
        this.driverDAO = (DriverDAO) DAOFactory.getDAO(DAOFactory.DAOType.DRIVER);
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
        this.orderDAO = (OrderDAO) DAOFactory.getDAO(DAOFactory.DAOType.ORDER);
        this.configurationDAO = (ConfigurationDAO) DAOFactory.getDAO(DAOFactory.DAOType.CONFIGURATION);
        this.userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
        this.itemDetailDAO = (ItemDetailDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEMDETAIL);
        this.deliveryDAO = (DeliveryDAO) DAOFactory.getDAO(DAOFactory.DAOType.DELIVERY);
    }
    @Override
    public CustomerDTO getCustomerName(String tel) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.getCustomerName(tel);
        if (customer != null){
            return new CustomerDTO(customer.getId(),customer.getName(),customer.getNic(),customer.getAddress(),customer.getEmail(),customer.getTel(),customer.getStatus());
        }
        return null;
    }

    @Override
    public int getDriverId(String name) throws SQLException, ClassNotFoundException {
        return driverDAO.getDriverId(name);
    }

    @Override
    public ObservableList<String> getDriverName() throws SQLException, ClassNotFoundException {
        return driverDAO.getDriverName();
    }

    @Override
    public boolean updateQty(List<ItemDetailDTO> itemDetailDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetail> list = new ArrayList<>();
        for (ItemDetailDTO itemDetailDTO1 : itemDetailDTO) {
            list.add(new ItemDetail(itemDetailDTO1.getItemId(),itemDetailDTO1.getOrderId(),itemDetailDTO1.getQty()));
        }
        return itemDAO.updateQty(list);
    }

    @Override
    public ItemDTO getItem(int id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(id);
        if (item != null){
            return new ItemDTO(item.getId(),item.getModel(),item.getQty(),item.getPrice(),item.getStatus());
        }
        return null;
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCount();
    }

    @Override
    public boolean save(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        Order order = new Order(orderDTO.getOrderId(),orderDTO.getDate(),orderDTO.getPrice(),orderDTO.getCustomerId(),orderDTO.getUserId());
        return orderDAO.save(order);
    }

    @Override
    public String getFirstOrderId() throws SQLException, ClassNotFoundException {
        return configurationDAO.getFirstOrderId();
    }

    @Override
    public boolean setFirstOrderId(String firstOrderId) throws SQLException, ClassNotFoundException {
        return configurationDAO.setFirstOrderId(firstOrderId);
    }

    @Override
    public double getInsideChange() throws SQLException, ClassNotFoundException {
        return configurationDAO.getInsideChange();
    }

    @Override
    public double getOutSideCharge() throws SQLException, ClassNotFoundException {
        return configurationDAO.getOutSideChage();
    }

    @Override
    public String getOwner() throws SQLException, ClassNotFoundException {
        return userDAO.getOwner();
    }

    @Override
    public boolean save(List<ItemDetailDTO> itemDetailDTOS) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetail> list = new ArrayList<>();
        for (ItemDetailDTO itemDetailDTO : itemDetailDTOS) {
            list.add(new ItemDetail(itemDetailDTO.getItemId(),itemDetailDTO.getOrderId(),itemDetailDTO.getQty()));
        }
        return itemDetailDAO.save(list);
    }

    @Override
    public boolean save(DeliveryDTO deliveryDTO) throws SQLException, ClassNotFoundException {
        Delivery delivery = new Delivery(deliveryDTO.getDeliveryChage(),deliveryDTO.getOrderId(),deliveryDTO.getDriverId());
        return deliveryDAO.save(delivery);
    }

    @Override
    public boolean placeOrder(PlaceOrder placeOrder) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (save(placeOrder.getOrderDTO())){
                if (updateQty(placeOrder.getItemDetailDTO())){
                    if (save(placeOrder.getItemDetailDTO())){
                        if (placeOrder.getDeliveryDTO() != null){
                            if (save(placeOrder.getDeliveryDTO())){
                                connection.commit();
                                return true;
                            }
                        } else {
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e){
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
