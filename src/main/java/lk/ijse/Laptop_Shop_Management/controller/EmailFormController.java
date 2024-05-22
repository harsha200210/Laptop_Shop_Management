package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
            sendMail(txtTo.getText(), txtAreaSubject.getText(), txtAreaMassege.getText());
        }
    }

    @FXML
    void txtToAction(ActionEvent event) {
        txtAreaSubject.requestFocus();
    }

    private void sendMail(String to, String subject, String text) {
        final String username = "sellx.laptop2024@gmail.com";
        final String password = "ohux cozv uckk ygfw";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sellx.laptop2024@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            new Alert(Alert.AlertType.CONFIRMATION,"Email sent successfully!").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
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
