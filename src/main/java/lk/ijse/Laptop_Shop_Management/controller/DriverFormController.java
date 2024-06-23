package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.DriverBO;
import lk.ijse.Laptop_Shop_Management.dao.custom.impl.DriverDAOImpl;
import lk.ijse.Laptop_Shop_Management.dto.DriverDTO;
import lk.ijse.Laptop_Shop_Management.tdm.DriverTm;
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

    DriverBO driverBO = (DriverBO) BOFactory.getBO(BOFactory.BOType.DRIVER);

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
            if (driverBO.checkId(txtNic.getText())){
                if (driverBO.delete(txtNic.getText())){
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
                DriverDTO driverDTO = getValues();
                if (driverBO.save(driverDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Driver Saved!").show();
                    loadAllDrivers();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private DriverDTO getValues() {
        return new DriverDTO(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            DriverDTO driverDTO = getValues();
            if (driverBO.checkId(driverDTO.getNic())){
                updateValues(driverDTO);
                if (driverBO.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Driver Updated!").show();
                    setNullValue();
                    loadAllDrivers();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(DriverDTO driverDTO) {
        if (!driverDTO.getName().equals("") && !driverDTO.getName().equals(DriverDAOImpl.driver.getName())){
            DriverDAOImpl.driver.setName(driverDTO.getName());
        }
        if (!driverDTO.getAddress().equals("") && !driverDTO.getAddress().equals(DriverDAOImpl.driver.getAddress())){
            DriverDAOImpl.driver.setAddress(driverDTO.getAddress());
        }
        if (!driverDTO.getEmail().equals("") && !driverDTO.getEmail().equals(DriverDAOImpl.driver.getEmail())){
            DriverDAOImpl.driver.setEmail(driverDTO.getEmail());
        }
        if (driverDTO.getTel() != 0 && driverDTO.getTel() != DriverDAOImpl.driver.getTel()){
            DriverDAOImpl.driver.setTel(driverDTO.getTel());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
       searchAction(event);
    }

    private void setTable(DriverDTO driverDTO) {
        ObservableList<DriverTm> obList = FXCollections.observableArrayList();
        DriverTm tm = new DriverTm(driverDTO.getName(), driverDTO.getNic(), driverDTO.getAddress(), driverDTO.getEmail(), driverDTO.getTel());
        obList.add(tm);
        driverTable.setItems(obList);
        setCellValueFactory();
    }

    private void setValues(DriverDTO driverDTO) {
            txtNic.setText(driverDTO.getNic());
            txtName.setText(driverDTO.getName());
            txtAddress.setText(driverDTO.getAddress());
            txtEmail.setText(driverDTO.getEmail());
            txtTel.setText(String.valueOf(driverDTO.getTel()));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                DriverDTO driverDTO = driverBO.search(txtSearch.getText());
                if (driverDTO != null){
                    setValues(driverDTO);
                    setTable(driverDTO);
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
            ObservableList<DriverDTO> list = driverBO.getDriver();
            for (DriverDTO driverDTO : list){
                obList.add(new DriverTm(driverDTO.getName(), driverDTO.getNic(), driverDTO.getAddress(), driverDTO.getEmail(), driverDTO.getTel()));
            }
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
