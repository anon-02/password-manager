package com.example.passwordmanager.View;

import com.example.passwordmanager.Controller.CreateUserController;
import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.security.auth.login.CredentialException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserViewManager implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible, confirmPasswordInvisible, confirmPasswordVisible;
    @FXML private Button backButton, createButton;
    @FXML private ImageView masterPasswordEye, confirmPasswordEye;

    private fxmlHelper helper = fxmlHelper.getInstance();
    private PasswordFieldManager firstFieldManager, secondFieldManager;
    CreateUserController controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CreateUserController();
        helper.onMouseHover(backButton);
        helper.onMouseHover(createButton);

        firstFieldManager = helper.addPasswordVisibleToggle(masterPasswordEye, masterPasswordInvisible, masterPasswordVisible);
        secondFieldManager = helper.addPasswordVisibleToggle(confirmPasswordEye, confirmPasswordInvisible, confirmPasswordVisible);
    }

    @FXML
    private void backButtonPressed() {controller.handleBackButtonPressed(baseAnchorPane);}

    private boolean verifyFields() {
        return (firstFieldManager.getPassword().equals(secondFieldManager.getPassword()) &&
                (!email.getText().isEmpty() || !masterPasswordInvisible.getText().isEmpty()));
    }

    @FXML
    private void createUserButtonPressed() throws SQLException {
        // TODO verifyFields(), mail format
        // TODO error handling somewhere
        if (verifyFields()) {
            controller.handleCreateUserButtonPressed(baseAnchorPane, email.getText(), secondFieldManager.getPassword());
        } else {
            System.out.println("Yo"); // TODO add error popup or highlight fields
        }
    }
}
