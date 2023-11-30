package com.example.passwordmanager;

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
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

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
                } catch (IOException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException |
                         InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                         BadPaddingException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        saveButton.setOnAction(event);
        model.addPasswordVisibleToggle(passwordVisible, invisiblePassword, visiblePassword);

        /*EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
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
        passwordVisible.setOnMouseClicked(onClick);*/
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
    private void saveButtonPressed() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (isFieldsComplete()) {


        }
    }

}
