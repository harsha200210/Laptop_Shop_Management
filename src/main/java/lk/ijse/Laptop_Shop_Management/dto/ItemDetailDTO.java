package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDetailDTO {
    private int itemId;
    private String orderId;
    private int qty;

}
