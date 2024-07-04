package lk.ijse.Laptop_Shop_Management.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;

import java.sql.SQLException;

public interface SupplierBO extends SuperBO {

    boolean delete(String nic) throws SQLException, ClassNotFoundException;

    boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

    boolean checkId(String nic) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    SupplierDTO search(String nic) throws SQLException, ClassNotFoundException;

    ObservableList<SupplierDTO> getSupplier() throws SQLException, ClassNotFoundException;

}
