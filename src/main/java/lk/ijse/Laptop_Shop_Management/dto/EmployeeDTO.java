package lk.ijse.Laptop_Shop_Management.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDTO {
    private int id;
    private String name;
    private String nic;
    private String address;
    private String email;
    private int tel;
    private int user_id;
    private String status;
}
