package com.example.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserViewController implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPassword, confirmMasterPassword;
    @FXML private Button backButton, createButton;

    private fxmlHelper helper = fxmlHelper.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void backButtonPressed() {
        helper.navigateTo(baseAnchorPane, "login_view.fxml");}

    @FXML
    private void createUserButtonPressed() {
        // TODO verifyFields()
        // TODO model.addUser(Fields)
        helper.navigateTo(baseAnchorPane, "login_view.fxml");
    }
}
