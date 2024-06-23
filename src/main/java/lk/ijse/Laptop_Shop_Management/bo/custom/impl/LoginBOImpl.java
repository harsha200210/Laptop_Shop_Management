package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.LoginBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.UserDAO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;
import lk.ijse.Laptop_Shop_Management.entity.User;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {
    UserDAO userDAO;

    public LoginBOImpl() {
        this.userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    }

    @Override
    public boolean checkUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(userDTO.getUserName(),userDTO.getPassword());
        return userDAO.checkUser(user);
    }
}
