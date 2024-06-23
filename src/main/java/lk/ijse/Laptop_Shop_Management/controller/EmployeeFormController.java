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
import lk.ijse.Laptop_Shop_Management.bo.custom.EmployeeBO;
import lk.ijse.Laptop_Shop_Management.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.Laptop_Shop_Management.dto.EmployeeDTO;
import lk.ijse.Laptop_Shop_Management.dto.SalaryDTO;
import lk.ijse.Laptop_Shop_Management.tdm.EmployeeTm;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.sql.SQLException;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<EmployeeTm> employeeTable;

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
    private TextField txtSalary;

    @FXML
    private TextField txtTel;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize(){
        loadAllEmployee();
        setCellValueFactory();
    }

    @FXML
    void btnClearAction(ActionEvent event) {
        setNullValue();
    }

    private void setNullValue() {
        txtSalary.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtTel.setText("");
        txtNic.setText("");
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        try {
            if (employeeBO.checkId(txtNic.getText())){
                if (employeeBO.delete(txtNic.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Deleted!").show();
                    loadAllEmployee();
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
                EmployeeDTO employeeDTO = getValues();
                SalaryDTO salaryDTO = getData();
                if (employeeBO.save(employeeDTO, salaryDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved!").show();
                    loadAllEmployee();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private SalaryDTO getData() throws SQLException, ClassNotFoundException {
        double salary = Double.parseDouble(txtSalary.getText());
        double taxRate = employeeBO.getTaxRate();
        double tax =  salary * (taxRate /100);
        double etf = salary * ((double) 3 /100);
        double epf = salary * ((double) 20 /100);
        return new SalaryDTO(salary,tax,etf,epf);
    }

    private EmployeeDTO getValues() {
        return new EmployeeDTO(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),LoginFormController.userDTO.getId(), "");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            EmployeeDTO employeeDTO = getValues();
            SalaryDTO salaryDTO = getData();
            if (employeeBO.checkId(employeeDTO.getNic())){
                updateValues(employeeDTO);
                if (employeeBO.updateSalary(salaryDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated!").show();
                    setNullValue();
                    loadAllEmployee();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(EmployeeDTO employeeDTO) {
        if (!employeeDTO.getName().equals("") && !employeeDTO.getName().equals(EmployeeDAOImpl.employee.getName())){
            EmployeeDAOImpl.employee.setName(employeeDTO.getName());
        }
        if (!employeeDTO.getAddress().equals("") && !employeeDTO.getAddress().equals(EmployeeDAOImpl.employee.getAddress())){
            EmployeeDAOImpl.employee.setAddress(employeeDTO.getAddress());
        }
        if (!employeeDTO.getEmail().equals("") && !employeeDTO.getEmail().equals(EmployeeDAOImpl.employee.getEmail())){
            EmployeeDAOImpl.employee.setEmail(employeeDTO.getEmail());
        }
        if (employeeDTO.getTel() != 0 && employeeDTO.getTel() != EmployeeDAOImpl.employee.getTel()){
            EmployeeDAOImpl.employee.setTel(employeeDTO.getTel());
        }

    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setValues(EmployeeDTO employeeDTO) {
            txtNic.setText(employeeDTO.getNic());
            txtName.setText(employeeDTO.getName());
            txtAddress.setText(employeeDTO.getAddress());
            txtEmail.setText(employeeDTO.getEmail());
            txtTel.setText(String.valueOf(employeeDTO.getTel()));
        try {
            txtSalary.setText(employeeBO.getSalary(employeeDTO.getId()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setTable(EmployeeDTO employeeDTO) {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        double salary = 0;
        try {
            salary = Double.parseDouble(employeeBO.getSalary(employeeDTO.getId()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        EmployeeTm tm = new EmployeeTm(employeeDTO.getName(), employeeDTO.getNic(), employeeDTO.getAddress(), employeeDTO.getEmail(), employeeDTO.getTel() , salary);
        obList.add(tm);
        employeeTable.setItems(obList);
    }

    private void setCellValueFactory() {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                EmployeeDTO employeeDTO = employeeBO.search(txtSearch.getText());
                if (employeeDTO != null){
                    setValues(employeeDTO);
                    setTable(employeeDTO);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllEmployee(){
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        try {
            obList = employeeBO.getEmployee();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        employeeTable.setItems(obList);
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
    void getTelAction(ActionEvent event) {
        txtSalary.requestFocus();
    }

    @FXML
    void employeeTableAction(MouseEvent event) {
        EmployeeTm selectedItem = employeeTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            txtNic.setText(selectedItem.getNic());
            txtSalary.setText(String.valueOf(selectedItem.getSalary()));
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtEmail.setText(selectedItem.getEmail());
            txtTel.setText(String.valueOf(selectedItem.getTel()));
        }
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtNic)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtSalary)) return false;
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
    void txtSalaryAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtSalary);
    }

    @FXML
    void txtSearchAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NIC,txtSearch);
    }
}
