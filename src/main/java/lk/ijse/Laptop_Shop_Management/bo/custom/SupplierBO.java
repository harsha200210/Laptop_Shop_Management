package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierTm;

import java.sql.SQLException;

public interface SupplierBO extends SuperBO {
    int supplierCount() throws SQLException, ClassNotFoundException;

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String nic) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    SupplierDTO search(String nic) throws SQLException, ClassNotFoundException;

    ObservableList<SupplierTm> getSupplier() throws SQLException, ClassNotFoundException;

    ObservableList<Integer> getSupplierID() throws SQLException, ClassNotFoundException;

    ObservableList<DriverTm> getDeleteSupplier() throws SQLException, ClassNotFoundException;

    SupplierDTO searchSupplier(int tel) throws SQLException, ClassNotFoundException;
}
