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
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.ItemBO;
import lk.ijse.Laptop_Shop_Management.dao.custom.impl.ItemDAOImpl;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;
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

    ItemBO itemBO = (ItemBO) BOFactory.getBO(BOFactory.BOType.ITEM);

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
            if (itemBO.checkId(txtModel.getText())){
                if (itemBO.delete(txtModel.getText())){
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
                ItemDTO itemDTO = getValues();
                if (itemBO.save(itemDTO)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Saved!").show();
                    loadAllItem();
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    private ItemDTO getValues() {
        return new ItemDTO(0,txtModel.getText(),Integer.parseInt(txtQty.getText()),Double.parseDouble(txtPrice.getText()),"");
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        try {
            ItemDTO itemDTO = getValues();
            if (itemBO.checkId(itemDTO.getModel())){
                updateValues(itemDTO);
                if (itemBO.update()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Item Updated!").show();
                    setNullValue();
                    loadAllItem();
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void updateValues(ItemDTO itemDTO) {
        if (!itemDTO.getModel().equals("") && !itemDTO.getModel().equals(ItemDAOImpl.item.getModel())){
            ItemDAOImpl.item.setModel(itemDTO.getModel());
        }
        if (itemDTO.getQty() != 0 && itemDTO.getQty() != ItemDAOImpl.item.getQty()){
            ItemDAOImpl.item.setQty(itemDTO.getQty());
        }
        if (itemDTO.getPrice() != 0.0 && itemDTO.getPrice() != ItemDAOImpl.item.getPrice()){
            ItemDAOImpl.item.setPrice(itemDTO.getPrice());
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    private void setTable(ItemDTO itemDTO) {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        ItemTm tm = new ItemTm(itemDTO.getModel(), itemDTO.getQty(), itemDTO.getPrice());
        obList.add(tm);
        itemTable.setItems(obList);
    }

    private void setCellValueFactory() {
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void setValues(ItemDTO itemDTO) {
        txtModel.setText(itemDTO.getModel());
        txtQty.setText(String.valueOf(itemDTO.getQty()));
        txtPrice.setText(String.valueOf(itemDTO.getPrice()));
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                ItemDTO itemDTO = itemBO.search(txtSearch.getText());
                if (itemDTO != null){
                    setValues(itemDTO);
                    setTable(itemDTO);
                } else {
                    setNullValue();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    private void loadAllItem(){
        try {
            ObservableList<ItemDTO> items = itemBO.getItem();
            for (ItemDTO itemDTO : items){
                itemTable.getItems().add(new ItemTm(itemDTO.getModel(), itemDTO.getQty(), itemDTO.getPrice()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk.ijse.Laptop_Shop_Management/generateQr_form.fxml"));
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
