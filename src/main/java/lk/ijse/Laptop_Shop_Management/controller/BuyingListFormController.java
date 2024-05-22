package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Laptop_Shop_Management.model.tm.SupplierItemTm;
import lk.ijse.Laptop_Shop_Management.repository.ItemSupplierRepo;
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

    private ObservableList<SupplierItemTm> list;

    public void initialize(){
        setCellValueFactory();
        loadSupplierItemData();
    }

    private void loadSupplierItemData() {
        try {
            list = ItemSupplierRepo.getSupplierItem();
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
                ObservableList<SupplierItemTm> list = ItemSupplierRepo.getSupplierItem(Integer.parseInt(txtSearch.getText()));
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
