package lk.ijse.Laptop_Shop_Management.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierItemTm {
    private int itemId;
    private int supplierId;
    private Date date;
    private int qty;
    private double price;
}
