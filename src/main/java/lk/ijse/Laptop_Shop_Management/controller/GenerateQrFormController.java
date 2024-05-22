package lk.ijse.Laptop_Shop_Management.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GenarateQrFormController {

    private ItemFormController itemFormController;

    public void setItemFormController(ItemFormController itemFormController) {
        this.itemFormController = itemFormController;
    }

    @FXML
    void backAction(MouseEvent event) {
        try {
            itemFormController.itemForm.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/view/item_form.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
