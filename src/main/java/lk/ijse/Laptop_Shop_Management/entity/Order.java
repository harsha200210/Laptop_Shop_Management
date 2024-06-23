package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String orderId;
    private Date date;
    private double price;
    private int customerId;
    private int userId;
}
