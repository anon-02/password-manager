package com.example.passwordmanager;

import com.example.passwordmanager.Model.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserViewController implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPassword, confirmMasterPassword;
    @FXML private Button backButton, createButton;

    private fxmlHelper helper = fxmlHelper.getInstance();


    private LoginService loginService = new LoginService();
    public CreateUserViewController() {
        this.loginService = new LoginService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void backButtonPressed() {
        helper.navigateTo(baseAnchorPane, "login_view.fxml");}

    private boolean verifyFields() {
        return (masterPassword.getText().equals(confirmMasterPassword.getText()) && (!email.getText().isEmpty() || !masterPassword.getText().isEmpty()));
    }

    @FXML
    private void createUserButtonPressed() throws SQLException {
        // TODO verifyFields(), mail format and check confirm password
        // DONE model.addUser(Fields)
        // TODO error handling somewhere
        if (verifyFields()) {
            helper.navigateTo(baseAnchorPane, "login_view.fxml");

            // Add User
            String userMailInput = email.getText();
            String userMasterPassInput = masterPassword.getText();
            System.out.println("new users mail: "+userMailInput);
            System.out.println("new users pass (prehash): "+userMasterPassInput);
            loginService.CreateNewUser(userMailInput, userMasterPassInput);
        } else {
            System.out.println("Yo"); // TODO add error popup or highlight fields
        }
    }
}
