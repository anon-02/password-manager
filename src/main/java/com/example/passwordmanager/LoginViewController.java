package com.example.passwordmanager;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.UserDAO;
import com.example.passwordmanager.Model.UserDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPassword;
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

    @FXML public void unlockButtonPressed() throws SQLException {
        String userMailInput = email.getText();
        String userMasterPassInput = masterPassword.getText();

        System.out.println("user '"+userMailInput + "' trying to log in");
        System.out.println("users pass : "+ userMasterPassInput);

        if (loginService.LogIn(userMailInput, userMasterPassInput)) {
            helper.navigateTo(baseAnchorPane, "main_view.fxml");
        }
    }


    @FXML public void createUserButtonPressed() {
        helper.navigateTo(baseAnchorPane, "create_user_view.fxml");}
}
