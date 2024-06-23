package lk.ijse.Laptop_Shop_Management.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.OutOfStokeBO;
import lk.ijse.Laptop_Shop_Management.tdm.ItemTm;

public class OutOfStokeFormController {

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<ItemTm> itemTable;

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane outOfStokeAnchorPane;

    OutOfStokeBO outOfStokeBO = (OutOfStokeBO) BOFactory.getBO(BOFactory.BOType.OUTOFSTOKE);

    private ObservableList<ItemTm> list;

    public void initialize(){
        setCellValueFactoryItem();
        loadData();
    }

    private void loadData() {
        try {
            list = outOfStokeBO.outOfStokeItem();
            itemTable.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactoryItem() {
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void backAction(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml")));
            Stage stage = (Stage) this.outOfStokeAnchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    @FXML
    void searchAction(ActionEvent event) {
        itemTable.setItems(list);
        list = itemTable.getItems();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getModel().equals(txtSearch.getText())){
                ObservableList<ItemTm> list1 = FXCollections.observableArrayList();
                list1.add(list.get(i));
                itemTable.setItems(list1);
                return;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "Can't find item !!").show();
    }

}
