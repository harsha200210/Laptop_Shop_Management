package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.BuyingListBO;
import lk.ijse.Laptop_Shop_Management.dto.ItemSupplierDTO;
import lk.ijse.Laptop_Shop_Management.tdm.SupplierItemTm;
import lk.ijse.Laptop_Shop_Management.util.Regex;


public class BuyingListFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableView<SupplierItemTm> supplierItemTable;

    @FXML
    private TextField txtSearch;

    BuyingListBO buyingListBO = (BuyingListBO) BOFactory.getBO(BOFactory.BOType.BUYINGLIST);

    private ObservableList<SupplierItemTm> list = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadSupplierItemData();
    }

    private void loadSupplierItemData() {
        try {
            ObservableList<ItemSupplierDTO> supplierItem = buyingListBO.getSupplierItem();
            for (ItemSupplierDTO itemSupplierDTO : supplierItem) {
                list.add(new SupplierItemTm(itemSupplierDTO.getItemId(), itemSupplierDTO.getSupplierId(), itemSupplierDTO.getDate(), itemSupplierDTO.getQty(), itemSupplierDTO.getPrice()));
            }
            supplierItemTable.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    @FXML
    void searchAction(ActionEvent event) {
        if (searchValid()){
            try {
                supplierItemTable.getItems().clear();
                ObservableList<ItemSupplierDTO> supplierItem = buyingListBO.getSupplierItem(Integer.parseInt(txtSearch.getText()));
                for (ItemSupplierDTO itemSupplierDTO : supplierItem) {
                    supplierItemTable.getItems().add(new SupplierItemTm(itemSupplierDTO.getItemId(), itemSupplierDTO.getSupplierId(), itemSupplierDTO.getDate(), itemSupplierDTO.getQty(), itemSupplierDTO.getPrice()));
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    public boolean searchValid(){
        if (!Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtSearch)) return false;
        return true;
    }

    @FXML
    void txtSearchAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Laptop_Shop_Management.util.TextField.QTY,txtSearch);
    }
}
