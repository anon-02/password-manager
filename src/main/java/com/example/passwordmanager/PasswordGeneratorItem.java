package com.example.passwordmanager;

import com.example.passwordmanager.Password.PassphraseGenerator;
import com.example.passwordmanager.Password.PasswordGenerator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class PasswordGeneratorItem extends AnchorPane {


    @FXML AnchorPane baseAnchorPane;

    /** Used for generating password **/
    @FXML private RadioButton passwordRadioButton, passphraseRadioButton;
    @FXML private Label lengthLabel;
    @FXML private Slider lengthSlider;
    @FXML private CheckBox includeUppercase, includeNumbers, includeSpecial;
    @FXML private Button generateButton;

    private int indicatorMaxWidth;

    private TextField invisibleTextField;
    private TextField visibleTextField;
    private DisplayableEntry connectedEntry;
    private MainViewController parentController;

    private ToggleGroup passwordTypes = new ToggleGroup();

    public PasswordGeneratorItem(String s, TextField invisible, TextField visible, MainViewController parentController) {
        this.invisibleTextField = invisible;
        this.visibleTextField = visible;
        this.parentController = parentController;

        FXMLLoader fxmlLoader = null;
        if (s.equals("create")) {
            fxmlLoader = new FXMLLoader(getClass().getResource("Views/password-generator.fxml"));
            indicatorMaxWidth = 250;
        } else if (s.equals("detail")){
            fxmlLoader = new FXMLLoader(getClass().getResource("Views/password-generator-small.fxml"));
            indicatorMaxWidth = 110;
        }
        assert fxmlLoader != null;
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        passwordRadioButton.setToggleGroup(passwordTypes);
        passphraseRadioButton.setToggleGroup(passwordTypes);
        init();

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    generateButtonClicked();
                } catch (InvalidAlgorithmParameterException | SQLException | NoSuchPaddingException | IllegalBlockSizeException | IOException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
                    e.printStackTrace();
                }
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

    public PasswordGeneratorItem(String s, TextField invisible, TextField visible, MainViewController parentController, DisplayableEntry entry) {
        this(s, invisible, visible, parentController);
        this.connectedEntry = entry;
    }

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
        lengthSlider.setValue(10);

        passwordRadioButton.setSelected(true);
    }

    private String getSelectedToggleButton() {
        RadioButton selected = (RadioButton) passwordTypes.getSelectedToggle();
        return selected.getText();
    }

    private int getLength() {
        double val = lengthSlider.getValue();
        return (int) val;
    }


    private boolean isPassword() {
        return getSelectedToggleButton().equals("Password");
    }

    private boolean isPassphrase() {
        return getSelectedToggleButton().equals("Passphrase");
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

    @FXML
    public void generateButtonClicked() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (isPassword()) {
            invisibleTextField.setText(PasswordGenerator.generatePassword(getLength(), isIncludeUppercase(), isIncludeNumbers(), isIncludeSpecial()));
            visibleTextField.setText(PasswordGenerator.generatePassword(getLength(), isIncludeUppercase(), isIncludeNumbers(), isIncludeSpecial()));
        }
        else if (isPassphrase()) {
            invisibleTextField.setText(PassphraseGenerator.generatePassphrase(getLength(), isIncludeUppercase(), isIncludeNumbers(), isIncludeSpecial()));
            visibleTextField.setText(PassphraseGenerator.generatePassphrase(getLength(), isIncludeUppercase(), isIncludeNumbers(), isIncludeSpecial()));
        }
        // else exception ?
        parentController.updateEntryList();
        parentController.populateDetailView(connectedEntry);
    }
}
