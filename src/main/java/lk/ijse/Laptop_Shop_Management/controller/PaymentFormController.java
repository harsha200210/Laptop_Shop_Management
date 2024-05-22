package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.Payment;
import lk.ijse.Laptop_Shop_Management.repository.PaymentRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PaymentFormController {

    @FXML
    private Label balanceLabel;

    @FXML
    private Button btnPay;

    @FXML
    private AnchorPane cashAnchorPane;

    @FXML
    private Label netTotalLabel;

    @FXML
    private AnchorPane paymentForm;

    @FXML
    private TextField txtCash;

    @FXML
    private ComboBox<String> typeBox;

    @Setter
    private OrderFormController orderFormController;

    public void initialize(){
        btnPay.setVisible(false);
        addPaymentType();
        getNetTotal();
    }

    private void getNetTotal() {
        netTotalLabel.setText(String.valueOf(OrderFormController.order.getPrice()));
    }

    private void addPaymentType() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Cash");
        list.add("Card");
        typeBox.setItems(list);
        typeBox.getSelectionModel().selectFirst();
    }

    @FXML
    void btnCompleteAction(ActionEvent event) {
        Payment payment = new Payment(typeBox.getValue(), Date.valueOf(LocalDate.now()),OrderFormController.order.getOrderId());
        try {
            if (PaymentRepo.save(payment)){
                orderFormController.btnNewOrder(event);
                closeStage();
                printBill();
                //new Alert(Alert.AlertType.CONFIRMATION, "Order Successfully !!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void printBill() {
        JasperDesign jasperDesign = null;
        JasperReport jasperReport = null;
        try {
            jasperDesign = JRXmlLoader.load("src/main/resources/Report/LapTop_Bill.jrxml");
            jasperReport = JasperCompileManager.compileReport(jasperDesign);


            Map<String,Object> data = new HashMap<>();
            data.put("OrderId",OrderFormController.order.getOrderId());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void closeStage() {
        Stage stage = (Stage) paymentForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnPayAction(ActionEvent event) {
        btnCompleteAction(event);
    }

    @FXML
    void txtCashAction(ActionEvent event) {
        if (isValied()){
            double balacnce  = Double.parseDouble(txtCash.getText()) - Double.parseDouble(netTotalLabel.getText());
            balanceLabel.setText(String.valueOf(balacnce));
        }
    }

    @FXML
    void typeBoxAction(ActionEvent event) {
        if (typeBox.getValue().equals("Cash")){
            btnPay.setVisible(false);
            cashAnchorPane.setVisible(true);
        } else {
            btnPay.setVisible(true);
            cashAnchorPane.setVisible(false);
        }
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE, txtCash)) return false;
        return true;
    }

    @FXML
    void cashAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtCash);
    }

}
