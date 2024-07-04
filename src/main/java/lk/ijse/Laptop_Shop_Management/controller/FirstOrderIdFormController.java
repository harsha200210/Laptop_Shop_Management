package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FirstOrderIdFormController {

    @FXML
    private AnchorPane firstOrderIdPane;

    @FXML
    private Label labelFirstOrderId;

    @FXML
    private TextField txtPrefix;

    @FXML
    private TextField txtSuffix;

    public static String firstOrderId;

    @FXML
    void btnSaveAction(ActionEvent event) {
        firstOrderId = labelFirstOrderId.getText();
        try {
            loadOrderForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadOrderForm() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/lk.ijse.Laptop_Shop_Management/order_form.fxml")));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) firstOrderIdPane.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void txtPrefixAction(ActionEvent event) {
        txtSuffix.requestFocus();
    }

    @FXML
    void txtSuffixAction(ActionEvent event) {
        labelFirstOrderId.setText(txtPrefix.getText() + "1" + txtSuffix.getText());
    }

}
