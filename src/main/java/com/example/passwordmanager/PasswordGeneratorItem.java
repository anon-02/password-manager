package com.example.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PasswordGeneratorItem extends AnchorPane {


    @FXML AnchorPane baseAnchorPane;
    @FXML RadioButton passwordRadioButton, passphraseRadioButton;

    /** Used for generating password **/
    @FXML Label lengthLabel;
    @FXML Slider lengthSlider;
    @FXML CheckBox includeUppercase, includeNumbers, includeSpecial;

    public PasswordGeneratorItem(String s) {
        FXMLLoader fxmlLoader = null;
        if (s.equals("create")) {
            fxmlLoader = new FXMLLoader(getClass().getResource("Views/password-generator.fxml"));
        } else if (s.equals("detail")){
            fxmlLoader = new FXMLLoader(getClass().getResource("Views/password-generator-small.fxml"));
        }
        assert fxmlLoader != null;
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
