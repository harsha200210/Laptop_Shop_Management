package lk.ijse.Laptop_Shop_Management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class SettingFormController {

    @FXML
    private AnchorPane changePane;

    @FXML
    private Line lineUserSetting;

    @FXML
    private AnchorPane settingAnchorPane;


    public void initialize(){

    }

    @FXML
    void userSettingAction(MouseEvent event) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/password_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();

        NavigateUserSetting(PasswordFormController.isUser);

    }

    private void NavigateUserSetting(boolean navigate){
        if (navigate) {
            removeStyle();
            changeStyleLine(lineUserSetting);
            try {
                changePane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/userSetting_form.fxml")));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void changeStyleLine(Line line){
        line.setStyle("-fx-stroke: #1E90FF;");
    }

    private void removeStyle(){
        String style = "-fx-stroke: white;";
        lineUserSetting.setStyle(style);
    }
}
