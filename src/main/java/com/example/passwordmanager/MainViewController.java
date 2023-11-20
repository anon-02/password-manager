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
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    /* Home View */
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField searchTextField;
    @FXML private Button addEntryButton;
    @FXML private FlowPane allEntrysFlowPane, detailViewFlowPane;

    /* Create Entry View */
    @FXML private AnchorPane createEntryAnchorPane;
    @FXML private ImageView createEntryExit;
    @FXML private ChoiceBox<String> entryType;
    private final String[] entryTypes = {"Account", "Card", "Wifi", "Secure note"};
    //@FXML private Button saveButton;
    @FXML private FlowPane injectEntryType;

    /* Detail view */
    @FXML private Button editButton, cancelButton, saveButton;


    private fxmlHelper helper = fxmlHelper.getInstance();
    ArrayList<DisplayableEntry> entries = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);
        editButton.setVisible(false);

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

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                System.out.println("Search: " + searchTextField.getText());
                //handleSearchAction(); TODO implement search
            }
        });
    }

    private void updateEntryList() throws IOException {
        allEntrysFlowPane.getChildren().clear();
        for (DisplayableEntry displayableEntry : this.entries) { // Temporary just for proof of concept
            allEntrysFlowPane.getChildren().add(new EntryListItem(displayableEntry, this));
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

        // Add case for every new type of entry
        switch (currentType) {
            case "Account":
                this.injectEntryType.getChildren().add(new CreateLogin(this));
                return;
            case "Card":
                this.injectEntryType.getChildren().add(new CreateCard(this));
                return;
            case "Wifi":
                this.injectEntryType.getChildren().add(new CreateWifi(this));
                return;
            case "Secure note":
                this.injectEntryType.getChildren().add(new CreateSecureNote(this));
                return;
            default:
                System.out.println("Entry type not found");
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

    public void addEntry(DisplayableEntry displayableEntry) {
        this.entries.add(displayableEntry);
    }

    // Creates the correct entry type for the detail view
    public void populateDetailView(DisplayableEntry entry) {
        detailViewFlowPane.getChildren().clear();
        if (entry instanceof AccountEntry) {
            detailViewFlowPane.getChildren().add(new DetailViewItem((AccountEntry) entry, this));
        }
        else if (entry instanceof CardEntry) {
            detailViewFlowPane.getChildren().add(new DetailViewItem((CardEntry) entry, this));
        }
        editButton.setVisible(true);
    }
}
