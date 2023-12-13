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
import java.net.URL;
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

    // Creates and returns a PasswordFieldManager object that manages Password- and TextFields
    public PasswordFieldManager addPasswordVisibleToggle(ImageView eye, TextField invisiblePassword, TextField visiblePassword) {
        return new PasswordFieldManager(eye, invisiblePassword, visiblePassword);
    }

    public Image getImage(String fileName) throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/" + fileName)).openStream());
    }

    public URL getFxmlFile(String fileName) {
        return getClass().getResource("Views/" + fileName);
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
