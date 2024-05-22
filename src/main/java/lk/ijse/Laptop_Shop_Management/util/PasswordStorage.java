package lk.ijse.Laptop_Shop_Management.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptDecryptUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
}
