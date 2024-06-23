package lk.ijse.Laptop_Shop_Management.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import lk.ijse.Laptop_Shop_Management.bo.BOFactory;
import lk.ijse.Laptop_Shop_Management.bo.custom.GenerateQrBO;
import lk.ijse.Laptop_Shop_Management.bo.custom.ItemBO;
import lk.ijse.Laptop_Shop_Management.bo.custom.impl.ItemBOImpl;
import lk.ijse.Laptop_Shop_Management.dto.ItemDTO;
import lombok.Setter;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class GenerateQrFormController {

    @FXML
    private Pane savePane;

    @FXML
    private TextField txtModel;

    @FXML
    private Label labelFilePath;

    @Setter
    private ItemFormController itemFormController;

    GenerateQrBO generateQrBO = (GenerateQrBO) BOFactory.getBO(BOFactory.BOType.GENERATEQR);

    ItemBO itemBO = new ItemBOImpl();

    private ItemDTO itemDTO;

    public void initialize(){
        savePane.setVisible(false);
        try {
            String path  = generateQrBO.getFilePath();
            if (path != null) {
                labelFilePath.setText(path);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void backAction(MouseEvent event) {
        try {
            itemFormController.itemForm.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("/view/item_form.fxml")));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnGenerateQrAction(ActionEvent event) {
        savePane.setVisible(false);
        try {
            itemDTO = itemBO.search(txtModel.getText());
            if (itemDTO != null) {
                savePane.setVisible(true);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtModelAction(ActionEvent event) {
        btnGenerateQrAction(event);
    }

    @FXML
    void btnSaveImageAction(ActionEvent event) {
        saveQrImage();
    }

    @FXML
    void txtQrNameAction(ActionEvent event) {
        saveQrImage();
    }

    private void saveQrImage(){
        try {
            String filePath = generateQrBO.getFilePath();
            String QR_CODE_IMAGE_PATH = filePath + "/" + itemDTO.getModel() + ".png";
            String text = String.valueOf(itemDTO.getId()); // Content for the QR code
            int width = 300;
            int height = 300;
            String format = "png";

            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
            Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);
            new Alert(Alert.AlertType.CONFIRMATION,"QR Code saved successfully.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        savePane.setVisible(false);
    }

    @FXML
    void btnChoosePathAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory Path");

        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();
            System.out.println(directoryPath);
            labelFilePath.setText(directoryPath);
            try {
                if (generateQrBO.getCount() == 1){
                    if (generateQrBO.updateFilePath(directoryPath)){
                        labelFilePath.setText(directoryPath);
                    }
                } else {
                    if (generateQrBO.addFilePath(directoryPath)){
                        labelFilePath.setText(directoryPath);
                    }
                }
            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }

        }
    }

}
