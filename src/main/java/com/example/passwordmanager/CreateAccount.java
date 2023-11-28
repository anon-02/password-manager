package com.example.passwordmanager;

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

import java.io.IOException;

public class CreateAccount extends AnchorPane {

    @FXML private AnchorPane backAnchorPane, noteAnchorPane;
    @FXML private TextField name, username, invisiblePassword, visiblePassword, note;
    @FXML private Button saveButton;
    @FXML private ImageView passwordVisible, invisiblePasswordGenerate, passwordGenerate;
    @FXML private FlowPane passwordGeneratorFlowPane;
    @FXML private Rectangle strengthIndicatorRec;
    @FXML private Label passwordStrengthLabel;


    private MainViewController parentController;
    private fxmlHelper model = fxmlHelper.getInstance();
    private boolean passwordGeneratorShowing = false;

    public CreateAccount(MainViewController controller) {
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
        model.addPasswordVisibleToggle(passwordVisible, invisiblePassword, visiblePassword);

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
                checkLength(this.invisiblePassword.getText()));
    }

    private boolean checkLength(String str) {
        return !str.isEmpty();
    }

    @FXML
    private void saveButtonPressed() throws IOException {
        if (isFieldsComplete()) {
            parentController.addEntry(new AccountEntry(this.name.getText(), this.username.getText(), this.invisiblePassword.getText(), this.note.getText()));
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
            passwordGeneratorFlowPane.getChildren().add(new PasswordGeneratorItem("create"));
        }
        passwordGeneratorShowing = !passwordGeneratorShowing;
    }

    @FXML
    private void generateButtonClicked() {
        // TODO model.generatePassword()
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



}
