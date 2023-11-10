package com.example.passwordmanager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    /* Home View */
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField searchTextField;
    @FXML private Button addEntryButton;
    @FXML private FlowPane allEntrysFlowPane;

    /* Create Entry View */
    @FXML private AnchorPane createEntryAnchorPane;
    @FXML private ImageView createEntryExit;
    @FXML private ChoiceBox<String> entryType;
    private final String[] entryTypes = {"Login", "Card"};
    @FXML private Button saveButton;
    @FXML private FlowPane injectEntryType;




    ArrayList<Entry> entries = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.setText("Search");
        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);

        try {
            updateEntryList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        entryType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                updateCreateView();
            }
        });

        searchTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && Objects.equals(searchTextField.getText(), "Search")) {
                searchTextField.setText("");
            } else {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Search");
                }
            }
        });

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                System.out.println("Search: " + searchTextField.getText());
                //handleSearchAction(); TODO implement search
            }
        });
    }

    private void updateEntryList() throws IOException {
        allEntrysFlowPane.getChildren().clear();
        for (Entry entry : this.entries) { // Temporary just for proof of concept
            allEntrysFlowPane.getChildren().add(new EntryListItem(entry, this));
        }
    }

    @FXML
    public void addButtonPressed() {
        createEntryAnchorPane.toFront();
        updateCreateView();
    }

    // Updates the create view based on the entry type
    public void updateCreateView() {
        this.injectEntryType.getChildren().clear();
        String currentType = entryType.getValue();
        System.out.println(currentType);

        // Add case for every new type of entry
        switch (currentType) {
            case "Login":
                this.injectEntryType.getChildren().add(new CreateEditLogin(this));
                return;
            case "Card":
                this.injectEntryType.getChildren().add(new CreateEditCard(this));
                return;
            default:
        }
    }

    @FXML
    public void closeButtonPressed() {
        mainAnchorPane.toFront();
    }

    @FXML
    public void handleSaveButtonPressed() throws IOException {
        closeButtonPressed();
        updateEntryList();
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

}
