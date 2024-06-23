package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.bo.custom.BuyingBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemSupplierDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.SupplierDAO;
import lk.ijse.Laptop_Shop_Management.dao.custom.UserDAO;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;
import lk.ijse.Laptop_Shop_Management.entity.Item;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.entity.Supplier;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyingBOImpl implements BuyingBO {
    ItemDAO itemDAO;
    SupplierDAO supplierDAO;
    ItemSupplierDAO itemSupplierDAO;
    UserDAO userDAO;

    public BuyingBOImpl() {
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
        this.supplierDAO = (SupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.SUPPLIER);
        this.itemSupplierDAO = (ItemSupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEMSUPPLIER);
        this.userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    }

    @Override
    public boolean saveItemSupplier(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ItemSupplier> itemSuppliers = new ArrayList<>();
        for (ItemSupplierDTO item : itemSupplierDTO) {
            itemSuppliers.add(new ItemSupplier(item.getItemId(),item.getSupplierId(),item.getDate(),item.getQty(),item.getPrice()));
        }
        return itemSupplierDAO.saveItemSupplier(itemSuppliers);
    }

    @Override
    public SupplierDTO searchSupplier(int tel) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.searchSupplier(tel);
        return new SupplierDTO(supplier.getId(),supplier.getName(),supplier.getNic(),supplier.getAddress(),supplier.getEmail(),supplier.getTel(),supplier.getStatus());
    }

    @Override
    public boolean updateSupplierQty(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ItemSupplier> itemSuppliers = new ArrayList<>();
        for (ItemSupplierDTO item : itemSupplierDTO) {
            itemSuppliers.add(new ItemSupplier(item.getItemId(),item.getSupplierId(),item.getDate(),item.getQty(),item.getPrice()));
        }
        return itemDAO.updateSupplierQty(itemSuppliers);
    }

    @Override
    public String getOwner() throws SQLException, ClassNotFoundException {
        return userDAO.getOwner();
    }

    @Override
    public boolean buy(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            if (saveItemSupplier(itemSupplierDTO)){
                if (updateSupplierQty(itemSupplierDTO)){
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

    @Override
    public ItemDTO getItem(int id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(id);
        if (item != null){
            return new ItemDTO(item.getId(),item.getModel(),item.getQty(),item.getPrice(),item.getStatus());
        }
        return null;
    }
}
