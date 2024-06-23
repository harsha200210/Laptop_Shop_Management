package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.*;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {

    CustomerDTO getCustomerName(String tel) throws SQLException, ClassNotFoundException;

    int getDriverId(String name) throws SQLException, ClassNotFoundException;

    ObservableList<String> getDriverName() throws SQLException, ClassNotFoundException;

    boolean updateQty(List<ItemDetailDTO> itemDetailDTO) throws SQLException, ClassNotFoundException;

    ItemDTO getItem(int id) throws SQLException, ClassNotFoundException;

    int getOrderCount() throws SQLException, ClassNotFoundException;

    boolean save(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    String getFirstOrderId() throws SQLException, ClassNotFoundException;

    boolean setFirstOrderId(String firstOrderId) throws SQLException, ClassNotFoundException;

    double getInsideChange() throws SQLException, ClassNotFoundException;

    double getOutSideCharge() throws SQLException, ClassNotFoundException;

    String getOwner() throws SQLException, ClassNotFoundException;

    boolean save(List<ItemDetailDTO> itemDetailDTOS) throws SQLException, ClassNotFoundException;

    boolean save(DeliveryDTO deliveryDTO) throws SQLException, ClassNotFoundException;

    boolean placeOrder(PlaceOrder placeOrder) throws SQLException;
}
