package com.example.passwordmanager.ViewManager;

import com.example.passwordmanager.Controller.LoginViewController;
import com.example.passwordmanager.Model.*;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * View manager for the login view, handling all fxml-elements and notifying the controller when user performs actions
 */
public class LoginViewManager implements Initializable {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible;
    @FXML private Button newUserButton, unlockButton;
    @FXML private ImageView eyeImageView;

    private final fxmlHelper helper = fxmlHelper.getInstance();
    private PasswordFieldManager manager;
    private LoginViewController controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = new LoginViewController();
        this.manager = helper.addPasswordVisibleToggle(eyeImageView, masterPasswordInvisible, masterPasswordVisible);

        helper.onMouseHover(newUserButton);
        helper.onMouseHover(unlockButton);
    }

    @FXML public void unlockButtonPressed() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        controller.handleUnlockButtonPressed(baseAnchorPane, email.getText(), manager.getPassword());
    }

    @FXML public void createUserButtonPressed() {
        controller.handleCreateUserButtonPressed(baseAnchorPane);
    }
}
