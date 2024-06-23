package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.TaxRateBO;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class TaxRateFromController {

    @FXML
    private Slider taxSlider;

    @FXML
    private TextField txtRate;

    TaxRateBO taxRateBO = (TaxRateBO) BOFactory.getBO(BOFactory.BOType.TAXRATE);

    public void initialize(){
        try {
            double rate = taxRateBO.getTaxRate();
            taxSlider.setValue(rate);
            txtRate.setText(String.format("%.2f",taxSlider.getValue()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void taxSliderAction(MouseEvent event) {
        txtRate.setText(String.format("%.2f",taxSlider.getValue()));
    }

    @FXML
    void btnChangeAction(ActionEvent event) {
        if (changeValid()){
            try {
                if (taxRateBO.changeTaxRate(taxSlider.getValue())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Change Successfully !!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING,"Change Unsuccessfully !!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    public boolean changeValid(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtRate)) return false;
        return true;
    }

    @FXML
    void txtRateAction(ActionEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtRate);
        taxSlider.setValue(Double.parseDouble(txtRate.getText()));
    }
}
