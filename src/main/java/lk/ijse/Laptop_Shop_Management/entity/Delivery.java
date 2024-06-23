package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Delivery {
    private int deliveryId;
    private double deliveryCharge;
    private String orderId;
    private int driverId;

    public Delivery(double deliveryCharge, String orderId, int driverId) {
        this.deliveryCharge = deliveryCharge;
        this.orderId = orderId;
        this.driverId = driverId;
    }
}
