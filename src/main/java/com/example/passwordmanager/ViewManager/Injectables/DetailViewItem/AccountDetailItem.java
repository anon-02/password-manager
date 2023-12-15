package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Model.Entries.AccountEntry;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.Entries.PasswordEntry;
import com.example.passwordmanager.Model.EntriesListHandler;
import com.example.passwordmanager.Model.Generator;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.Password.PassphraseGenerator;
import com.example.passwordmanager.Password.PasswordGenerator;
import com.example.passwordmanager.ViewManager.Injectables.PasswordGeneratorItem;
import com.example.passwordmanager.fxmlHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

/**
 * Injectable AnchorPane in the detailView
 */
public class AccountDetailItem extends AnchorPane implements Generator, DetailItemImpl {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private AnchorPane noteAnchorPane;
    @FXML private TextField entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible;
    @FXML private TextArea accountNote;
    @FXML private ImageView eyeImageView, cogImageView;
    @FXML private FlowPane generatePasswordFlowPane;
    private boolean passwordGeneratorShowing = false;

    TextInputControl[] accountFields;

    private DisplayableEntry currentEntry;
    private PasswordFieldManager manager;
    private final fxmlHelper helper = fxmlHelper.getInstance();
    DetailViewController parentController;

    public AccountDetailItem(AccountEntry entry, DetailViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("detail_view_account.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;
        manager = helper.addPasswordVisibleToggle(eyeImageView, accountPasswordInvisible, accountPasswordVisible);

        this.entryAccountName.setText(entry.getName());
        this.accountUsername.setText(entry.getUsername());
        manager.setPassword(entry.getPassword());
        this.accountNote.setText(entry.getNote());

        accountFields = new TextInputControl[]{entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible, accountNote};

        // Listener for when the user opens the inline password generator
        EventHandler<MouseEvent> cogClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cogClicked();
            }

            private void cogClicked() {
                if (passwordGeneratorShowing) {
                    generatePasswordFlowPane.getChildren().clear();
                    noteAnchorPane.setLayoutY(255);
                } else {
                    noteAnchorPane.setLayoutY(476);
                    generatePasswordFlowPane.getChildren().clear();
                    generatePasswordFlowPane.getChildren().add(new PasswordGeneratorItem(getSelf(), "detail"));
                }
                passwordGeneratorShowing = !passwordGeneratorShowing;
            }
        };
        cogImageView.setOnMouseClicked(cogClick);
        this.currentEntry = entry;
    }

    // Needed to make above method work
    private AccountDetailItem getSelf() {
        return this;
    }

    // Generates a password based on the parameters in the PasswordGeneratorItem and updates the password field in the view
    @Override
    public void generate(String type, int length, boolean includeUpper, boolean includeNumbers, boolean includeSpecial) {
        String generatedPassword = null;
        if (type.equals("Password")) {
            generatedPassword = PasswordGenerator.generatePassword(length, includeUpper, includeNumbers, includeSpecial);

        } else if (type.equals("Passphrase")) {
            generatedPassword = PassphraseGenerator.generatePassphrase(length, includeUpper, includeNumbers, includeSpecial);
        }
        manager.setPassword(generatedPassword);
    }

    @Override
    public void toggleEditable() {
        for (TextInputControl field: this.accountFields) {
            field.setEditable(!field.isEditable());
        }
    }
    @Override
    public void updateEntry() {
        AccountEntry updateEntry = new AccountEntry(currentEntry.getEntryId(), entryAccountName.getText(), accountUsername.getText(), manager.getPassword(), accountNote.getText());
        EntriesListHandler.getInstance().updatePasswordEntry((PasswordEntry) currentEntry, updateEntry);
        EncryptionBuffer.updateAccountEntry(updateEntry);
        System.out.println("successful detail update");
    }

    @Override
    public DisplayableEntry getEntry() {
        return currentEntry;
    }
}
