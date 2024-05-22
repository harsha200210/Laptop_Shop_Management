package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Laptop_Shop_Management.model.Customer;
import lk.ijse.Laptop_Shop_Management.model.tm.CustomerTm;
import lk.ijse.Laptop_Shop_Management.repository.CustomerRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class CustomerFormController {

    @FXML
    private TableView<CustomerTm> customerTable;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button buttonSave;

    @FXML
    private AnchorPane customerPane;

    public void initialize()  {
        setCellValueFactory();
        loadAllCustomers();
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        if (isValied()){
            try {
                Customer customer = getValues();
                if (CustomerRepo.save(customer)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!").show();
                    loadAllCustomers();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Customer getValues() {
        return new Customer(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),"");
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
            if (CustomerRepo.checkId(txtNic.getText())){
                if (CustomerRepo.delete(txtNic.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted!").show();
                    loadAllCustomers();
                    setNullValue();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            Customer customer = getValues();
            if (CustomerRepo.checkId(customer.getNic())){
                updateValues(customer);
                if (CustomerRepo.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated!").show();
                    setNullValue();
                    loadAllCustomers();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(Customer customer) {
        if (!customer.getName().equals("") && !customer.getName().equals(CustomerRepo.customer.getName())){
            CustomerRepo.customer.setName(customer.getName());
        }
        if (!customer.getAddress().equals("") && !customer.getAddress().equals(CustomerRepo.customer.getAddress())){
            CustomerRepo.customer.setAddress(customer.getAddress());
        }
        if (!customer.getEmail().equals("") && !customer.getEmail().equals(CustomerRepo.customer.getEmail())){
            CustomerRepo.customer.setEmail(customer.getEmail());
        }
        if (customer.getTel() != 0 && customer.getTel() != CustomerRepo.customer.getTel()){
            CustomerRepo.customer.setTel(customer.getTel());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setTable(Customer customer) {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        CustomerTm tm = new CustomerTm(customer.getName(), customer.getNic(), customer.getAddress(), customer.getEmail(), customer.getTel());
        obList.add(tm);
        customerTable.setItems(obList);
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                Customer customer = CustomerRepo.search(txtSearch.getText());
                if (customer != null){
                    setValues(customer);
                    setTable(customer);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private void setValues(Customer customer) {
            txtNic.setText(customer.getNic());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtEmail.setText(customer.getEmail());
            txtTel.setText(String.valueOf(customer.getTel()));
    }

    private void loadAllCustomers(){
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            obList = CustomerRepo.getCustomers();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        customerTable.setItems(obList);
    }

    private void setCellValueFactory() {
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
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
    void customerTableAction(MouseEvent event) {
        CustomerTm selectedItem = customerTable.getSelectionModel().getSelectedItem();

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
