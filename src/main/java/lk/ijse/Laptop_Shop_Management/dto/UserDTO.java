package lk.ijse.Laptop_Shop_Management.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserDTO {
    private int id;
    private String userName;
    private String password;
    private String type;
    private String view;
}
