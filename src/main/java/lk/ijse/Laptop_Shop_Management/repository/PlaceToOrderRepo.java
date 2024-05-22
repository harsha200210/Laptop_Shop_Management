package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceToOrderRepo {
    public static boolean placeOrder(PlaceOrder placeOrder) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (OrderRepo.save(placeOrder.getOrder())){
                if (ItemRepo.updateQty(placeOrder.getItemDetail())){
                    if (ItemDetailsRepo.save(placeOrder.getItemDetail())){
                        if (placeOrder.getDelivery() != null){
                            if (DeliveryRepo.save(placeOrder.getDelivery())){
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
