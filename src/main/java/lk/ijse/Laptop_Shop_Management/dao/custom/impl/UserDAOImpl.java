package lk.ijse.Laptop_Shop_Management.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.controller.LoginFormController;
import lk.ijse.Laptop_Shop_Management.dao.SQLUtil;
import lk.ijse.Laptop_Shop_Management.dao.custom.UserDAO;
import lk.ijse.Laptop_Shop_Management.entity.User;
import lk.ijse.Laptop_Shop_Management.util.PasswordStorage;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User user) throws SQLException, FileNotFoundException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user (user_type, user_name, password, profile_picture) VALUES (?, ?, ?, ?)",user.getType(),user.getUserName(),user.getPassword(),user.getView());
    }

    @Override
    public boolean checkUser(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT user_id,password,user_type,profile_picture FROM user WHERE user_name = ?",user.getUserName());

        if (resultSet.next()){
            if (PasswordStorage.checkPassword(user.getPassword(),resultSet.getString("password"))){
                LoginFormController.userDTO.setType(resultSet.getString("user_type"));
                LoginFormController.userDTO.setId(resultSet.getInt("user_id"));
                LoginFormController.userDTO.setView(resultSet.getString("profile_picture"));
                return true;
            }else {
                new Alert(Alert.AlertType.INFORMATION, "sorry! password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
        return false;
    }

    @Override
    public String getOwner() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT user_name FROM user WHERE user_type = 'Owner'");
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET user_name = ? , password = ? , profile_picture = ? WHERE user_id = ?",user.getUserName(),user.getPassword(),user.getView(),user.getId());
    }
}
