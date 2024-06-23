package lk.ijse.Laptop_Shop_Management.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private int id;
    private String model;
    private int qty;
    private double price;
    private String status;
}
