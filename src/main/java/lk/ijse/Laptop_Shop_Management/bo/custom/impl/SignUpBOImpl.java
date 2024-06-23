package lk.ijse.Laptop_Shop_Management.bo.custom.impl;

import lk.ijse.Laptop_Shop_Management.bo.custom.SignUpBO;
import lk.ijse.Laptop_Shop_Management.dao.DAOFactory;
import lk.ijse.Laptop_Shop_Management.dao.custom.UserDAO;
import lk.ijse.Laptop_Shop_Management.dto.UserDTO;
import lk.ijse.Laptop_Shop_Management.entity.User;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SignUpBOImpl implements SignUpBO {
    UserDAO userDAO;

    public SignUpBOImpl() {
        this.userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    }

    @Override
    public boolean save(UserDTO userDTO) throws SQLException, FileNotFoundException, ClassNotFoundException {
        User user = new User(userDTO.getUserName(),userDTO.getPassword(),userDTO.getType(),userDTO.getView());
        return userDAO.save(user);
    }

}
