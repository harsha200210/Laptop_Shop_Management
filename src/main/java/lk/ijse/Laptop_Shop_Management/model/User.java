package lk.ijse.Laptop_Shop_Management.model;


import javafx.scene.image.Image;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {
    private int id;
    private String userName;
    private String password;
    private String type;
    private String view;
}
