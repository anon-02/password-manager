package com.example.passwordmanager.Controller;

import com.example.passwordmanager.Model.LoginService;
import com.example.passwordmanager.fxmlHelper;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Controller for create user view
 * Handles user input
 */
public class CreateUserViewController {

    private final fxmlHelper helper;
    private final LoginService loginService;
    private boolean emailVerified = false;

    public CreateUserViewController() {
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

    // Verifies the validity of an email, and sets the internal value of emailVerified to that
    public boolean verifyEmail(String s) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean isEmail = Pattern.compile(regexPattern).matcher(s).matches();
        this.emailVerified = isEmail;
        return isEmail;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    /*public void showWarningPopup() {
        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.WINDOW_MODAL);

        Button okButton
    }*/
}