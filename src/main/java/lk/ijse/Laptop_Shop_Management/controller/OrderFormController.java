package lk.ijse.Laptop_Shop_Management.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.OrderBO;
import lk.ijse.Laptop_Shop_Management.dto.*;
import lk.ijse.Laptop_Shop_Management.tdm.CartTm;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.Laptop_Shop_Management.util.QrScanner;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class OrderFormController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> driverNameBox;

    @FXML
    private Label labelCashier;

    @FXML
    private Label labelCustomerName;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelNetTotal;

    @FXML
    private Label labelOnHandQty;

    @FXML
    private Label labelOrderID;

    @FXML
    private Label labelOwner;

    @FXML
    private TextField txtQty;

    @FXML
    private Label labelUnitPrice;

    @FXML
    private TableView<CartTm> orderTable;

    @FXML
    private Pane paneDriverID;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private Label labelModel;

    @FXML
    private Pane deliveryPane;

    @FXML
    private Label labelDeliveryCharge;

    @FXML
    private CheckBox outCheckBox;

    @FXML
    private CheckBox insideCheckBox;

    @FXML
    private CheckBox noCheckBox;

    @FXML
    private CheckBox yesCheckBox;

    @FXML
    private Button btnScan;

    @FXML
    private TextField txtItemId;

    OrderBO orderBO = (OrderBO) BOFactory.getBO(BOFactory.BOType.ORDER);

    private ItemDTO itemDTO = new ItemDTO();
    public static OrderDTO orderDTO = new OrderDTO();

    private ObservableList<CartTm> list = FXCollections.observableArrayList();
    private CustomerDTO customer;

    public void initialize(){
        paneDriverID.setVisible(false);
        deliveryPane.setVisible(false);
        getOwnerAndCashier();
        getOrderIDAndDate();
        setCellValueFactory();
    }

    private void getOrderIDAndDate() {
        labelDate.setText(" " + MainFormController.date);
        try {
            int count = orderBO.getOrderCount();
            if (count != 0){
                String id = orderBO.getFirstOrderId();
                labelOrderID.setText(nextOrderId(id,count));
            } else {
                if (orderBO.setFirstOrderId(FirstOrderIdFormController.firstOrderId)){
                    labelOrderID.setText(FirstOrderIdFormController.firstOrderId);
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private String nextOrderId(String id, int count) {
        String[] split = id.split(String.valueOf(1),2);
        String nextOrderId = split[0] + (count+1);
        try {
            nextOrderId = nextOrderId + split[1];
        } catch (Exception e){
        }
        return nextOrderId;
    }

    private void getOwnerAndCashier() {
        try {
            labelOwner.setText(" " + orderBO.getOwner());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
            labelCashier.setText(" " + LoginFormController.userDTO.getUserName());
    }

    @FXML
    void addNewCustomerAction(ActionEvent event) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) orderForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void customerTelAction(ActionEvent event) {
        if (isValiedCustomerTel()){
            try {
                customer = orderBO.getCustomerName(txtCustomerTel.getText());
                if (customer.getName() != null){
                    labelCustomerName.setText(" " + customer.getName());
                } else {
                    labelCustomerName.setText("");
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnAddToCartAction(ActionEvent event) {
        if (isValied()){
            loadData();
            setNetTotal();
            clearData();
            btnScan.requestFocus();
        }
    }

    private void clearData() {
        txtQty.clear();
        labelModel.setText("");
        labelOnHandQty.setText("");
        labelUnitPrice.setText("");
        txtItemId.clear();
    }

    private void setNetTotal() {
        double total = 0;
        try {
            total = Double.parseDouble(labelDeliveryCharge.getText());
        } catch (Exception e){
        }

        for (int i = 0;i < orderTable.getItems().size();i++){
            total += (Double) colTotal.getCellData(i);
        }
        labelNetTotal.setText(String.valueOf(total));
    }

    private void loadData() {
        Image img = new Image("/icon/icons8-minimize-48.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setFitWidth(15);
        Button button = new Button();
        button.setStyle("-fx-background-color: white;");
        button.setGraphic(view);

        button.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = orderTable.getSelectionModel().getSelectedIndex();
                try{
                    list.remove(selectedIndex);
                } catch (Exception exception){
                    new Alert(Alert.AlertType.INFORMATION,"Select Column And Remove !!").show();
                    return;
                }
                orderTable.refresh();
                setNetTotal();
            }
        });

        int qty = 0;
        try {
            qty = Integer.parseInt(txtQty.getText());
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return;
        }

        if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(labelOnHandQty.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Out of Stoke.Please Check Quantity !!").show();
            return;
        }

        for (int i = 0; i < orderTable.getItems().size(); i++){
            if (itemDTO.getId() == ((Integer)colId.getCellData(i))){
                CartTm tm = list.get(i);

                qty += tm.getQty();

                tm.setQty(qty);
                tm.setTotal(qty * itemDTO.getPrice());

                orderTable.refresh();
                return;
            }
        }

        CartTm tm = new CartTm(itemDTO.getId(), itemDTO.getModel(), itemDTO.getPrice(), qty, (itemDTO.getPrice() * qty), button);
        list.add(tm);
        orderTable.setItems(list);
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnCloseAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    void btnPlaceToOrderAction(ActionEvent event) {
        if (isValiedCustomerTel()){
            orderDTO = new OrderDTO(labelOrderID.getText(), Date.valueOf(LocalDate.now()),Double.parseDouble(labelNetTotal.getText()),customer.getId(),LoginFormController.userDTO.getId());

            List<ItemDetailDTO> itemList = new ArrayList<>();

            for (int i = 0; i < orderTable.getItems().size(); i++) {
                itemList.add(new ItemDetailDTO(orderTable.getItems().get(i).getId(),labelOrderID.getText(),orderTable.getItems().get(i).getQty()));
            }

            DeliveryDTO deliveryDTO = null;
            int driverId = 0;

            try {
                driverId = orderBO.getDriverId(driverNameBox.getValue());
            } catch (Exception e) {
            }

            if (!labelDeliveryCharge.getText().equals("")){
                deliveryDTO = new DeliveryDTO(Double.parseDouble(labelDeliveryCharge.getText()),labelOrderID.getText(),driverId);
            }

            PlaceOrder placeOrder = new PlaceOrder(orderDTO,itemList, deliveryDTO);

            try {
                if (orderBO.placeOrder(placeOrder)){
                    //new Alert(Alert.AlertType.CONFIRMATION, "Order Placed Successfully !!").show();
                    navigatePayment();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully !!").show();
                }
            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private void navigatePayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));
            Parent node = loader.load();
            PaymentFormController controller = loader.getController();
            controller.setOrderFormController(this);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(node));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void noAction(ActionEvent event) {
        noCheckBox.setSelected(true);
        paneDriverID.setVisible(false);
        deliveryPane.setVisible(false);
        yesCheckBox.setSelected(false);
    }

    @FXML
    void qtyAction(ActionEvent event) {
        btnAddToCartAction(event);
    }

    @FXML
    void yesAction(ActionEvent event) {
        yesCheckBox.setSelected(true);
        paneDriverID.setVisible(true);
        driverNameAdd();
        deliveryPane.setVisible(true);
        noCheckBox.setSelected(false);
    }

    private void driverNameAdd() {
        try {
            ObservableList<String> list = orderBO.getDriverName();
            driverNameBox.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    public void btnNewOrder(ActionEvent event) {
        try {
            exitStage();
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml")));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void exitStage() {
        Stage stage = (Stage) orderForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void txtItemIdAction(ActionEvent event) {
        try {
            itemDTO = orderBO.getItem(Integer.parseInt(txtItemId.getText()));
            if (itemDTO != null) {
                labelUnitPrice.setText(String.valueOf(itemDTO.getPrice()));
                labelOnHandQty.setText(String.valueOf(itemDTO.getQty()));
                labelModel.setText(itemDTO.getModel());
                txtQty.requestFocus();
            } else {
                clearData();
                btnScan.requestFocus();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        txtQty.requestFocus();
    }

    @FXML
    void btnScanAction(ActionEvent event) {
        String qrCodeData = QrScanner.startScanning();
        if (qrCodeData != null) {
            Platform.runLater(() -> {
                txtItemId.setText(qrCodeData);
                try {
                    itemDTO = orderBO.getItem(Integer.parseInt(qrCodeData));
                    if (itemDTO != null){
                        labelUnitPrice.setText(String.valueOf(itemDTO.getPrice()));
                        labelOnHandQty.setText(String.valueOf(itemDTO.getQty()));
                        labelModel.setText(itemDTO.getModel());
                        txtQty.requestFocus();
                    } else {
                        clearData();
                        btnScan.requestFocus();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    void insideColomboAction(ActionEvent event) {
        insideCheckBox.setSelected(true);
        outCheckBox.setSelected(false);
        try {
            labelDeliveryCharge.setText(String.valueOf(orderBO.getInsideChange()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void outOfColomboAction(ActionEvent event) {
        outCheckBox.setSelected(true);
        insideCheckBox.setSelected(false);
        try {
            labelDeliveryCharge.setText(String.valueOf(orderBO.getOutSideCharge()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public boolean isValiedCustomerTel() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL, txtCustomerTel)) return false;
        return true;
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY, txtQty)) return false;
        return true;
    }

    @FXML
    void txtCustomerTelAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL,txtCustomerTel);
    }

    @FXML
    void txtQtyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtQty);
    }
}
