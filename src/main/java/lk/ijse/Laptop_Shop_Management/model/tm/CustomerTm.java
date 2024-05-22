package lk.ijse.Laptop_Shop_Management.model.tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTm {
    private String name;
    private String nic;
    private String address;
    private String email;
    private int tel;
}
