package com.example.passwordmanager.ViewManager.Injectables;

import com.example.passwordmanager.Model.Generator;
import com.example.passwordmanager.fxmlHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Injectable password generator component
 */
public class PasswordGeneratorItem extends AnchorPane {


    @FXML AnchorPane baseAnchorPane;

    /** Used for generating password **/
    @FXML private RadioButton passwordRadioButton, passphraseRadioButton;
    @FXML private Label lengthLabel;
    @FXML private Slider lengthSlider;
    @FXML private CheckBox includeUppercase, includeNumbers, includeSpecial;
    @FXML private Button generateButton;

    private int indicatorMaxWidth;
    private final Generator parentController;

    private final ToggleGroup passwordTypes = new ToggleGroup();

    // Creates a PasswordGeneratorItem
    // It has two different variant depending on if the generator is in the detail- or createAccount view
    public PasswordGeneratorItem(Generator controller, String s) {
        FXMLLoader fxmlLoader = null;
        fxmlHelper helper = fxmlHelper.getInstance();
        if (s.equals("create")) {
            fxmlLoader = new FXMLLoader(helper.getFxmlFile("password-generator.fxml"));
            this.indicatorMaxWidth = 250;
        } else if (s.equals("detail")){
            fxmlLoader = new FXMLLoader(helper.getFxmlFile("password-generator-small.fxml"));
            this.indicatorMaxWidth = 110;
        }
        assert fxmlLoader != null;
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        parentController = controller;
        passwordRadioButton.setToggleGroup(passwordTypes);
        passphraseRadioButton.setToggleGroup(passwordTypes);
        init();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateButtonClicked();
            }
        };
        generateButton.setOnAction(event);

        lengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                update(newValue);
            }
        });

        passwordTypes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                update((int) lengthSlider.getValue());
            }
        });
    }

    // Updates the length label based on the slider value
    private void update(Number newValue) {
        String selected = getSelectedToggleButton();
        if (selected.equals("Password")) {
            lengthSlider.setMax(100);
            lengthLabel.setText("Length: " + newValue.intValue());
        } else if (selected.equals("Passphrase")) {
            lengthSlider.setMax(10);
            lengthLabel.setText("Words: " + Math.min(newValue.intValue(), 10));
        }
    }

    private void init() {
        lengthSlider.setMin(1);
        lengthSlider.setMax(100);
        lengthSlider.setBlockIncrement(10);
        lengthSlider.setSnapToTicks(true);
        lengthSlider.setMinorTickCount(1);
        lengthSlider.setMajorTickUnit(1);
        lengthSlider.setValue(5);

        passwordRadioButton.setSelected(true);
    }

    // Returns the currently selected toggle button
    private String getSelectedToggleButton() {
        RadioButton selected = (RadioButton) passwordTypes.getSelectedToggle();
        return selected.getText();
    }

    // Returns the slider value cast to an int
    private int getLength() {
        double val = lengthSlider.getValue();
        return (int) val;
    }

    private boolean isIncludeUppercase() {
        return includeUppercase.isSelected();
    }

    private boolean isIncludeNumbers() {
        return includeNumbers.isSelected();
    }

    private boolean isIncludeSpecial() {
        return includeSpecial.isSelected();
    }

    // Notifies the parentController that the user has pressed the "generate" button
    @FXML
    public void generateButtonClicked() {
        parentController.generate(getSelectedToggleButton(), getLength(), isIncludeUppercase(), isIncludeNumbers(), isIncludeSpecial());
    }

}
