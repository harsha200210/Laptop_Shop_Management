package lk.ijse.Laptop_Shop_Management.model.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemTm {
    private String model;
    private int qty;
    private double price;
}
