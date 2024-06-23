package lk.ijse.Laptop_Shop_Management.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemTm {
    private String orderId;
    private int itemId;
    private int qty;
}
