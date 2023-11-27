package com.example.passwordmanager;

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

import java.io.IOException;

public class CreateCard extends AnchorPane {

    @FXML private TextField name, cardNumber, visibleCvcCode, note;
    @FXML private ChoiceBox<String> expireMonth, expireYear;
    @FXML private PasswordField invisibleCvcCode;
    @FXML private Button saveButton;
    @FXML private ImageView cvcVisible;
    @FXML private FlowPane passwordGeneratorFlowPane;

    private MainViewController parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();


    public CreateCard(MainViewController controller) {
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
                } catch (IOException e) {
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
    private void saveButtonPressed() throws IOException {
        if (isFieldsComplete()) {
            parentController.addEntry(new CardEntry(this.name.getText(), "Tim Carlsson", this.cardNumber.getText(), this.expireMonth.getValue(), this.expireYear.getValue(), this.invisibleCvcCode.getText(), this.note.getText()));
            parentController.handleSaveButtonPressed();
        }

    }
}
