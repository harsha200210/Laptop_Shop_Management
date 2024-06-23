package lk.ijse.Laptop_Shop_Management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private int id;
    private String name;
    private String nic;
    private String address;
    private String email;
    private int tel;
    private String status;

}
