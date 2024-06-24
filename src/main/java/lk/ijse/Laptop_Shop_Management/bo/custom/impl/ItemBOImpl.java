package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

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
    public int itemCount() throws SQLException, ClassNotFoundException {
        return itemDAO.count();
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
    public ObservableList<ItemTm> getItem() throws SQLException, ClassNotFoundException {
        return itemDAO.getItem();
    }

    @Override
    public ObservableList<Integer> getItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getItemId();
    }

    @Override
    public ObservableList<Integer> getOrderItem() throws SQLException, ClassNotFoundException {
        return itemDAO.getOrderItem();
    }

    @Override
    public ItemDTO getItem(int id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(id);
        if (item != null){
            return new ItemDTO(item.getId(),item.getModel(),item.getQty(),item.getPrice(),item.getStatus());
        }
        return null;
    }

    @Override
    public ObservableList<ItemTm> getDeleteItem() throws SQLException, ClassNotFoundException {
        return itemDAO.getDeleteItem();
    }

    @Override
    public ObservableList<ItemTm> outOfStokeItem() throws SQLException, ClassNotFoundException {
        return itemDAO.outOfStokeItem();
    }

    @Override
    public boolean updateQty(List<ItemDetailDTO> itemDetailDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDetail> list = new ArrayList<>();
        for (ItemDetailDTO itemDetailDTO1 : itemDetailDTO) {
            list.add(new ItemDetail(itemDetailDTO1.getItemId(),itemDetailDTO1.getOrderId(),itemDetailDTO1.getQty()));
        }
        return itemDAO.updateQty(list);
    }

    @Override
    public boolean updateQty(ItemDetailDTO i) throws SQLException, ClassNotFoundException {
        ItemDetail itemDetail = new ItemDetail(i.getItemId(),i.getOrderId(),i.getQty());
        return itemDAO.updateQty(itemDetail);
    }

    @Override
    public boolean updateSupplierQty(List<ItemSupplierDTO> itemSupplierDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ItemSupplier> list = new ArrayList<>();
        for (ItemSupplierDTO itemSupplierDTO1 : itemSupplierDTO) {
            list.add(new ItemSupplier(itemSupplierDTO1.getItemId(),itemSupplierDTO1.getSupplierId(),itemSupplierDTO1.getDate(),itemSupplierDTO1.getQty(),itemSupplierDTO1.getPrice()));
        }
        return itemDAO.updateSupplierQty(list);
    }

    @Override
    public boolean setQty(ItemSupplierDTO i) throws SQLException, ClassNotFoundException {
        ItemSupplier itemSupplier = new ItemSupplier(i.getItemId(),i.getSupplierId(),i.getDate(),i.getQty(),i.getPrice());
        return itemDAO.setQty(itemSupplier);
    }
}
