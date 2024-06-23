package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrder {
    private OrderDTO orderDTO;
    private List<ItemDetailDTO> itemDetailDTO;
    private DeliveryDTO deliveryDTO;
}
