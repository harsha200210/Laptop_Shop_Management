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
        list.add("CustomerDTO");
        list.add("Item");
        list.add("Supplier");
        list.add("Employee");
        list.add("Driver");
        deleteDataBox.setItems(list);
    }

    @FXML
    void deleteDataBoxAction(ActionEvent event) {
        switch (deleteDataBox.getValue()){
            case "CustomerDTO" :
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
            ObservableList<DriverTm> list = deleteDataBO.getDeleteDrivers();
            tableModel.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setEmployee() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<DriverTm> list = deleteDataBO.getDeleteEmployee();
            tableModel.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setSupplier() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<DriverTm> list = deleteDataBO.getDeleteSupplier();
            tableModel.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setItems() {
        itemTable.setVisible(true);
        tableModel.setVisible(false);
        try {
            ObservableList<ItemTm> list = deleteDataBO.getDeleteItem();
            itemTable.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCustomers() {
        itemTable.setVisible(false);
        tableModel.setVisible(true);
        try {
            ObservableList<DriverTm> list = deleteDataBO.getDeleteCustomer();
            tableModel.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
