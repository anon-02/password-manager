package com.example.passwordmanager;

import com.example.passwordmanager.Model.PasswordFieldManager;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class fxmlHelper {
    private static fxmlHelper instance = null;

    protected fxmlHelper() {} // Does nothing


    /* Singleton, ensures that every part of the application shares the model */
    public static fxmlHelper getInstance() {
        if (instance == null) {
            instance = new fxmlHelper();
        }
        return instance;
    }

    public Image getImageFroURL(String url) {
        return new Image(url);
    }

    public PasswordFieldManager addPasswordVisibleToggle(ImageView eye, TextField invisiblePassword, TextField visiblePassword) {
        PasswordFieldManager manager = new PasswordFieldManager(eye, invisiblePassword, visiblePassword);
        return manager;

        /*visiblePassword.setFocusTraversable(false);
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
                eye.requestFocus(); // Makes it not look shit
            }
        };
        eye.setOnMouseClicked(onClick);*/
    }

    public Image getImage(String fileName) throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/" + fileName)).openStream());
    }

    public void changeImageOnHover(ImageView imageView, String normalImage, String hoverImage) {
        imageView.setOnMouseEntered(mouseEvent -> {
            try {
                System.out.println("We hoverin");
                imageView.setImage(getImage(hoverImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        imageView.setOnMouseExited(mouseEvent -> {
            try {
                System.out.println("we not hovering");
                imageView.setImage(getImage(normalImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Mainly used to get the current showing password field
    public boolean getVisible(TextField field) {
        return field.isVisible();
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

    // Returns true if the application is running in debug mode
    public boolean isDebug() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
    }
}
