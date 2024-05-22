package lk.ijse.Laptop_Shop_Management.repository;

import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.ItemSupplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BuyItemsRepo {
    public static boolean buy(List<ItemSupplier> itemSupplier) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (ItemSupplierRepo.saveItemSupplier(itemSupplier)){
                if (ItemRepo.updateSupplierQty(itemSupplier)){
                    connection.commit();
                    return true;
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
