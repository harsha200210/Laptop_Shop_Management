package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.Laptop_Shop_Management.model.Supplier;
import lk.ijse.Laptop_Shop_Management.model.tm.SupplierTm;
import lk.ijse.Laptop_Shop_Management.repository.SupplierRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class SupplierFormController {

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
    private TableView<SupplierTm> supplierTable;

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
        loadAllSupplier();
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
            if (SupplierRepo.checkId(txtNic.getText())){
                if (SupplierRepo.delete(txtNic.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted!").show();
                    loadAllSupplier();
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
                Supplier supplier = getValues();
                if (SupplierRepo.save(supplier)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Saved!").show();
                    loadAllSupplier();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Supplier getValues() {
        return new Supplier(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            Supplier supplier = getValues();
            if (SupplierRepo.checkId(supplier.getNic())){
                updateValues(supplier);
                if (SupplierRepo.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated!").show();
                    setNullValue();
                    loadAllSupplier();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(Supplier supplier) {
        if (!supplier.getName().equals("") && !supplier.getName().equals(SupplierRepo.supplier.getName())){
            SupplierRepo.supplier.setName(supplier.getName());
        }
        if (!supplier.getAddress().equals("") && !supplier.getAddress().equals(SupplierRepo.supplier.getAddress())){
            SupplierRepo.supplier.setAddress(supplier.getAddress());
        }
        if (!supplier.getEmail().equals("") && !supplier.getEmail().equals(SupplierRepo.supplier.getEmail())){
            SupplierRepo.supplier.setEmail(supplier.getEmail());
        }
        if (supplier.getTel() != 0 && supplier.getTel() != SupplierRepo.supplier.getTel()){
            SupplierRepo.supplier.setTel(supplier.getTel());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setTable(Supplier supplier) {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        SupplierTm tm = new SupplierTm(supplier.getName(), supplier.getNic(), supplier.getAddress(), supplier.getEmail(), supplier.getTel());
        obList.add(tm);
        supplierTable.setItems(obList);
    }

    private void setValues(Supplier supplier) {
            txtNic.setText(supplier.getNic());
            txtName.setText(supplier.getName());
            txtAddress.setText(supplier.getAddress());
            txtEmail.setText(supplier.getEmail());
            txtTel.setText(String.valueOf(supplier.getTel()));

    }

    private void setCellValueFactory() {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                Supplier supplier = SupplierRepo.search(txtSearch.getText());
                if (supplier != null){
                    setValues(supplier);
                    setTable(supplier);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllSupplier(){
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try {
            obList = SupplierRepo.getSupplier();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        supplierTable.setItems(obList);
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
    void supplierTableAction(MouseEvent event) {
        SupplierTm selectedItem = supplierTable.getSelectionModel().getSelectedItem();

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
