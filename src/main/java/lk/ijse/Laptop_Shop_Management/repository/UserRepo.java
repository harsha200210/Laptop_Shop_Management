package lk.ijse.Laptop_Shop_Management.repository;

import javafx.scene.control.Alert;
import lk.ijse.Laptop_Shop_Management.controller.LoginFormController;
import lk.ijse.Laptop_Shop_Management.db.DbConnection;
import lk.ijse.Laptop_Shop_Management.model.User;
import lk.ijse.Laptop_Shop_Management.util.PasswordStorage;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public static boolean save(User user) throws SQLException, FileNotFoundException {
        String sql = "INSERT INTO user (user_type, user_name, password, profile_picture) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,user.getType());
        preparedStatement.setObject(2,user.getUserName());
        preparedStatement.setObject(3,user.getPassword());
        preparedStatement.setObject(4,user.getView());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean checkUser(User user) throws SQLException {
        String sql = "SELECT user_id,password,user_type,profile_picture FROM user WHERE user_name = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,user.getUserName());
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            if (PasswordStorage.checkPassword(user.getPassword(),resultSet.getString("password"))){
                LoginFormController.user.setType(resultSet.getString("user_type"));
                LoginFormController.user.setId(resultSet.getInt("user_id"));
                LoginFormController.user.setView(resultSet.getString("profile_picture"));
                return true;
            }else {
                new Alert(Alert.AlertType.INFORMATION, "sorry! password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
        return false;
    }

    public static String getOwner() throws SQLException {
        String sql = "SELECT user_name FROM user WHERE user_type = 'Owner'";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET user_name = ? , password = ? , profile_picture = ? WHERE user_id = ?";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setObject(1,user.getUserName());
        preparedStatement.setObject(2,user.getPassword());
        preparedStatement.setObject(3,user.getView());
        preparedStatement.setObject(4,user.getId());
        return preparedStatement.executeUpdate() > 0;
    }
}
