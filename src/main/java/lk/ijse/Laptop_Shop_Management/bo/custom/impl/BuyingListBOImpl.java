package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.BuyingListBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemSupplierDAO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;

import java.sql.SQLException;

public class BuyingListBOImpl implements BuyingListBO {
    ItemSupplierDAO itemSupplierDAO;

    public BuyingListBOImpl() {
        this.itemSupplierDAO = (ItemSupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEMSUPPLIER);
    }

    @Override
    public ObservableList<ItemSupplierDTO> getSupplierItem() throws SQLException, ClassNotFoundException {
        ObservableList<ItemSupplierDTO> list = FXCollections.observableArrayList();
        ObservableList<ItemSupplier> supplierItem = itemSupplierDAO.getSupplierItem();

        for (ItemSupplier itemSupplier : supplierItem) {
            list.add(new ItemSupplierDTO(itemSupplier.getItemId(),itemSupplier.getSupplierId(),itemSupplier.getDate(),itemSupplier.getQty(),itemSupplier.getPrice()));
        }
        return list;
    }

    @Override
    public ObservableList<ItemSupplierDTO> getSupplierItem(int id) throws SQLException, ClassNotFoundException {
        ObservableList<ItemSupplierDTO> list = FXCollections.observableArrayList();
        ObservableList<ItemSupplier> supplierItem = itemSupplierDAO.getSupplierItem(id);

        for (ItemSupplier itemSupplier : supplierItem) {
            list.add(new ItemSupplierDTO(itemSupplier.getItemId(),itemSupplier.getSupplierId(),itemSupplier.getDate(),itemSupplier.getQty(),itemSupplier.getPrice()));
        }
        return list;
    }
}
