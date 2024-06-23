package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.UserSettingBO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;
import lk.ijse.Laptop_Shop_Management.util.PasswordStorage;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.io.File;

public class UserSettingFormController {

    @FXML
    private ImageView imageViewPP;

    @FXML
    private StackPane paneImage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane userSettingForm;

    UserSettingBO userSettingBO = (UserSettingBO) BOFactory.getBO(BOFactory.BOType.USERSETTING);

    public void initialize() {
        setPP();
        txtUserName.setText(LoginFormController.userDTO.getUserName());
        txtPassword.setText(LoginFormController.userDTO.getPassword());
    }

    private void setPP() {
        Image image = new Image("file:" + LoginFormController.userDTO.getView());
        imageViewPP = new ImageView(image);

        Circle clip = new Circle();
        clip.centerXProperty().bind(imageViewPP.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(imageViewPP.fitHeightProperty().divide(2));
        clip.radiusProperty().bind(imageViewPP.fitWidthProperty().divide(2));

        imageViewPP.setClip(clip);

        imageViewPP.setFitWidth(100);
        imageViewPP.setFitHeight(100);

        imageViewPP.setClip(clip);

        paneImage.getChildren().add(imageViewPP);
    }

    @FXML
    void btnEditAction(ActionEvent event) {
        getNewPP();
    }

    private void getNewPP(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null  && selectedFile.exists()) {
            Image image = new Image(selectedFile.toURI().toString());
            imageViewPP.setImage(image);

            String profilePicturePath = selectedFile.getAbsolutePath();
            LoginFormController.userDTO.setView(profilePicturePath);
        }
    }
    @FXML
    void btnSaveAction(ActionEvent event) {
        if (isValied()){
            if (!txtUserName.getText().equals("")){
                LoginFormController.userDTO.setUserName(txtUserName.getText());
            }
            if (!txtPassword.getText().equals("")){
                LoginFormController.userDTO.setPassword(txtPassword.getText());
            }

            try {
                UserDTO userDTO = LoginFormController.userDTO;
                userDTO.setPassword(getPassword());
                if (userSettingBO.updateUser(userDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION, "User Updated !!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private String getPassword(){
        return PasswordStorage.hashPassword(LoginFormController.userDTO.getPassword());
    }

    @FXML
    void paneImageAction(MouseEvent event) {
        getNewPP();
    }

    @FXML
    void userNameAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PASSWORD, txtPassword)) return false;
        return true;
    }

    @FXML
    void txtUserNameAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtUserName);
    }

    @FXML
    void txtPasswordAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PASSWORD,txtPassword);
    }

}
