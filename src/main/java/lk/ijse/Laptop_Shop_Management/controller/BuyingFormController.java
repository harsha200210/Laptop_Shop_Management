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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.BuyingBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.dto.SupplierDTO;
import lk.ijse.Laptop_Shop_Management.tdm.CartTm;
import lk.ijse.Laptop_Shop_Management.util.QrScanner;
import lk.ijse.Laptop_Shop_Management.util.Regex;

import java.awt.image.BufferedImage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BuyingFormController {

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
    private Label labelDate;

    @FXML
    private Label labelModel;

    @FXML
    private Label labelNetTotal;

    @FXML
    private Label labelOwner;

    @FXML
    private Label labelSupplierName;

    @FXML
    private TableView<CartTm> purchaseTable;

    @FXML
    private TextField txtSupplierTel;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private AnchorPane purchaseForm;

    @FXML
    private Button btnScan;

    @FXML
    private TextField txtItemId;

    BuyingBO buyingBO = (BuyingBO) BOFactory.getBO(BOFactory.BOType.BUYING);

    ItemDTO itemDTO = new ItemDTO();

    private ObservableList<CartTm> list = FXCollections.observableArrayList();

    public void initialize(){
        try {
            labelOwner.setText(" " + buyingBO.getOwner());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        labelDate.setText(" " + MainFormController.date);
    }

    @FXML
    void btnAddToTableAction(ActionEvent event) {
        if (isValied()) {
            loadData();
            setCellValueFactory();
            setNetTotal();
            clearData();
            btnScan.requestFocus();
        }
    }

    private void clearData() {
        txtQty.clear();
        txtUnitPrice.clear();
        labelModel.setText("");
        txtItemId.clear();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setNetTotal() {
        double total = 0;
        for (int i = 0;i < purchaseTable.getItems().size();i++){
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
                int selectedIndex = purchaseTable.getSelectionModel().getSelectedIndex();
                try{
                    list.remove(selectedIndex);
                } catch (Exception exception){
                    new Alert(Alert.AlertType.ERROR,"Select Column And Remove !!").show();
                    return;
                }
                purchaseTable.refresh();
                setNetTotal();
            }
        });

        int qty = 0;
        double unitPrice = 0;
        try {
            qty = Integer.parseInt(txtQty.getText());
            unitPrice = Double.parseDouble(txtUnitPrice.getText());
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return;
        }

        for (int i = 0; i < purchaseTable.getItems().size(); i++){
            if (itemDTO.getId() == ((Integer)colId.getCellData(i)) && (unitPrice == (Double)colUnitPrice.getCellData(i))){
                CartTm tm = list.get(i);

                qty += tm.getQty();

                tm.setQty(qty);
                tm.setTotal(qty * unitPrice);

                purchaseTable.refresh();
                return;
            }
        }

        CartTm tm = new CartTm(itemDTO.getId(), itemDTO.getModel(), unitPrice, qty, (unitPrice * qty), button);
        list.add(tm);
        purchaseTable.setItems(list);
    }

    @FXML
    void btnBuyAction(ActionEvent event) {
        if (isValidSupplierTel()){
            List<ItemSupplierDTO> itemSupplierDTO = new ArrayList<>();

            SupplierDTO supplierDTO = null;
            try {
                supplierDTO = buyingBO.searchSupplier(Integer.parseInt(txtSupplierTel.getText()));
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }

            for (int i = 0;i < purchaseTable.getItems().size();i++){
                itemSupplierDTO.add(new ItemSupplierDTO(purchaseTable.getItems().get(i).getId(), supplierDTO.getId(), Date.valueOf(LocalDate.now()),purchaseTable.getItems().get(i).getQty(),purchaseTable.getItems().get(i).getUnitPrice()));
            }

            try {
                if (buyingBO.buy(itemSupplierDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Bought Successfully !!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Bought Unsuccessfully !!").show();
                }
            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnCloseAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    void btnNewItemAction(ActionEvent event) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml")));
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
        Stage stage = (Stage) this.purchaseForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnNewOrder(ActionEvent event) {
        try {
            exitStage();
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/buying_form.fxml")));
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
        Stage stage = (Stage) purchaseForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void txtSupplierTelAction(ActionEvent event) {
        if (isValidSupplierTel()){
            try {
                SupplierDTO supplierDTO = buyingBO.searchSupplier(Integer.parseInt(txtSupplierTel.getText()));
                labelSupplierName.setText(" " + supplierDTO.getName());
            } catch (Exception e) {
            }
        }
    }

    @FXML
    void txtItemIdAction(ActionEvent event) {
        try {
            itemDTO = buyingBO.getItem(Integer.parseInt(txtItemId.getText()));
            if (itemDTO != null){
                labelModel.setText(itemDTO.getModel());
            } else {
                clearData();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtUnitPrice.requestFocus();
    }

    @FXML
    void btnScanAction(ActionEvent event) {
        String qrCodeData = QrScanner.startScanning();
        if (qrCodeData != null){
            Platform.runLater(() -> {
                txtItemId.setText(qrCodeData);
                try {
                    itemDTO = buyingBO.getItem(Integer.parseInt(qrCodeData));
                    if (itemDTO != null){
                        labelModel.setText(itemDTO.getModel());
                        txtUnitPrice.requestFocus();
                    } else {
                        clearData();
                        btnScan.requestFocus();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            });
        }
    }

    @FXML
    void qtyAction(ActionEvent event) {
        btnAddToTableAction(event);
    }

    @FXML
    void txtUnitPriceAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void btnNewSupplierAction(ActionEvent event) {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        closeStage();
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE, txtUnitPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY, txtQty)) return false;
        return true;
    }

    public boolean isValidSupplierTel(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL, txtSupplierTel)) return false;
        return true;
    }

    @FXML
    void txtTelAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.TEL,txtSupplierTel);
    }

    @FXML
    void priceAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtUnitPrice);
    }

    @FXML
    void txtQtyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtQty);
    }

}
