package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.OutOfStokeBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.ItemDAO;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

import java.sql.SQLException;

public class OutOfStokeBOImpl implements OutOfStokeBO {
    ItemDAO itemDAO;

    public OutOfStokeBOImpl() {
        this.itemDAO = (ItemDAO) DAOFactory.getDAO(DAOFactory.DAOType.ITEM);
    }

    @Override
    public ObservableList<ItemTm> outOfStokeItem() throws SQLException, ClassNotFoundException {
        return itemDAO.outOfStokeItem();
    }
}
