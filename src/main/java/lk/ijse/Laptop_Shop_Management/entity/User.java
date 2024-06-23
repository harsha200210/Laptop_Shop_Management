package lk.ijse.Laptop_Shop_Management.entity;


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

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String type, String view) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.view = view;
    }

    public User(int id, String userName, String password, String view) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.view = view;
    }
}
