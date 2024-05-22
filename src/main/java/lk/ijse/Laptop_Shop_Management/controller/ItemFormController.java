package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Laptop_Shop_Management.model.Item;
import lk.ijse.Laptop_Shop_Management.model.tm.ItemTm;
import lk.ijse.Laptop_Shop_Management.repository.ItemRepo;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class ItemFormController {

    @FXML
    public AnchorPane itemForm;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<ItemTm> itemTable;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    public void initialize(){
        setCellValueFactory();
        loadAllItem();
   }

    @FXML
    void btnClearAction(ActionEvent event) {
        setNullValue();
    }

    private void setNullValue() {
        txtModel.setText("");
        txtQty.setText("");
        txtPrice.setText("");
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        try {
            if (ItemRepo.checkId(txtModel.getText())){
                if (ItemRepo.delete(txtModel.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted!").show();
                    loadAllItem();
                    setNullValue();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        if (isValied()){
            try {
                Item item = getValues();
                if (ItemRepo.save(item)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Saved!").show();
                    loadAllItem();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private Item getValues() {
        return new Item(0,txtModel.getText(),Integer.parseInt(txtQty.getText()),Double.valueOf(txtPrice.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            Item item = getValues();
            if (ItemRepo.checkId(item.getModel())){
                updateValues(item);
                if (ItemRepo.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Updated!").show();
                    setNullValue();
                    loadAllItem();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(Item item) {
        if (!item.getModel().equals("") && !item.getModel().equals(ItemRepo.item.getModel())){
            ItemRepo.item.setModel(item.getModel());
        }
        if (item.getQty() != 0 && item.getQty() != ItemRepo.item.getQty()){
            ItemRepo.item.setQty(item.getQty());
        }
        if (item.getPrice() != 0.0 && item.getPrice() != ItemRepo.item.getPrice()){
            ItemRepo.item.setPrice(item.getPrice());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setTable(Item item) {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        ItemTm tm = new ItemTm(item.getModel(), item.getQty(), item.getPrice());
        obList.add(tm);
        itemTable.setItems(obList);
    }

    private void setCellValueFactory() {
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void setValues(Item item) {
            txtModel.setText(item.getModel());
            txtQty.setText(String.valueOf(item.getQty()));
            txtPrice.setText(String.valueOf(item.getPrice()));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                Item item = ItemRepo.search(txtSearch.getText());
                if (item != null){
                    setValues(item);
                    setTable(item);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllItem(){
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try {
            obList = ItemRepo.getItem();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        itemTable.setItems(obList);
    }

    @FXML
    void getModelAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void getQtyAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void itemTableAction(MouseEvent event) {
        ItemTm selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            txtModel.setText(selectedItem.getModel());
            txtQty.setText(String.valueOf(selectedItem.getQty()));
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
        }
    }

    @FXML
    void generateQrFormAction(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/generateQr_form.fxml"));
            Node node = loader.load();
            GenerateQrFormController controller = loader.getController();
            controller.setItemFormController(this);
            itemForm.getChildren().setAll(node);
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtQty)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtModel)) return false;
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtPrice)) return false;
        return true;
    }

    public boolean searchValid(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtSearch)) return false;
        return true;
    }

    @FXML
    void txtQtyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtQty);
    }

    @FXML
    void txtModelAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtModel);
    }

    @FXML
    void txtPriceAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.PRICE,txtPrice);
    }

    @FXML
    void txtSearchAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.NAME,txtSearch);
    }
}
