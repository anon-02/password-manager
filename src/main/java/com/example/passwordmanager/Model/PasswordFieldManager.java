package com.example.passwordmanager.Model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PasswordFieldManager {

    @FXML private TextField visibleField, invisibleField;
    @FXML private Node toggle;
    private String password = "";


    public PasswordFieldManager(Node toggle, TextField invisibleField, TextField visibleField) {
        this.invisibleField = invisibleField;
        this.visibleField = visibleField;
        this.toggle = toggle;

        visibleField.setFocusTraversable(false);
        invisibleField.setFocusTraversable(true);

        EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean state = visibleField.isVisible();
                if (state) {
                    invisibleField.toFront();
                    invisibleField.setVisible(true);
                    visibleField.setVisible(false);

                    invisibleField.setFocusTraversable(true);
                    visibleField.setFocusTraversable(false);

                } else {
                    visibleField.toFront();
                    invisibleField.setVisible(false);
                    visibleField.setVisible(true);

                    visibleField.setFocusTraversable(true);
                    invisibleField.setFocusTraversable(false);

                }
                toggle.requestFocus();  // Makes it not look shit
                //toggleFocusTraversable();
                updateFields();
            }
        };
        toggle.setOnMouseClicked(onClick);

        visibleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {
                password = newString;
            }
        });

        invisibleField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {
                password = newString;
            }
        });
    }

    private void updateFields() {
        visibleField.setText(password);
        invisibleField.setText(password);
    }

    private void toggleFocusTraversable() {
        visibleField.setFocusTraversable(!visibleField.isFocusTraversable());
        invisibleField.setFocusTraversable(!invisibleField.isFocusTraversable());
    }


    public String getPassword() {
        return this.password;
    }


}
