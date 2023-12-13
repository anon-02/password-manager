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

public class CreateUserViewManager implements Initializable {
    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField email, masterPasswordInvisible, masterPasswordVisible, confirmPasswordInvisible, confirmPasswordVisible;
    @FXML private Button backButton, createButton;
    @FXML private ImageView masterPasswordEye, confirmPasswordEye;

    private fxmlHelper helper = fxmlHelper.getInstance();
    private LoginService loginService = new LoginService();
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
                if (!email.getText().matches(" \t\n" +
                        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                        "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x" +
                        "09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                        "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[" +
                        "0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\" +
                        "x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                    email.setStyle("-fx-border-color: #75DD73");
                } else {
                    email.setStyle("-fx-border-color: #DD7373");
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
                (!email.getText().isEmpty() || !masterPasswordInvisible.getText().isEmpty()));
    }
}
