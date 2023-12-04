package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateLogin extends AnchorPane {

    @FXML private AnchorPane backAnchorPane;
    @FXML private TextField name, username, invisiblePassword, visiblePassword, note;
    @FXML private Button saveButton;
    @FXML private ImageView passwordVisible, invisiblePasswordGenerate;

    private MainViewController parentController;
    private fxmlHelper model = fxmlHelper.getInstance();

    public CreateLogin(MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/create-account.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initFields();
        this.parentController = controller;

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    saveButtonPressed();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        saveButton.setOnAction(event);
        model.addPasswordVisibleToggle(passwordVisible, invisiblePassword, visiblePassword);
    }

    public void initFields() {
        this.name.setText("");
        this.username.setText("");
        this.invisiblePassword.setText("");
        this.note.setText("");
        this.visiblePassword.setVisible(false);
    }

    private boolean isFieldsComplete() {
        return (checkLength(this.name.getText()) &&
                checkLength(this.username.getText()) &&
                checkLength(this.invisiblePassword.getText()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    @FXML
    private void saveButtonPressed() throws IOException {
        if (isFieldsComplete()) {
            parentController.addPasswordEntry(new AccountEntry(this.name.getText(), this.username.getText(), this.invisiblePassword.getText(), this.note.getText())); //change so that factory creates the objects
            parentController.handleSaveButtonPressed();
        }
    }

}