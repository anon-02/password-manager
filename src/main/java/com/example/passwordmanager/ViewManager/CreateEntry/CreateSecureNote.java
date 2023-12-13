package com.example.passwordmanager.ViewManager.CreateEntry;

import com.example.passwordmanager.Model.EntriesListHandler;
import com.example.passwordmanager.Model.Entries.SecureNoteEntry;
import com.example.passwordmanager.ViewManager.MainViewManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateSecureNote extends AnchorPane {

    @FXML private AnchorPane backAnchorPane;
    @FXML private TextField entryName, noteSubject;
    @FXML private TextArea noteContent;
    @FXML private Button saveButton;

    private MainViewManager parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();
    private EntriesListHandler entriesListHandler = EntriesListHandler.getInstance();

    public CreateSecureNote(MainViewManager controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("create-secure-note.fxml"));
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
                } catch (IOException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | SQLException e) {
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
    private void saveButtonPressed() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        if (isFieldsComplete()) {
            SecureNoteEntry newEntry = new SecureNoteEntry(0, entryName.getText(), noteSubject.getText(), noteContent.getText());
            entriesListHandler.addPasswordEntry(newEntry);
            parentController.handleSaveButtonPressed();
        }
    }
}
