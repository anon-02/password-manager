package com.example.passwordmanager;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserViewController implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible, confirmPasswordInvisible, confirmPasswordVisible;
    @FXML private Button backButton, createButton;
    @FXML private ImageView masterPasswordEye, confirmPasswordEye;

    private fxmlHelper helper = fxmlHelper.getInstance();
    private LoginService loginService = new LoginService();
    private PasswordFieldManager firstFieldManager, secondFieldManager;


    public CreateUserViewController() {
        this.loginService = new LoginService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helper.onMouseHover(backButton);
        helper.onMouseHover(createButton);

        firstFieldManager = helper.addPasswordVisibleToggle(masterPasswordEye, masterPasswordInvisible, masterPasswordVisible);
        secondFieldManager = helper.addPasswordVisibleToggle(confirmPasswordEye, confirmPasswordInvisible, confirmPasswordVisible);
    }

    @FXML
    private void backButtonPressed() {
        helper.navigateTo(baseAnchorPane, "login_view.fxml");}

    private boolean verifyFields() {
        return (firstFieldManager.getPassword().equals(secondFieldManager.getPassword()) &&
                (!email.getText().isEmpty() || !masterPasswordInvisible.getText().isEmpty()));
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
            String userMasterPassInput = secondFieldManager.getPassword();
            System.out.println("new users mail: "+userMailInput);
            System.out.println("new users pass (prehash): "+userMasterPassInput);
            loginService.CreateNewUser(userMailInput, userMasterPassInput);
        } else {
            System.out.println("Yo"); // TODO add error popup or highlight fields
        }
    }
}
