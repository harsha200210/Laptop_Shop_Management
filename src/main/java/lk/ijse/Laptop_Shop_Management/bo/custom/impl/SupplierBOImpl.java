package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.SupplierBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.SupplierDAO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;
import lk.ijse.Laptop_Shop_Management.entity.Supplier;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierTm;

import java.sql.SQLException;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO;

    public SupplierBOImpl() {
        this.supplierDAO = (SupplierDAO) DAOFactory.getDAO(DAOFactory.DAOType.SUPPLIER);
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(nic);
    }

    @Override
    public boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(supplierDTO.getId(),supplierDTO.getName(),supplierDTO.getNic(),supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getTel(),supplierDTO.getStatus());
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        return supplierDAO.checkId(nic);
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return supplierDAO.update();
    }

    @Override
    public SupplierDTO search(String nic) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(nic);
        if (supplier != null){
            return new SupplierDTO(supplier.getId(),supplier.getName(),supplier.getNic(),supplier.getAddress(),supplier.getEmail(),supplier.getTel(),supplier.getStatus());
        }
        return null;
    }

    @Override
    public ObservableList<SupplierDTO> getSupplier() throws SQLException, ClassNotFoundException {
        ObservableList<SupplierDTO> list = FXCollections.observableArrayList();
        ObservableList<Supplier> suppliers = supplierDAO.getSupplier();

        for (Supplier supplier : suppliers) {
            list.add(new SupplierDTO(supplier.getId(),supplier.getName(),supplier.getNic(),supplier.getAddress(),supplier.getEmail(),supplier.getTel(),supplier.getStatus()));
        }
        return list;
    }

}
