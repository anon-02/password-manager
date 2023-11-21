package com.example.passwordmanager.Model;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class LoginService {
    private UserDAOImpl userDAO = new UserDAOImpl();

    public LoginService() {
        this.userDAO = new UserDAOImpl();
    }

    // TODO Login()   void for now, send user_id later
    public void LogIn(String username, String masterPassword) throws SQLException {
        String hashedPass = HashPass(masterPassword);

        boolean userExist = userDAO.doesUserExist(username);

        if (userExist) {
            System.out.println("User exists");
            boolean correctPass = userDAO.isPassCorrect(hashedPass);
            if (correctPass) {
                System.out.println("Correct login, user '" + username + "' logging in");
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("This user is not registered here!");
        }


        // TODO connect to view and restrict access

    }

    // create new user connect to CreateUserViewController
    // TODO proper exception handling, check for already used mail addresses
    public int CreateNewUser(String username, String masterPassword) throws SQLException {

        String hashedPass = HashPass(masterPassword);

        // TODO do something about user_id
        User newUser = new User(0, username, hashedPass);

        int result = userDAO.insert(newUser);
        System.out.println(result +" new user added successfully");

        return result;
    };

    public String HashPass(String masterPassword) {
        String sha256hex = Hashing.sha256().hashString(masterPassword, StandardCharsets.UTF_8).toString();
        System.out.println("hashedpass: "+sha256hex );
        return sha256hex;
    }

}
