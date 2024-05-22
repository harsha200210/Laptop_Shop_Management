package lk.ijse.Laptop_Shop_Management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Delivery {
    private double deliveryChage;
    private String orderId;
    private int driverId;
}
