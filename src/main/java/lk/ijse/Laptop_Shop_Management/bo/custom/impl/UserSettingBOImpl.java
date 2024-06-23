package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.UserSettingBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.UserDAO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;
import lk.ijse.Laptop_Shop_Management.entity.User;

import java.sql.SQLException;

public class UserSettingBOImpl implements UserSettingBO {
    UserDAO userDAO;

    public UserSettingBOImpl() {
        this.userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(userDTO.getId(),userDTO.getUserName(),userDTO.getPassword(),userDTO.getView());
        return userDAO.updateUser(user);
    }
}
