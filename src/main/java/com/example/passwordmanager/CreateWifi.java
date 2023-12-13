package com.example.passwordmanager;

import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateWifi extends AnchorPane {

    @FXML AnchorPane backAnchorPane;
    @FXML TextField entryName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiURL, wifiAdminPassword, wifiNote;
    @FXML Button saveButton;
    @FXML ImageView eyeImageView;

    private MainViewController parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();
    private PasswordFieldManager manager;


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
        manager = helper.addPasswordVisibleToggle(eyeImageView, wifiPasswordInvisible, wifiPasswordVisible);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    saveButtonPressed();
                } catch (IOException | InvalidAlgorithmParameterException | NoSuchPaddingException |
                         IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                         InvalidKeyException | SQLException e) {
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
    private void saveButtonPressed() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        if (isFieldsComplete()) {
            // Se över detta
            WifiEntry newEntry = new WifiEntry(0, entryName.getText(), wifiName.getText(), manager.getPassword(), wifiURL.getText(), wifiAdminPassword.getText(), wifiNote.getText());
            EncryptionBuffer.insertWifiEntry(newEntry);
            parentController.addPasswordEntry(newEntry);
            parentController.handleSaveButtonPressed();
        }
    }
}
