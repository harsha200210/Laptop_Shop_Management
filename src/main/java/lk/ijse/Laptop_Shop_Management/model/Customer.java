package lk.ijse.Laptop_Shop_Management.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private int id;
    private String name;
    private String nic;
    private String address;
    private String email;
    private int tel;
    private String status;

}
