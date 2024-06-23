package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDetail {
    private int itemId;
    private String orderId;
    private int qty;

}
