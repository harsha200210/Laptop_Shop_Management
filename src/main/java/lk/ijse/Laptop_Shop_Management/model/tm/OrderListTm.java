package lk.ijse.Laptop_Shop_Management.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderListTm {
    private String orderId;
    private int customerId;
    private Date date;
    private double price;
    private int userId;
}
