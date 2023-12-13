package com.example.passwordmanager;

import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CreateCard extends AnchorPane {

    @FXML private TextField name, cardHolder, cardNumber, visibleCvcCode, note;
    @FXML private ChoiceBox<String> expireMonth, expireYear;
    @FXML private PasswordField invisibleCvcCode;
    @FXML private Button saveButton;
    @FXML private ImageView cvcVisible;
    @FXML private FlowPane passwordGeneratorFlowPane;

    private MainViewManager parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();


    public CreateCard(MainViewManager controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/create-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initFields();
        this.parentController = controller;

        String[] expireMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        this.expireMonth.getItems().addAll(expireMonths);

        String[] expireYears = {"24", "25", "26", "27", "28", "29", "30"};
        this.expireYear.getItems().addAll(expireYears);


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
        helper.addPasswordVisibleToggle(cvcVisible, invisibleCvcCode, visibleCvcCode);

        // Regex for card number
        cardNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cardNumber.getText().replace(" ", "").matches("^\\d{16}$")) {
                    cardNumber.setText("");
                }
                else {
                    StringBuilder output = new StringBuilder();
                    String text = this.cardNumber.getText().replace(" ", "");
                    for (int i=0; i<text.length(); i++) {
                        if (i % 4 == 0 && i != 0) {
                            output.append(" ");
                        }
                        output.append(text.charAt(i));
                    }
                    cardNumber.setText(String.valueOf(output));
                }
            }
        });

        invisibleCvcCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!invisibleCvcCode.getText().matches("^\\d{3}$")) {
                    invisibleCvcCode.setText("");
                }
            }
        });

        visibleCvcCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!visibleCvcCode.getText().matches("^\\d{3}$")) {
                    visibleCvcCode.setText("");
                }
            }
        });
    }


    public void initFields() {
        this.name.setText("");
        this.cardNumber.setText("");
        this.invisibleCvcCode.setText("");
        this.note.setText("");
        this.visibleCvcCode.setVisible(false);
    }

    private boolean isFieldsComplete() {
        return (checkLength(this.name.getText()) &&
                checkLength(this.cardNumber.getText()) &&
                checkLength(this.expireMonth.getValue()) &&
                checkLength(this.expireYear.getValue()) &&
                checkLength(this.invisibleCvcCode.getText()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    @FXML
    private void saveButtonPressed() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        if (isFieldsComplete()) {
            // Se över detta
            CardEntry newEntry = new CardEntry(0, name.getText(), cardHolder.getText(), cardNumber.getText(), expireMonth.getValue(), expireYear.getValue(), invisibleCvcCode.getText(), note.getText());
            EncryptionBuffer.insertCardEntry(newEntry);
            parentController.addPasswordEntry(newEntry);
            parentController.handleSaveButtonPressed();
        }

    }
}
