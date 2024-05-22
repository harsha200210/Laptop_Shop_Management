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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Laptop_Shop_Management.model.*;
import lk.ijse.Laptop_Shop_Management.model.tm.CartTm;
import lk.ijse.Laptop_Shop_Management.repository.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.SQLException;
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

    private Item item = new Item();

    public static Order order = new Order();

    private ObservableList<CartTm> list = FXCollections.observableArrayList();
    private Customer customer;

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
            int count = OrderRepo.getOrderCount();
            if (count != 0){
                String id = ConfigurationRepo.getFirstOrderId();
                labelOrderID.setText(nextOrderId(id,count));
            } else {
                if (ConfigurationRepo.setFirstOrderId(FirstOrderIdFormController.firstOrderId)){
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
            labelOwner.setText(" " + UserRepo.getOwner());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
            labelCashier.setText(" " + LoginFormController.user.getUserName());
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
                customer = CustomerRepo.getCustomerName(txtCustomerTel.getText());
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
        txtQty.setText("");
        labelModel.setText("");
        labelOnHandQty.setText("");
        labelUnitPrice.setText("");
        txtItemId.setText("");
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
            if (item.getId() == ((Integer)colId.getCellData(i))){
                CartTm tm = list.get(i);

                qty += tm.getQty();

                tm.setQty(qty);
                tm.setTotal(qty * item.getPrice());

                orderTable.refresh();
                return;
            }
        }

        CartTm tm = new CartTm(item.getId(), item.getModel(), item.getPrice(), qty, (item.getPrice() * qty), button);
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
            order = new Order(labelOrderID.getText(), Date.valueOf(LocalDate.now()),Double.parseDouble(labelNetTotal.getText()),customer.getId(),LoginFormController.user.getId());

            List<ItemDetail> itemList = new ArrayList<>();

            for (int i = 0; i < orderTable.getItems().size(); i++) {
                itemList.add(new ItemDetail(orderTable.getItems().get(i).getId(),labelOrderID.getText(),orderTable.getItems().get(i).getQty()));
            }

            Delivery delivery = null;
            int driverId = 0;

            try {
                driverId = DriverRepo.getDriverId(driverNameBox.getValue());
            } catch (Exception e) {
            }

            if (!labelDeliveryCharge.getText().equals("")){
                delivery = new Delivery(Double.parseDouble(labelDeliveryCharge.getText()),labelOrderID.getText(),driverId);
            }

            PlaceOrder placeOrder = new PlaceOrder(order,itemList,delivery);

            try {
                if (PlaceToOrderRepo.placeOrder(placeOrder)){
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
            ObservableList<String> list = DriverRepo.getDriverName();
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
            item = ItemRepo.getItem(Integer.parseInt(txtItemId.getText()));
            if (item != null) {
                labelUnitPrice.setText(String.valueOf(item.getPrice()));
                labelOnHandQty.setText(String.valueOf(item.getQty()));
                labelModel.setText(item.getModel());
            } else {
                txtItemId.setText("");
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        txtQty.requestFocus();
    }

    @FXML
    void btnScanAction(ActionEvent event) {
        startScanning();
        txtQty.requestFocus();
    }

    private void startScanning() {
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open();
            if (webcam.isOpen()){
                btnScan.setDisable(true);
            }

            new Thread(() -> {
                while (true) {
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        try {
                            String qrCodeData = readQRCode(image);
                            if (qrCodeData != null) {
                                Platform.runLater(() -> {
                                    txtItemId.setText(qrCodeData);
                                    try {
                                        item = ItemRepo.getItem(Integer.parseInt(qrCodeData));
                                        if (item != null){
                                            labelUnitPrice.setText(String.valueOf(item.getPrice()));
                                            labelOnHandQty.setText(String.valueOf(item.getQty()));
                                            labelModel.setText(item.getModel());
                                        } else {
                                            txtItemId.setText("");
                                        }
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                webcam.close();
                                if (!webcam.isOpen()){
                                    btnScan.setDisable(false);
                                }
                                return; // Stop scanning once QR code is found
                            }
                        } catch (NotFoundException e) {
                            // QR code not found in the current frame, continue scanning
                        }
                    }
                }
            }).start();
        } else {
            System.err.println("No webcam detected!");
        }
    }

    private String readQRCode(BufferedImage image) throws NotFoundException {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        MultiFormatReader reader = new MultiFormatReader();
        Result result = reader.decode(binaryBitmap, hints);

        return result.getText();
    }

    @FXML
    void insideColomboAction(ActionEvent event) {
        insideCheckBox.setSelected(true);
        outCheckBox.setSelected(false);
        try {
            labelDeliveryCharge.setText(String.valueOf(ConfigurationRepo.getInsideChange()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void outOfColomboAction(ActionEvent event) {
        outCheckBox.setSelected(true);
        insideCheckBox.setSelected(false);
        try {
            labelDeliveryCharge.setText(String.valueOf(ConfigurationRepo.getOutSideChage()));
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
