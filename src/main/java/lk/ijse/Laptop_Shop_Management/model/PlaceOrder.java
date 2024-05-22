package lk.ijse.Laptop_Shop_Management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrder {
    private Order order;
    private List<ItemDetail> itemDetail;
    private Delivery delivery;
}
