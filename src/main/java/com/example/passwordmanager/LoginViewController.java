package com.example.passwordmanager;

import com.example.passwordmanager.Model.*;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible;
    @FXML private Button newUserButton, unlockButton;

    private fxmlHelper helper = fxmlHelper.getInstance();

    // TODO private LoginService loginService
    private LoginService loginService = new LoginService();
    public LoginViewController() {
        this.loginService = new LoginService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helper.onMouseHover(newUserButton);
        helper.onMouseHover(unlockButton);
    }

    @FXML public void unlockButtonPressed() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String userMailInput = email.getText();
        String userMasterPassInput = masterPasswordInvisible.getText();

        System.out.println("user '"+userMailInput + "' trying to log in");
        System.out.println("users pass : "+ userMasterPassInput);

        loginService.LogIn(userMailInput, userMasterPassInput);

        User thisUser = SessionManager.getCurrentUser();
        System.out.println(thisUser + " is currently logged in");



        helper.navigateTo(baseAnchorPane, "main_view.fxml");
    }


    @FXML public void createUserButtonPressed() {
        helper.navigateTo(baseAnchorPane, "create_user_view.fxml");}
}
