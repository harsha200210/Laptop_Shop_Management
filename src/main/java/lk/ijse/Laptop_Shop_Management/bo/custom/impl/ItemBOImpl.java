package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.ItemBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDAO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDetailDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.entity.Item;
import lk.ijse.Laptop_Shop_Management.entity.ItemDetail;
import lk.ijse.Laptop_Shop_Management.entity.ItemSupplier;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO;

    public ItemBOImpl() {
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
    }

    @Override
    public boolean delete(String model) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(model);
    }

    @Override
    public boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getId(),itemDTO.getModel(),itemDTO.getQty(),itemDTO.getPrice(),itemDTO.getStatus());
        return itemDAO.save(item);
    }

    @Override
    public boolean checkId(String model) throws SQLException, ClassNotFoundException {
        return itemDAO.checkId(model);
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return itemDAO.update();
    }

    @Override
    public ItemDTO search(String model) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(model);
        if (item != null){
            return new ItemDTO(item.getId(),item.getModel(),item.getQty(),item.getPrice(),item.getStatus());
        }
        return null;
    }

    @Override
    public ObservableList<ItemDTO> getItem() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDTO> list = FXCollections.observableArrayList();
        ObservableList<Item> items = itemDAO.getItem();

        for (Item item : items) {
            list.add(new ItemDTO(item.getId(), item.getModel(), item.getQty(), item.getPrice(), item.getStatus()));
        }
        return list;
    }

    @Override
    public ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemId();
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
