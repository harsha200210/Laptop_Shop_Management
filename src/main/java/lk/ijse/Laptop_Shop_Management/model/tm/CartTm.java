package lk.ijse.Laptop_Shop_Management.model.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartTm {
    private int id;
    private String model;
    private double unitPrice;
    private int qty;
    private double total;
    private Button btnRemove;
}
