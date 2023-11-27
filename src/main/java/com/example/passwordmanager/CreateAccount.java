package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class CreateAccount extends AnchorPane {

    @FXML private AnchorPane backAnchorPane, noteAnchorPane;
    @FXML private TextField name, username, invisiblePassword, visiblePassword, note;
    @FXML private Button saveButton;
    @FXML private ImageView passwordVisible, invisiblePasswordGenerate, passwordGenerate;
    @FXML private FlowPane passwordGeneratorFlowPane;

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

}
