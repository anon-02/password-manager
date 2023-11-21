package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateSecureNote extends AnchorPane {

    @FXML private AnchorPane backAnchorPane;
    @FXML private TextField entryName, noteSubject;
    @FXML private TextArea noteContent;
    @FXML private Button saveButton;

    private MainViewController parentController;
    private fxmlHelper model = fxmlHelper.getInstance();

    public CreateSecureNote(MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/create-secure-note.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

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
    }

    private boolean isFieldsComplete() {
        return (checkLength(this.entryName.getText()) &&
                checkLength(this.noteSubject.getText()) &&
                checkLength(this.noteContent.getText()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    @FXML
    private void saveButtonPressed() throws IOException {
        if (isFieldsComplete()) {
            parentController.addEntry(new SecureNoteEntry(this.entryName.getText(), this.noteSubject.getText(), this.noteContent.getText()));
            parentController.handleSaveButtonPressed();
        }
    }
}
