package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.SignUpBO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;
import lk.ijse.Laptop_Shop_Management.util.PasswordStorage;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.io.File;
import java.io.IOException;

public class SignUpFormController {

    @FXML
    private AnchorPane signUpPage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private ImageView imageViewPP;

    @FXML
    private StackPane paneImage;

    @FXML
    private CheckBox cashierCheckBox;

    @FXML
    private CheckBox ownerCheckBox;

    SignUpBO signUpBO = (SignUpBO) BOFactory.getBO(BOFactory.BOType.SIGNUP);

    private UserDTO userDTO = new UserDTO();

    public void initialize(){
        txtUserName.requestFocus();
    }

    @FXML
    void btnSignUpAction(ActionEvent event) {
        if (isValied()){
            userDTO.setUserName(txtUserName.getText());
            setPassword();

            try {
                boolean isSave = signUpBO.save(userDTO);
                if (isSave){
                    new Alert(Alert.AlertType.CONFIRMATION,"Save Successfully").show();

                    navigateToTheLoginForm();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private void setPassword() {
        String password = PasswordStorage.hashPassword(txtPassword.getText());
        userDTO.setPassword(password);
    }

    private void navigateToTheLoginForm() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml")));
        Stage stage = (Stage) signUpPage.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backAction(MouseEvent event) {
        try {
            navigateToTheLoginForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void cashierAction(ActionEvent event) {
        cashierCheckBox.setSelected(true);
        ownerCheckBox.setSelected(false);
        userDTO.setType("Cashier");
    }

    @FXML
    void ownerAction(ActionEvent event) {
        ownerCheckBox.setSelected(true);
        cashierCheckBox.setSelected(false);
        userDTO.setType("Owner");
    }

    @FXML
    void userNameAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void paneImageAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null  && selectedFile.exists()) {
            Image image = new Image(selectedFile.toURI().toString());
            imageViewPP.setImage(image);

            Circle clip = new Circle();
            clip.centerXProperty().bind(imageViewPP.fitWidthProperty().divide(2));
            clip.centerYProperty().bind(imageViewPP.fitHeightProperty().divide(2));
            clip.radiusProperty().bind(imageViewPP.fitWidthProperty().divide(2));

            imageViewPP.setClip(clip);

            imageViewPP.setFitWidth(100);
            imageViewPP.setFitHeight(100);

            imageViewPP.setClip(clip);

            String profilePicturePath = selectedFile.getAbsolutePath();
            userDTO.setView(profilePicturePath);
        }
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
