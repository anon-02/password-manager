package com.example.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPassword;
    @FXML private Button newUserButton, unlockButton;

    private fxmlHelper helper = fxmlHelper.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helper.onMouseHover(newUserButton);
        helper.onMouseHover(unlockButton);
    }

    @FXML public void unlockButtonPressed() {
        helper.navigateTo(baseAnchorPane, "main_view.fxml");}
    @FXML public void createUserButtonPressed() {
        helper.navigateTo(baseAnchorPane, "create_user_view.fxml");}
}
