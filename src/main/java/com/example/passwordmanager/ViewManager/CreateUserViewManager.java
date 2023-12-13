package com.example.passwordmanager.ViewManager;

import com.example.passwordmanager.Controller.CreateUserViewController;
import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateUserViewManager implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible, confirmPasswordInvisible, confirmPasswordVisible;
    @FXML private Button backButton, createButton;
    @FXML private ImageView masterPasswordEye, confirmPasswordEye;

    private final fxmlHelper helper = fxmlHelper.getInstance();
    private PasswordFieldManager firstFieldManager, secondFieldManager;
    private CreateUserViewController controller;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = new CreateUserViewController();

        helper.onMouseHover(backButton);
        helper.onMouseHover(createButton);

        firstFieldManager = helper.addPasswordVisibleToggle(masterPasswordEye, masterPasswordInvisible, masterPasswordVisible);
        secondFieldManager = helper.addPasswordVisibleToggle(confirmPasswordEye, confirmPasswordInvisible, confirmPasswordVisible);

        email.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                if (controller.verifyEmail(email.getText())) {
                    email.setStyle("-fx-text-box-border: #75DD73");
                } else {
                    email.setStyle("-fx-text-box-border: #DD737");
                }
            }
        });
    }

    @FXML
    private void backButtonPressed() {
        controller.handleBackButtonPressed(baseAnchorPane);
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

    private boolean verifyFields() {
        return (firstFieldManager.getPassword().equals(secondFieldManager.getPassword()) &&
                (controller.isEmailVerified() || !masterPasswordInvisible.getText().isEmpty()));
    }
}
