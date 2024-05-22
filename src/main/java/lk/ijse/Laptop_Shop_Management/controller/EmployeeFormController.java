package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.Laptop_Shop_Management.model.Employee;
import lk.ijse.Laptop_Shop_Management.model.Salary;
import lk.ijse.Laptop_Shop_Management.model.tm.EmployeeTm;
import lk.ijse.Laptop_Shop_Management.repository.ConfigurationRepo;
import lk.ijse.Laptop_Shop_Management.repository.EmployeeRepo;
import lk.ijse.Laptop_Shop_Management.repository.SalaryRepo;
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
            if (EmployeeRepo.checkId(txtNic.getText())){
                if (EmployeeRepo.delete(txtNic.getText())){
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
                Employee employee = getValues();
                Salary salary = getData();
                if (EmployeeRepo.save(employee,salary)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved!").show();
                    loadAllEmployee();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Salary getData() throws SQLException {
        double salary = Double.parseDouble(txtSalary.getText());
        double taxRate = ConfigurationRepo.getTaxRate();
        double tax =  salary * (taxRate /100);
        double etf = salary * ((double) 3 /100);
        double epf = salary * ((double) 20 /100);
        return new Salary(salary,tax,etf,epf);
    }

    private Employee getValues() {
        return new Employee(0,txtName.getText(),txtNic.getText(),txtAddress.getText(),txtEmail.getText(),Integer.parseInt(txtTel.getText()),LoginFormController.user.getId(), "");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            Employee employee = getValues();
            Salary salary = getData();
            if (EmployeeRepo.checkId(employee.getNic())){
                updateValues(employee);
                if (EmployeeRepo.update(salary)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated!").show();
                    setNullValue();
                    loadAllEmployee();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(Employee employee) {
        if (!employee.getName().equals("") && !employee.getName().equals(EmployeeRepo.employee.getName())){
            EmployeeRepo.employee.setName(employee.getName());
        }
        if (!employee.getAddress().equals("") && !employee.getAddress().equals(EmployeeRepo.employee.getAddress())){
            EmployeeRepo.employee.setAddress(employee.getAddress());
        }
        if (!employee.getEmail().equals("") && !employee.getEmail().equals(EmployeeRepo.employee.getEmail())){
            EmployeeRepo.employee.setEmail(employee.getEmail());
        }
        if (employee.getTel() != 0 && employee.getTel() != EmployeeRepo.employee.getTel()){
            EmployeeRepo.employee.setTel(employee.getTel());
        }

    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setValues(Employee employee) {
            txtNic.setText(employee.getNic());
            txtName.setText(employee.getName());
            txtAddress.setText(employee.getAddress());
            txtEmail.setText(employee.getEmail());
            txtTel.setText(String.valueOf(employee.getTel()));
        try {
            txtSalary.setText(SalaryRepo.getSalary(employee.getId()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setTable(Employee employee) {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        double salary = 0;
        try {
            salary = Double.parseDouble(SalaryRepo.getSalary(employee.getId()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        EmployeeTm tm = new EmployeeTm(employee.getName(), employee.getNic(), employee.getAddress(), employee.getEmail(), employee.getTel() , salary);
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
                Employee employee = EmployeeRepo.search(txtSearch.getText());
                if (employee != null){
                    setValues(employee);
                    setTable(employee);
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
            obList = EmployeeRepo.getEmployee();
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
