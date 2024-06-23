package lk.ijse.Laptop_Shop_Management.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.StageStyle;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.MainBO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MainFormController {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane iconBar;

    @FXML
    private Button btnConfigurations;

    @FXML
    private Button btnDashboard;

    @FXML
    private ImageView iconDashboard;

    @FXML
    private Button btnCustomer;

    @FXML
    private ImageView iconCustomer;

    @FXML
    private Button btnSupplier;

    @FXML
    private ImageView iconSupplier;

    @FXML
    private Button btnItem;

    @FXML
    private ImageView iconItem;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnSetting;

    @FXML
    private ImageView iconEmployee;

    @FXML
    private Button btnDriver;

    @FXML
    private ImageView iconDriver;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPosition;

    @FXML
    private Text txtDate;

    @FXML
    private Label labelOrderCount;

    @FXML
    private Label labelCustomerCount;

    @FXML
    private Label labelDriverCount;

    @FXML
    private Label labelEmployeeCount;

    @FXML
    private Label labelItemCount;

    @FXML
    private Label labelSupplierCount;

    @FXML
    private ImageView mainImageView;

    @FXML
    private StackPane paneImage;

    @FXML
    private Button btnTrush;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnPurchases;

    @FXML
    private AnchorPane mainForm;

    MainBO mainBO = (MainBO) BOFactory.getBO(BOFactory.BOType.MAIN);

    public static String date;

    public void initialize(){
        labelName.setText(LoginFormController.userDTO.getUserName());
        labelPosition.setText(LoginFormController.userDTO.getType());
        setDate();
        setCounts();
        setPP();
    }

    private void setPP() {
        Image image = new Image("file:" + LoginFormController.userDTO.getView());
        ImageView mainImageView = new ImageView(image);

        Circle clip = new Circle();
        clip.centerXProperty().bind(mainImageView.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(mainImageView.fitHeightProperty().divide(2));
        clip.radiusProperty().bind(mainImageView.fitWidthProperty().divide(2));

        mainImageView.setClip(clip);

        mainImageView.setFitWidth(58);
        mainImageView.setFitHeight(49);

        mainImageView.setClip(clip);

        paneImage.getChildren().add(mainImageView);
    }

    private void setCounts()  {
        try {
            labelOrderCount.setText(String.valueOf(mainBO.getOrderCount()));
            labelCustomerCount.setText(String.valueOf(mainBO.customerCount()));
            labelSupplierCount.setText(String.valueOf(mainBO.supplierCount()));
            labelItemCount.setText(String.valueOf(mainBO.itemCount()));
            labelEmployeeCount.setText(String.valueOf(mainBO.employeeCount()));
            labelDriverCount.setText(String.valueOf(mainBO.driverCount()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setDate() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        MainFormController.date = String.valueOf(localDate);
        txtDate.setText(MainFormController.date);
    }

    @FXML
    void customerAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnCustomer);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void dashboardAction(ActionEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml")));
            Stage stage = (Stage) this.mainAnchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void configurationsAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnConfigurations);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/configuration_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void driverAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnDriver);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/driver_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void employeeAction(ActionEvent event) {
        if (LoginFormController.userDTO.getType().equals("Owner")){
            try {
                getStyle();
                changeButtonStyle(btnEmployee);
                mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION,"You do not have access to this page").show();
        }
    }

    @FXML
    void itemAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnItem);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void logOutAction(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml")));
            Stage stage = (Stage) this.mainAnchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void settingAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnSetting);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/setting_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void supplierAction(ActionEvent event) {
        if (LoginFormController.userDTO.getType().equals("Owner")){
            try {
                getStyle();
                changeButtonStyle(btnSupplier);
                mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION,"You do not have access to this page").show();
        }
    }

    @FXML
    void btnOrderAction(ActionEvent event) {
        try {
            if (mainBO.getOrderCount() != 0){
                loadOrderForm();
            } else {
                loadFirstOrderIdForm();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadFirstOrderIdForm() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/firstOrderId_form.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    private void loadOrderForm() throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.show();
    }
    @FXML
    void btnPurchaseAction(ActionEvent event) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/buying_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.show();
    }

    private void changeButtonStyle(Button button){
        button.setStyle("-fx-background-color: #2B60DE;-fx-background-radius: 18;-fx-border-color: white;-fx-border-radius: 10;-fx-border-width: 10;");
    }
    private void getStyle(){
        String style = "-fx-background-color:  #1E90FF;-fx-background-radius: 18;-fx-border-color: white;-fx-border-radius: 10;-fx-border-width: 10;";
        btnConfigurations.setStyle(style);
        btnCustomer.setStyle(style);
        btnSupplier.setStyle(style);
        btnItem.setStyle(style);
        btnEmployee.setStyle(style);
        btnDriver.setStyle(style);
        btnSetting.setStyle(style);
        btnTrush.setStyle(style);
        btnOrders.setStyle(style);
        btnPurchases.setStyle(style);
    }

    @FXML
    void btnOutOfStokeAction(ActionEvent event) {
        try {
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/outOfStoke_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOrdersAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnOrders);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/orderList_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void emailAction(MouseEvent event) {
        try {
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/email_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnPurchasesAction(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnPurchases);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/buyingList_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnTrush(ActionEvent event) {
        try {
            getStyle();
            changeButtonStyle(btnTrush);
            mainAnchorPane.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/deleteData_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void closeAction(MouseEvent event) {
        Stage stage = (Stage) mainForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) mainForm.getScene().getWindow();
        stage.setIconified(true);
    }

}
