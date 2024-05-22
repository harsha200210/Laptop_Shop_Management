package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Laptop_Shop_Management.model.User;
import lk.ijse.Laptop_Shop_Management.repository.UserRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.io.IOException;

public class LoginFormController {

    public AnchorPane loginForm;
    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    public static User user;

    @FXML
    void enterNameAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void enterPasswordAction(ActionEvent event) {
        btnLoginAction(event);
    }

    @FXML
    void btnLoginAction(ActionEvent event) {
        if (isValied()){
            user = new User();
            user.setUserName(txtUserName.getText());
            user.setPassword(txtPassword.getText());

            try {
                boolean isUser = UserRepo.checkUser(user);
                if (isUser){
                    navigateToTheMainForm();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private void navigateToTheMainForm() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml")));

        Stage stage = (Stage) this.loginForm.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void registerAction(MouseEvent event) {
        try {
            loginAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/signUp_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void exitAction(MouseEvent event) {
        Stage stage = (Stage) loginForm.getScene().getWindow();
        stage.close();
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME, txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PASSWORD, txtPassword)) return false;
        return true;
    }

    @FXML
    void txtPasswordAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PASSWORD,txtPassword);
    }

    @FXML
    void txtUserNameAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtUserName);
    }
}
