package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSupplierDTO {
    private int itemId;
    private int supplierId;
    private Date date;
    private int qty;
    private double price;
}
