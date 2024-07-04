package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.Laptop_Shop_Management.bo.custom.DriverBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.DriverDAO;
import lk.ijse.Laptop_Shop_Management.dto.DriverDTO;
import lk.ijse.Laptop_Shop_Management.entity.Driver;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;

import java.sql.SQLException;

public class DriverBOImpl implements DriverBO {
    DriverDAO driverDAO;

    public DriverBOImpl() {
        this.driverDAO = (DriverDAO) DAOFactory.getDAO(DAOFactory.DAOType.DRIVER);
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return driverDAO.delete(nic);
    }

    @Override
    public boolean save(DriverDTO driverDTO) throws SQLException, ClassNotFoundException {
        Driver driver = new Driver(driverDTO.getId(), driverDTO.getName(), driverDTO.getNic(), driverDTO.getAddress(), driverDTO.getEmail(), driverDTO.getTel(), driverDTO.getStatus());
        return driverDAO.save(driver);
    }

    @Override
    public boolean checkId(String nic) throws SQLException, ClassNotFoundException {
        return driverDAO.checkId(nic);
    }

    @Override
    public boolean update() throws SQLException, ClassNotFoundException {
        return driverDAO.update();
    }

    @Override
    public DriverDTO search(String nic) throws SQLException, ClassNotFoundException {
        Driver driver = driverDAO.search(nic);
        if (driver != null){
            return new DriverDTO(driver.getId(), driver.getName(), driver.getNic(), driver.getAddress(), driver.getEmail(), driver.getTel(), driver.getStatus());
        }
        return null;
    }

    @Override
    public ObservableList<DriverDTO> getDriver() throws SQLException, ClassNotFoundException {
        ObservableList<Driver> drivers = driverDAO.getObject();
        ObservableList<DriverDTO> driverDTOS = FXCollections.observableArrayList();
        for (Driver driver : drivers) {
            driverDTOS.add(new DriverDTO(driver.getId(), driver.getName(), driver.getNic(), driver.getAddress(), driver.getEmail(), driver.getTel(), driver.getStatus()));
        }
        return driverDTOS;
    }

}
