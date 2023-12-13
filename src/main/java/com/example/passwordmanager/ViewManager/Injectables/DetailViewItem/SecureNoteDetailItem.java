package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.Entries.SecureNoteEntry;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SecureNoteDetailItem extends AnchorPane implements DetailItemImpl {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField entryNoteName, noteSubject;
    @FXML private TextArea noteContent;

    private DisplayableEntry currentEntry;
    private PasswordFieldManager manager;
    private final fxmlHelper helper = fxmlHelper.getInstance();
    DetailViewController parentController;
    TextInputControl[] secureNoteFields;

    public SecureNoteDetailItem(SecureNoteEntry entry, DetailViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("detail_view_secure-note.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;

        this.entryNoteName.setText(entry.getName());
        this.noteSubject.setText(entry.getNoteSubject());
        this.noteContent.setText(entry.getNoteContent());

        this.secureNoteFields = new TextInputControl[]{entryNoteName, noteSubject, noteContent};

        this.currentEntry = entry;
    }

    @Override
    public void toggleEditable() {
        for (TextInputControl field: this.secureNoteFields) {
            field.setEditable(!field.isEditable());
        }
    }
    @Override
    public void updateEntry() {
        SecureNoteEntry updateEntry = new SecureNoteEntry(currentEntry.getEntryId(), entryNoteName.getText(), noteSubject.getText(), noteContent.getText());
        EncryptionBuffer.updateNoteEntry(updateEntry);
        if (helper.isDebug()) {
            System.out.println("updated entry (detailview): ");
            System.out.println(updateEntry);
        }
    }

    @Override
    public DisplayableEntry getEntry() {
        return currentEntry;
    }
}
