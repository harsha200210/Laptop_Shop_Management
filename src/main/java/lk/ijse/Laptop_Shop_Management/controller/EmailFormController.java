package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Laptop_Shop_Management.util.Email;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailFormController {

    @FXML
    private TextArea txtAreaMassege;

    @FXML
    private TextArea txtAreaSubject;

    @FXML
    private TextField txtTo;

    @FXML
    void btnSendAction(ActionEvent event) {
        if (isValidEmail()){
            Email.sendMail(txtTo.getText(), txtAreaSubject.getText(), txtAreaMassege.getText());
        }
    }

    @FXML
    void txtToAction(ActionEvent event) {
        txtAreaSubject.requestFocus();
    }

    private boolean isValidEmail() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.EMAIL,txtTo)) return false;
        return true;
    }

    @FXML
    void txtToKeyReleseAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.EMAIL,txtTo);
    }
}
