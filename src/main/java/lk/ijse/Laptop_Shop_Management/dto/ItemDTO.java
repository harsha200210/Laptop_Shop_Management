package lk.ijse.Laptop_Shop_Management.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemDTO {
    private int id;
    private String model;
    private int qty;
    private double price;
    private String status;
}
