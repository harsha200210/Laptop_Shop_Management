package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Laptop_Shop_Management.repository.ConfigurationRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.sql.SQLException;

public class DeliverySettingFormController {

    @FXML
    private TextField txtInsideColombo;

    @FXML
    private TextField txtOutOfColombo;

    public void initialize(){
        try {
            txtInsideColombo.setText(String.valueOf(ConfigurationRepo.getInsideChange()));
            txtOutOfColombo.setText(String.valueOf(ConfigurationRepo.getOutSideChage()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        if (isValied()){
            double inside = Double.parseDouble(txtInsideColombo.getText());
            double out = Double.parseDouble(txtOutOfColombo.getText());


            try {
                if (ConfigurationRepo.changeDeliveryCharge(inside,out)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Change Successfully !!!").show();
                    clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Change Unsuccessfully !!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void clear() {
        txtInsideColombo.setText("");
        txtOutOfColombo.setText("");
    }

    @FXML
    void txtInsideColomboAction(ActionEvent event) {
        btnSaveAction(event);
    }

    @FXML
    void txtOutOfColomboAction(ActionEvent event) {
        txtInsideColombo.requestFocus();
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE, txtInsideColombo)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE, txtOutOfColombo)) return false;
        return true;
    }

    @FXML
    void insideColomboAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtInsideColombo);
    }

    @FXML
    void outOfColomboAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtOutOfColombo);
    }
}
