package com.example.passwordmanager.Controller;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.View.LoginViewManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.scene.layout.AnchorPane;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Controller for login view
 * Handles user input
 */
public class LoginViewController {
    private final LoginService loginService;
    private final fxmlHelper helper;


    public LoginViewController() {
        this.loginService = new LoginService();
        this.helper = fxmlHelper.getInstance();
    }

    // Checks that the user exists in the database and if the password is correct
    public void handleUnlockButtonPressed(AnchorPane anchorPane, String email, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        // TODO fix login logic to not raise exception
        loginService.LogIn(email, password);
        User thisUser = SessionManager.getCurrentUser();
        helper.navigateTo(anchorPane, "main_view.fxml");
        if (helper.isDebug()) {
            System.out.println("user '"+email + "' trying to log in");
            System.out.println("users pass : "+ password);
            System.out.println(thisUser + " is currently logged in");
        }
    }


    public void handleCreateUserButtonPressed(AnchorPane anchorPane) {
        helper.navigateTo(anchorPane, "create_user_view.fxml");
    }
}
