package com.example.passwordmanager.Controller;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.fxmlHelper;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Controller for create user view
 * Handles user input
 */
public class CreateUserController {

    private final fxmlHelper helper;
    private final LoginService loginService;

    public CreateUserController() {
        this.loginService = new LoginService();
        this.helper = fxmlHelper.getInstance();
    }

    public void handleBackButtonPressed(AnchorPane anchorPane) {
        helper.navigateTo(anchorPane, "login_view.fxml");
    }

    public void handleCreateUserButtonPressed(AnchorPane anchorPane, String email, String password) throws SQLException {
        loginService.CreateNewUser(email, password);
        helper.navigateTo(anchorPane, "login_view.fxml");

        if (helper.isDebug()) {
            System.out.println("new users mail: "+ email);
            System.out.println("new users pass (prehash): "+ password);
        }
    }

    /*public void showWarningPopup() {
        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.WINDOW_MODAL);

        Button okButton
    }*/
}
