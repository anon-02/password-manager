package com.example.passwordmanager;

import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    /* Home View */
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField searchTextField;
    @FXML private Button addEntryButton, logoutButton;
    @FXML private FlowPane allEntrysFlowPane, detailViewFlowPane;

    /* Create Entry View */
    @FXML private AnchorPane createEntryAnchorPane;
    @FXML private ImageView createEntryExit;
    @FXML private ChoiceBox<String> entryType;
    private final String[] entryTypes = {"Account", "Card", "Wifi", "Secure note"};
    @FXML private FlowPane injectEntryType;

    /* Detail view */
    @FXML private AnchorPane cancelSavePane, editPane;
    @FXML private Button editButton, cancelButton, saveButton, removeButton;
    @FXML private Label editLabel;

    private fxmlHelper helper = fxmlHelper.getInstance();
    private DetailViewItem currentDetailItem;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);
        clearDetailView();

        try {
            updateEntryList();
        } catch (IOException | InvalidAlgorithmParameterException | SQLException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
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

    private void updateEntryList() throws IOException, InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        allEntrysFlowPane.getChildren().clear();
        for (DisplayableEntry displayableEntry : EncryptionBuffer.retrieveEntries()) { // Temporary just for proof of concept
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
                this.injectEntryType.getChildren().add(new CreateAccount(this));
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
    public void handleSaveButtonPressed() throws IOException, InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        closeButtonPressed();
        updateEntryList();
    }

    // Creates the correct detail view per given entry
    public void populateDetailView(DisplayableEntry entry) {
        detailViewFlowPane.getChildren().clear();
        DetailViewItem detailViewItem;

        if (entry instanceof AccountEntry) {
            detailViewItem = new DetailViewItem((AccountEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        }
        else if (entry instanceof CardEntry) {
            detailViewItem = new DetailViewItem((CardEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        }
        else if (entry instanceof WifiEntry) {
            detailViewItem = new DetailViewItem((WifiEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        }
        else {
            detailViewItem = new DetailViewItem((SecureNoteEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        }
        editButton.setVisible(true);
        removeButton.setVisible(true);
        currentDetailItem = detailViewItem;
    }

    @FXML
    public void editButtonPressed() {
        toggleEditingMode();

        cancelSavePane.toFront();
    }

    @FXML
    public void removeButtonPressed() throws SQLException {
        // TODO handle removal of entry with database
        DisplayableEntry currentEntry = currentDetailItem.getEntry();
        System.out.println("the selected entry about to be deleted "+ currentEntry);
        EncryptionBuffer.deleteEntry(currentEntry);
        clearDetailView();

    }

    private void clearDetailView() {
        detailViewFlowPane.getChildren().clear();
        editButton.setVisible(false);
        removeButton.setVisible(false);
    }
    @FXML
    public void cancelButtonPressed() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        toggleEditingMode();
        updateEntryList();


        editPane.toFront();
    }

    @FXML
    public void saveButtonPressed() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        // TODO handle save action
        currentDetailItem.updateDetailEntry();
        toggleEditingMode();
        updateEntryList();

        editPane.toFront();
    }

    private void toggleEditingMode() {
        editLabel.setVisible(!editLabel.isVisible());
        currentDetailItem.toggleEditable();
    }

    @FXML
    public void logoutButtonPressed() {
        // TODO handle user when logged out
        helper.navigateTo(mainAnchorPane, "login_view.fxml");
    }

}
