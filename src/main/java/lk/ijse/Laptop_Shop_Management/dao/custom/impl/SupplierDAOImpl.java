package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.SupplierDAO;
import lk.ijse.Laptop_Shop_Management.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDAOImpl implements SupplierDAO {
    public static Supplier supplier;

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT status FROM supplier");

        int count = 0;
        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET status = ? WHERE NIC = ?","Delete",nic);
    }

    @Override
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier (name, NIC, address, email, tel, status) VALUES (?, ?, ?, ?, ?, ?)", supplier.getName(), supplier.getNic(), supplier.getAddress(), supplier.getEmail(), supplier.getTel(), supplier.getStatus());
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE NIC = ?",nic);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        }  else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return false;
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET name = ?, address = ?, email = ?, tel = ? WHERE NIC = ?", supplier.getName(), supplier.getAddress(), supplier.getEmail(), supplier.getTel(), supplier.getNic());
    }

    @Override
    public Supplier search(String nic) throws SQLException, ClassNotFoundException {
        supplier = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE NIC = ?",nic);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return supplier;
    }

    @Override
    public ObservableList<Supplier> getSupplier() throws SQLException, ClassNotFoundException {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");

        while (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                obList.add(new Supplier(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getString(7)));
            }
        }
        return obList;
    }

    @Override
    public ObservableList<Supplier> getDeleteSupplier() throws SQLException, ClassNotFoundException {
        ObservableList<Supplier> list = FXCollections.observableArrayList();

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");

        while (resultSet.next()){
            if (resultSet.getString("status").equals("Delete")) {
                list.add(new Supplier(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getString(7)));
            }
        }
        return list;
    }

    @Override
    public Supplier searchSupplier(int tel) throws SQLException, ClassNotFoundException {
        supplier = null;

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE tel = ?",tel);

        if (resultSet.next()){
            if (!resultSet.getString("status").equals("Delete")){
                supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6), resultSet.getString(7));
            } else {
                new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Can't find supplier id!!!").show();
        }
        return supplier;
    }
}
