package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.*;
import lk.ijse.Laptop_Shop_Management.bo.custom.impl.*;
import lk.ijse.Laptop_Shop_Management.dto.*;
import lk.ijse.Laptop_Shop_Management.entity.Driver;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;


public class DeleteDataFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<ItemTm> itemTable;

    @FXML
    private AnchorPane deleteDataAnchorPane;

    @FXML
    private ComboBox<String> deleteDataBox;

    @FXML
    private AnchorPane tableAnchorPane;

    @FXML
    private TableView<DriverTm> tableModel;

    DeleteDataBO deleteDataBO = (DeleteDataBO) BOFactory.getBO(BOFactory.BOType.DELETEDATA);

    public void initialize(){
        getDataType();
        setCellValueFactory();
        itemTable.setVisible(false);
    }

    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getDataType() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Customer");
        list.add("Item");
        list.add("Supplier");
        list.add("Employee");
        list.add("Driver");
        deleteDataBox.setItems(list);
    }

    @FXML
    void deleteDataBoxAction(ActionEvent event) {
        switch (deleteDataBox.getValue()){
            case "Customer" :
                setCustomers();
                break;
            case "Item" :
                setItems();
                break;
            case "Supplier" :
                setSupplier();
                break;
            case "Employee" :
                setEmployee();
                break;
            case "Driver" :
                setDriver();
                break;
        }

    }

    private void setDriver() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<DriverDTO> list = deleteDataBO.getDeleteDrivers();
            for (DriverDTO driverDTO : list) {
                tableModel.getItems().add(new DriverTm(driverDTO.getName(), driverDTO.getNic(), driverDTO.getAddress(), driverDTO.getEmail(), driverDTO.getTel()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setEmployee() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<EmployeeDTO> list = deleteDataBO.getDeleteEmployee();
            for (EmployeeDTO employeeDTO : list){
                tableModel.getItems().add(new DriverTm(employeeDTO.getName(), employeeDTO.getNic(), employeeDTO.getAddress(), employeeDTO.getEmail(), employeeDTO.getTel()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setSupplier() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<SupplierDTO> list = deleteDataBO.getDeleteSupplier();
            for (SupplierDTO supplierDTO : list){
                tableModel.getItems().add(new DriverTm(supplierDTO.getName(),supplierDTO.getNic(),supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getTel()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setItems() {
        itemTable.setVisible(true);
        tableModel.setVisible(false);
        try {
            ObservableList<ItemDTO> deleteItem = deleteDataBO.getDeleteItem();
            for (ItemDTO item : deleteItem) {
                itemTable.getItems().add(new ItemTm(item.getModel(), item.getQty(), item.getPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCustomers() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<CustomerDTO> list = deleteDataBO.getDeleteCustomer();
            for (CustomerDTO customerDTO : list) {
                tableModel.getItems().add(new DriverTm(customerDTO.getName(), customerDTO.getNic(), customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getTel()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
