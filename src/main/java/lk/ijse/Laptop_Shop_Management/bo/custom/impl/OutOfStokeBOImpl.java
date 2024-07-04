package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.OutOfStokeBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDAO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.entity.Item;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;

public class OutOfStokeBOImpl implements OutOfStokeBO {
    ItemDAO itemDAO;

    public OutOfStokeBOImpl() {
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
    }

    @Override
    public ObservableList<ItemDTO> outOfStokeItem() throws SQLException, ClassNotFoundException {
        ObservableList<ItemDTO> list = FXCollections.observableArrayList();
        ObservableList<Item> items = itemDAO.outOfStokeItem();

        for (Item item : items) {
            list.add(new ItemDTO(item.getId(), item.getModel(), item.getQty(), item.getPrice(), item.getStatus()));
        }
        return list;
    }
}
