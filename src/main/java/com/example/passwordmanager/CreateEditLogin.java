package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateEditLogin extends AnchorPane {

    @FXML private AnchorPane backAnchorPane;
    @FXML private TextField name, username, invisiblePassword, visiblePassword, note;
    @FXML private Button saveButton;
    @FXML private ImageView passwordVisible, invisiblePasswordGenerate;

    private MainViewController parentController;


    public CreateEditLogin(MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/create-edit-login.fxml"));
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

        EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean state = visiblePassword.isVisible();
                if (state) {
                    invisiblePassword.setText(visiblePassword.getText());
                    invisiblePassword.toFront();
                    invisiblePassword.setVisible(true);
                    visiblePassword.setVisible(false);

                } else {
                    visiblePassword.setText(invisiblePassword.getText());
                    visiblePassword.toFront();
                    invisiblePassword.setVisible(false);
                    visiblePassword.setVisible(true);
                }
                passwordVisible.requestFocus(); // Will focus the TextField otherwise
            }
        };
        passwordVisible.setOnMouseClicked(onClick);
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
            parentController.addEntry(new LoginEntry(this.name.getText(), this.username.getText(), this.invisiblePassword.getText(), this.note.getText()));
            parentController.handleSaveButtonPressed();
        }
    }

}
