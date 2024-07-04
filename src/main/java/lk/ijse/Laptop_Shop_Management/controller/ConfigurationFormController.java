package lk.ijse.Laptop_Shop_Management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;


public class ConfigurationFormController {

    @FXML
    private AnchorPane changePane;

    @FXML
    private Line lineDelievrySetting;

    @FXML
    private Line lineTaxRate;

    public void initialize(){
        navigateDelivery();
    }

    private void navigateDelivery() {
        removeStyle();
        changeStyleLine(lineDelievrySetting);
        try {
            changePane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/lk.ijse.Laptop_Shop_Management/deliverySetting_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void deliveryAction(MouseEvent event) {
        navigateDelivery();
    }

    @FXML
    void taxRateAction(MouseEvent event) {
        removeStyle();
        changeStyleLine(lineTaxRate);
        try {
            changePane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/lk.ijse.Laptop_Shop_Management/taxRate_from.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void changeStyleLine(Line line){
        line.setStyle("-fx-stroke: #1E90FF;");
    }

    private void removeStyle(){
        String style = "-fx-stroke: white;";
        lineTaxRate.setStyle(style);
        lineDelievrySetting.setStyle(style);
    }

}
