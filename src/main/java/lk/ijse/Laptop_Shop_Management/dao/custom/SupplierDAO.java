package lk.ijse.Laptop_Shop_Management.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.dao.CrudDAO;
import lk.ijse.Laptop_Shop_Management.dao.SuperDAO;
import lk.ijse.Laptop_Shop_Management.entity.Supplier;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierTm;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> , SuperDAO {
    ObservableList<SupplierTm> getSupplier() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getSupplierID() throws SQLException, ClassNotFoundException;

    ObservableList<DriverTm> getDeleteSupplier() throws SQLException, ClassNotFoundException;

    Supplier searchSupplier(int tel) throws SQLException, ClassNotFoundException;
}
