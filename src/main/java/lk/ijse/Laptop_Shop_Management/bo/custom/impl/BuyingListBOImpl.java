package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.BuyingListBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemSupplierDAO;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;

import java.sql.SQLException;

public class BuyingListBOImpl implements BuyingListBO {
    ItemSupplierDAO itemSupplierDAO;

    public BuyingListBOImpl() {
        this.itemSupplierDAO = (ItemSupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEMSUPPLIER);
    }

    @Override
    public ObservableList<SupplierItemTm> getSupplierItem() throws SQLException, ClassNotFoundException {
        return itemSupplierDAO.getSupplierItem();
    }

    @Override
    public ObservableList<SupplierItemTm> getSupplierItem(int id) throws SQLException, ClassNotFoundException {
        return itemSupplierDAO.getSupplierItem(id);
    }
}
