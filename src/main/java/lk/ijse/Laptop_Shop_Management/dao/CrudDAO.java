package lk.ijse.Laptop_Shop_Management.dao;

import java.sql.SQLException;

public interface CrudDAO<T> {
    int count() throws SQLException, ClassNotFoundException;

    T search(String dao) throws SQLException, ClassNotFoundException;

    boolean save(T dao) throws SQLException, ClassNotFoundException;

    boolean checkId(String dao) throws SQLException, ClassNotFoundException;

    boolean update() throws SQLException, ClassNotFoundException;

    boolean delete(String dao) throws SQLException, ClassNotFoundException;

}
