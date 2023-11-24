package com.example.passwordmanager;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.UserDAO;
import com.example.passwordmanager.Model.UserDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible;
    @FXML private Button newUserButton, unlockButton;
    @FXML private ImageView eyeImageView;

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
        helper.addPasswordVisibleToggle(eyeImageView, masterPasswordInvisible, masterPasswordVisible);

        email.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.TAB) {
                if (helper.getVisible(masterPasswordInvisible)) {
                    masterPasswordInvisible.requestFocus();
                } else {
                    masterPasswordVisible.requestFocus();
                }
            }
        });

        helper.changeImageOnHover(eyeImageView, "eye.png", "eye-hover.png");
    }

    @FXML public void unlockButtonPressed() throws SQLException {
        String userMailInput = email.getText();
        String userMasterPassInput = masterPasswordInvisible.getText();

        System.out.println("user '"+userMailInput + "' trying to log in");
        System.out.println("users pass : "+ userMasterPassInput);

        if (loginService.LogIn(userMailInput, userMasterPassInput)) {
            helper.navigateTo(baseAnchorPane, "main_view.fxml");
        }
    }


    @FXML public void createUserButtonPressed() {
        helper.navigateTo(baseAnchorPane, "create_user_view.fxml");}
}
