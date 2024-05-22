package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.Laptop_Shop_Management.model.Driver;
import lk.ijse.Laptop_Shop_Management.model.tm.DriverTm;
import lk.ijse.Laptop_Shop_Management.repository.DriverRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class DriverFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<DriverTm> driverTable;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTel;

    public void initialize(){
        setCellValueFactory();
        loadAllDrivers();
    }

    @FXML
    void btnClearAction(ActionEvent event) {
        setNullValue();
    }

    private void setNullValue() {
        txtNic.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        try {
            if (DriverRepo.checkId(txtNic.getText())){
                if (DriverRepo.delete(txtNic.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Driver Deleted!").show();
                    loadAllDrivers();
                    setNullValue();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        if (isValied()){
            try {
                Driver driver = getValues();
                if (DriverRepo.save(driver)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Driver Saved!").show();
                    loadAllDrivers();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Driver getValues() {
        return new Driver(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            Driver driver = getValues();
            if (DriverRepo.checkId(driver.getNic())){
                updateValues(driver);
                if (DriverRepo.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Driver Updated!").show();
                    setNullValue();
                    loadAllDrivers();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(Driver driver) {
        if (!driver.getName().equals("") && !driver.getName().equals(DriverRepo.driver.getName())){
            DriverRepo.driver.setName(driver.getName());
        }
        if (!driver.getAddress().equals("") && !driver.getAddress().equals(DriverRepo.driver.getAddress())){
            DriverRepo.driver.setAddress(driver.getAddress());
        }
        if (!driver.getEmail().equals("") && !driver.getEmail().equals(DriverRepo.driver.getEmail())){
            DriverRepo.driver.setEmail(driver.getEmail());
        }
        if (driver.getTel() != 0 && driver.getTel() != DriverRepo.driver.getTel()){
            DriverRepo.driver.setTel(driver.getTel());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
       searchAction(event);
    }

    private void setTable(Driver driver) {
        ObservableList<DriverTm> obList = FXCollections.observableArrayList();
        DriverTm tm = new DriverTm(driver.getName(), driver.getNic(), driver.getAddress(), driver.getEmail(), driver.getTel());
        obList.add(tm);
        driverTable.setItems(obList);
        setCellValueFactory();
    }

    private void setValues(Driver driver) {
            txtNic.setText(driver.getNic());
            txtName.setText(driver.getName());
            txtAddress.setText(driver.getAddress());
            txtEmail.setText(driver.getEmail());
            txtTel.setText(String.valueOf(driver.getTel()));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                Driver driver = DriverRepo.search(txtSearch.getText());
                if (driver != null){
                    setValues(driver);
                    setTable(driver);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllDrivers(){
        ObservableList<DriverTm> obList = FXCollections.observableArrayList();
        try {
            obList = DriverRepo.getDriver();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        driverTable.setItems(obList);
    }

    private void setCellValueFactory() {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }
    @FXML
    void getAddressAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void getEmailAction(ActionEvent event) {
        txtTel.requestFocus();
    }

    @FXML
    void getNicAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void getNameAction(ActionEvent event) {
        txtNic.requestFocus();
    }

    @FXML
    void driverTableAcion(MouseEvent event) {
        DriverTm selectedItem = driverTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            txtNic.setText(selectedItem.getNic());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtEmail.setText(selectedItem.getEmail());
            txtTel.setText(String.valueOf(selectedItem.getTel()));
        }
    }


    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtNic)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL,txtTel)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.EMAIL,txtEmail)) return false;
        return true;
    }

    public boolean searchValid(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtSearch)) return false;
        return true;
    }

    @FXML
    void txtNicAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtNic);
    }

    @FXML
    void txtAddressAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.ADDRESS,txtAddress);
    }

    @FXML
    void txtEmailAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.EMAIL,txtEmail);
    }

    @FXML
    void txtNameAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtName);
    }

    @FXML
    void txtTelAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL,txtTel);
    }

    @FXML
    void txtSearchAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtSearch);
    }
}
