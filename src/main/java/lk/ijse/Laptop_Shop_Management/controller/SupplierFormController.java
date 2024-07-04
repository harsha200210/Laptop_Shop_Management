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
import lk.ijse.Laptop_Shop_Management.bo.custom.SupplierBO;
import lk.ijse.Laptop_Shop_Management.dao.custom.impl.SupplierDAOImpl;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierTm;
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

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBO(BOFactory.BOType.SUPPLIER);

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
            if (supplierBO.checkId(txtNic.getText())){
                if (supplierBO.delete(txtNic.getText())){
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
                SupplierDTO supplierDTO = getValues();
                if (supplierBO.save(supplierDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Saved!").show();
                    loadAllSupplier();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private SupplierDTO getValues() {
        return new SupplierDTO(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            SupplierDTO supplierDTO = getValues();
            if (supplierBO.checkId(supplierDTO.getNic())){
                updateValues(supplierDTO);
                if (supplierBO.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated!").show();
                    setNullValue();
                    loadAllSupplier();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(SupplierDTO supplierDTO) {
        if (!supplierDTO.getName().equals("") && !supplierDTO.getName().equals(SupplierDAOImpl.supplier.getName())){
            SupplierDAOImpl.supplier.setName(supplierDTO.getName());
        }
        if (!supplierDTO.getAddress().equals("") && !supplierDTO.getAddress().equals(SupplierDAOImpl.supplier.getAddress())){
            SupplierDAOImpl.supplier.setAddress(supplierDTO.getAddress());
        }
        if (!supplierDTO.getEmail().equals("") && !supplierDTO.getEmail().equals(SupplierDAOImpl.supplier.getEmail())){
            SupplierDAOImpl.supplier.setEmail(supplierDTO.getEmail());
        }
        if (supplierDTO.getTel() != 0 && supplierDTO.getTel() != SupplierDAOImpl.supplier.getTel()){
            SupplierDAOImpl.supplier.setTel(supplierDTO.getTel());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setTable(SupplierDTO supplierDTO) {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        SupplierTm tm = new SupplierTm(supplierDTO.getName(), supplierDTO.getNic(), supplierDTO.getAddress(), supplierDTO.getEmail(), supplierDTO.getTel());
        obList.add(tm);
        supplierTable.setItems(obList);
    }

    private void setValues(SupplierDTO supplierDTO) {
            txtNic.setText(supplierDTO.getNic());
            txtName.setText(supplierDTO.getName());
            txtAddress.setText(supplierDTO.getAddress());
            txtEmail.setText(supplierDTO.getEmail());
            txtTel.setText(String.valueOf(supplierDTO.getTel()));

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
                SupplierDTO supplierDTO = supplierBO.search(txtSearch.getText());
                if (supplierDTO != null){
                    setValues(supplierDTO);
                    setTable(supplierDTO);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllSupplier(){
        try {
            ObservableList<SupplierDTO> supplierDTOS = supplierBO.getSupplier();
            for (SupplierDTO supplierDTO : supplierDTOS) {
                supplierTable.getItems().add(new SupplierTm(supplierDTO.getName(),supplierDTO.getNic(),supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getTel()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
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
