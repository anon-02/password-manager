package com.example.passwordmanager;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class PasswordManagerModel {
    private static PasswordManagerModel instance = null;

    protected PasswordManagerModel() {} // Does nothing


    /* Singleton, ensures that every part of the application shares the model */
    public static PasswordManagerModel getInstance() {
        if (instance == null) {
            instance = new PasswordManagerModel();
        }
        return instance;
    }

    // TODO getEntries(user) -> List<Entries>
    // TODO getMatching(String searchTerm) -> List<Entries>
    // TODO addEntry(Entry entry)
    // TODO removeEntry(Entry entry)
    // TODO clearEntries()

    public Image getImageFroURL(String url) {
        return new Image(url);
    }
    public void addPasswordVisibleToggle(ImageView eye, TextField invisiblePassword, TextField visiblePassword) {
        EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
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
                eye.requestFocus(); // Will focus the TextField otherwise
            }
        };
        eye.setOnMouseClicked(onClick);
    }

    /* Sets a TextFields text to given string, when field is clicked the text disappears */
    public void AddDefaultText(TextField field, String text) {
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && Objects.equals(field.getText(), text)) {
                field.setText("");
            } else {
                if (field.getText().isEmpty()) {
                    field.setText(text);
                }
            }
        });
    }

    /* Loads the new scene and sets the current scene to the new one */
    public void navigateTo(AnchorPane currentSceneAnchorPane, String fxmlFile) {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/" + fxmlFile)));
            currentSceneAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* Will change the cursor to a hand when entering node surface */
    public void onMouseHover(Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }


}
