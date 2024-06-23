package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryDTO {
    private double deliveryChage;
    private String orderId;
    private int driverId;
}
