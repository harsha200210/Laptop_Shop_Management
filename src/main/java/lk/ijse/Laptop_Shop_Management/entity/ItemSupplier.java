package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSupplier {
    private int itemId;
    private int supplierId;
    private Date date;
    private int qty;
    private double price;
}
