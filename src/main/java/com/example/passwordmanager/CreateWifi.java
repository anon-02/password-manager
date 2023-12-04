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

public class CreateWifi extends AnchorPane {

    @FXML AnchorPane backAnchorPane;
    @FXML TextField entryName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiURL, wifiAdminPassword, wifiNote;
    @FXML Button saveButton;
    @FXML ImageView eyeImageView;

    private MainViewController parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();


    public CreateWifi(MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/create-wifi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        helper.addPasswordVisibleToggle(eyeImageView, wifiPasswordInvisible, wifiPasswordVisible);

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
    }

    private boolean isFieldsComplete() {
        return (checkLength(this.entryName.getText()) &&
                checkLength(this.wifiName.getText()) &&
                checkLength(this.wifiPasswordInvisible.getText()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    @FXML
    private void saveButtonPressed() throws IOException {
        System.out.println(isFieldsComplete());
        if (isFieldsComplete()) {
            parentController.addPasswordEntry(new WifiEntry(this.entryName.getText(),
                    this.wifiName.getText(),
                    this.wifiPasswordInvisible.getText(),
                    this.wifiURL.getText(),
                    this.wifiAdminPassword.getText(),
                    this.wifiNote.getText()));
            parentController.handleSaveButtonPressed();
        }
    }
}