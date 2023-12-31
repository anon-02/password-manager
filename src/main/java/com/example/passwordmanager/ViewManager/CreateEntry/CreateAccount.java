package com.example.passwordmanager.ViewManager.CreateEntry;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Model.Entries.AccountEntry;
import com.example.passwordmanager.Model.EntriesListHandler;
import com.example.passwordmanager.Model.Generator;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.Password.PassphraseGenerator;
import com.example.passwordmanager.Password.PasswordGenerator;
import com.example.passwordmanager.ViewManager.Injectables.PasswordGeneratorItem;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

/**
 * Injectable AnchorPane in the createEntry view
 */
public class CreateAccount extends AnchorPane implements Generator {

    @FXML private AnchorPane backAnchorPane, noteAnchorPane;
    @FXML private TextField name, username, invisiblePassword, visiblePassword, note;
    @FXML private Button saveButton;
    @FXML private ImageView passwordVisible, invisiblePasswordGenerate, passwordGenerate;
    @FXML private FlowPane passwordGeneratorFlowPane;
    @FXML private Rectangle strengthIndicatorRec;
    @FXML private Label passwordStrengthLabel;


    private MainViewManager parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();
    private EntriesListHandler entriesListHandler = EntriesListHandler.getInstance();
    private PasswordFieldManager manager;

    private boolean passwordGeneratorShowing = false;

    public CreateAccount(MainViewManager controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("create-account.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initFields();
        this.parentController = controller;
        manager = helper.addPasswordVisibleToggle(passwordVisible, invisiblePassword, visiblePassword);
        updateStrengthIndicator(manager.getPassword());

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    saveButtonPressed();
                } catch (IOException | InvalidAlgorithmParameterException | SQLException | NoSuchPaddingException |
                         IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeySpecException |
                         BadPaddingException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        saveButton.setOnAction(event);

        EventHandler<MouseEvent> cogClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cogClicked();
            }
        };
        passwordGenerate.setOnMouseClicked(cogClick);

        invisiblePassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {
                updateStrengthIndicator(newString);
            }
        });
    }

    private void initFields() {
        this.name.setText("");
        this.username.setText("");
        this.invisiblePassword.setText("");
        this.note.setText("");
        this.visiblePassword.setVisible(false);
    }

    private boolean isFieldsComplete() {
        return (checkLength(this.name.getText()) &&
                checkLength(this.username.getText()) &&
                checkLength(manager.getPassword()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    // Verifies that the necessary fields have been filled in, creates an entry and adds it to the database using entriesListHandler
    @FXML
    private void saveButtonPressed() throws IOException, InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        if (isFieldsComplete()) {
            AccountEntry newEntry = new AccountEntry(0, name.getText(), username.getText(), manager.getPassword(), note.getText());
            EncryptionBuffer.insertAccountEntry(newEntry);
            //entriesListHandler.addPasswordEntry(newEntry);
            parentController.handleSaveButtonPressed();
        }
    }

    private void cogClicked() {
        if (passwordGeneratorShowing) {
            passwordGeneratorFlowPane.getChildren().clear();
            noteAnchorPane.setLayoutY(174);
        } else {
            noteAnchorPane.setLayoutY(384);
            passwordGeneratorFlowPane.getChildren().clear();
            passwordGeneratorFlowPane.getChildren().add(new PasswordGeneratorItem(this, "create"));
        }
        passwordGeneratorShowing = !passwordGeneratorShowing;
    }

    private void updateStrengthIndicator(String newString) {
        // TODO get ratio from model according to rules
        double ratio = invisiblePassword.getText().length() * 8 / 250d;
        strengthIndicatorRec.setWidth((int) (250 * ratio));

        if (ratio >= 0 && ratio < 0.25) {
            strengthIndicatorRec.setStyle("-fx-fill: #DD7373");
            passwordStrengthLabel.setText("Bad password");
        } else if (ratio >= 0.25 && ratio < 0.5) {
            strengthIndicatorRec.setStyle("-fx-fill: #DDCC73");
            passwordStrengthLabel.setText("Weak password");
        } else if (ratio >= 0.5 && ratio < 0.75) {
            strengthIndicatorRec.setStyle("-fx-fill: #75DD73");
            passwordStrengthLabel.setText("Good password");
        } else {
            strengthIndicatorRec.setStyle("-fx-fill: #3BD338");
            passwordStrengthLabel.setText("Very password");
        }
    }

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

}
