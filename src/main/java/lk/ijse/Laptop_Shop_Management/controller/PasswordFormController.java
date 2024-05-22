package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class PasswordFormController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label labelName;

    @FXML
    private StackPane paneImage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private AnchorPane passwordForm;

    public static boolean isUser = false;

    public void initialize(){
        setPP();
        labelName.setText(LoginFormController.user.getUserName());

    }

    private void setPP() {
        Image image = new Image("file:" + LoginFormController.user.getView());
        imageView = new ImageView(image);

        Circle clip = new Circle();
        clip.centerXProperty().bind(imageView.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(imageView.fitHeightProperty().divide(2));
        clip.radiusProperty().bind(imageView.fitWidthProperty().divide(2));

        imageView.setClip(clip);

        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        imageView.setClip(clip);

        paneImage.getChildren().add(imageView);
    }

    @FXML
    void txtPasswordAction(ActionEvent event) {
        if (txtPassword.getText().equals(LoginFormController.user.getPassword())){
            isUser = true;
        } else {
            new Alert(Alert.AlertType.WARNING,"Incorrect Password !!").show();
            isUser = false;
        }
        Stage stage = (Stage) this.passwordForm.getScene().getWindow();
        stage.close();
    }

}
