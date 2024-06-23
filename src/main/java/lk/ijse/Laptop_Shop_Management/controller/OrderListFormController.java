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
import javafx.scene.input.MouseEvent;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.OrderListBO;
import lk.ijse.Laptop_Shop_Management.tdm.OrderItemTm;
import lk.ijse.Laptop_Shop_Management.tdm.OrderListTm;

public class OrderListFormController {

    @FXML
    private TableView<OrderItemTm> OrderItemTable;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<OrderListTm> orderTable;

    @FXML
    private TextField txtSearch;

    OrderListBO orderListBO = (OrderListBO) BOFactory.getBO(BOFactory.BOType.ORDERLIST);

    private ObservableList<OrderListTm> orderList;
    private ObservableList<OrderItemTm> itemList;

    public void initialize(){
        setCellValueFactory();
        loadOrderDetails();
        loadItemDetails();
    }

    private void loadItemDetails() {
        try {
            itemList = orderListBO.getItems();
            OrderItemTable.setItems(itemList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadOrderDetails() {
        try {
            orderList = orderListBO.getOrders();
            orderTable.setItems(orderList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    @FXML
    void goAction(ActionEvent event) {
        searchAction(event);
    }

    @FXML
    void searchAction(ActionEvent event) {
        for (int i = 0; i < itemList.size(); i++) {
            if ((" " + txtSearch.getText()).equals(itemList.get(i).getOrderId())){
                ObservableList<OrderItemTm> list = FXCollections.observableArrayList();
                list.add(new OrderItemTm(itemList.get(i).getOrderId(),itemList.get(i).getItemId(),itemList.get(i).getQty()));
                OrderItemTable.setItems(list);
            }
        }

        for (int i = 0; i < orderList.size(); i++) {
            if ((" " + txtSearch.getText()).equals(orderList.get(i).getOrderId())){
                ObservableList<OrderListTm> list = FXCollections.observableArrayList();
                list.add(new OrderListTm(orderList.get(i).getOrderId(),orderList.get(i).getCustomerId(),orderList.get(i).getDate(),orderList.get(i).getPrice(),orderList.get(i).getUserId()));
                orderTable.setItems(list);
                return;
            }
        }
        new Alert(Alert.AlertType.ERROR,"Can't Find Order Id !!").show();
    }

    @FXML
    void orderTableAction(MouseEvent event) {
        OrderListTm selectedItem = orderTable.getSelectionModel().getSelectedItem();

        try {
            ObservableList<OrderItemTm> list = orderListBO.getItem(selectedItem.getOrderId());
            OrderItemTable.setItems(list);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
