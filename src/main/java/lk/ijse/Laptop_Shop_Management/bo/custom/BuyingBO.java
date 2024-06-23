package lk.ijse.Laptop_Shop_Management.bo.custom;

import lk.ijse.Laptop_Shop_Management.bo.SuperBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface BuyingBO extends SuperBO {

    boolean saveItemSupplier(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException;

    SupplierDTO searchSupplier(int tel) throws SQLException, ClassNotFoundException;

    boolean updateSupplierQty(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException;

    boolean buy(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException;

    ItemDTO getItem(int id) throws SQLException, ClassNotFoundException;

    String getOwner() throws SQLException, ClassNotFoundException;
}
