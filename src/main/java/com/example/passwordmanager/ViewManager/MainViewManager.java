package com.example.passwordmanager.ViewManager;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Controller.MainViewController;
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

import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewManager implements Initializable {

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


    // Category related
    @FXML private AnchorPane addChooserAnchorPane;
    @FXML private AnchorPane createCategoryAnchorPane;
    @FXML private AnchorPane categoryChooser;
    @FXML private AnchorPane editCategoryAnchorPane;
    @FXML private FlowPane chooseCategoryFlowPane;
    @FXML private FlowPane removeFromFlowPane;
    @FXML private FlowPane addToFlowPane;
    @FXML private TextField categoryName;
    @FXML private TextField categoryEditName;
    @FXML private Label addToCategory;
    @FXML private Label addToCategoryExtended;
    @FXML private AnchorPane extended;
    @FXML private AnchorPane original;
    @FXML private ImageView detailClose;

    private fxmlHelper helper = fxmlHelper.getInstance();
    private DetailViewItem currentDetailItem;

    private CategoryEntry categoryEditing;
    private PasswordEntry passwordEntryEditing;

    public EntriesListHandler entriesHandler;
    private MainViewController controller;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = new MainViewController();

        entriesHandler = new EntriesListHandler(EncryptionBuffer.retrieveEntries());

        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);

        clearDetailView();
        updateEntryList();


        entryType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                updateCreateView();
            }
        });

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                System.out.println("Search: " + searchTextField.getText());
                handleSearchAction(searchTextField.getText());
            }
        });
    }

    // Iterates through all entries, creates a list of entries that matches the search term.
    // If the search term is empty, show all entries.
    private void handleSearchAction(String s) {
        String searchTerm = s.replace(" ", "").toLowerCase();
        if (searchTerm.isEmpty()) {
            updateEntryList();
        } else {
            List<DisplayableEntry> currentPasswordEntries = new LinkedList<>(entriesHandler.getAllPasswordEntries());
            displayResult(Searcher.search(currentPasswordEntries, searchTerm));
            }
    }

    private void displayResult(ArrayList<DisplayableEntry> matchingEntries) {
        if (!(matchingEntries.isEmpty()))
            updateEntryList(matchingEntries);
        else
            updateEntryList();
    }

    public void updateEntryList() {
        allEntrysFlowPane.getChildren().clear();
        for (DisplayableEntry entry : entriesHandler.displayEntries()) {
            allEntrysFlowPane.getChildren().add(EntryListItemFactory.makeEntryListItem(entry, this));
        }
    }

    protected void updateEntryList (ArrayList<DisplayableEntry> array) {
        allEntrysFlowPane.getChildren().clear();
        for (DisplayableEntry entry : array) {
            allEntrysFlowPane.getChildren().add(EntryListItemFactory.makeEntryListItem(entry, this));
        }
    }

    public void updateCategoryChooserList (PasswordEntry passwordEntry) {
        this.chooseCategoryFlowPane.getChildren().clear();
        List<CategoryEntry> currentCategories = entriesHandler.getCategories();

        for (CategoryEntry categoryEntry : currentCategories) {
            CategoryEntryListItemSmall categoryEntryListItemSmall = new CategoryEntryListItemSmall(categoryEntry, this);
            this.chooseCategoryFlowPane.getChildren().add(categoryEntryListItemSmall);
            categoryEntryListItemSmall.setTempPasswordEntry(passwordEntry);
        }
    }

    public void updateCategoryChooserListExtended (PasswordEntry passwordEntry) {
        this.removeFromFlowPane.getChildren().clear();
        this.addToFlowPane.getChildren().clear();
        List<CategoryEntry> currentCategories = new LinkedList<>(entriesHandler.getCategories());
        currentCategories.remove(passwordEntry.getCategory());

        for (CategoryEntry categoryEntry : currentCategories) {
            CategoryEntryListItemSmall categoryEntryListItemSmall = new CategoryEntryListItemSmall(categoryEntry, this);
            this.addToFlowPane.getChildren().add(categoryEntryListItemSmall);
            categoryEntryListItemSmall.setTempPasswordEntry(passwordEntry);
        }
        CategoryEntryListItemSmall categoryEntryListItemSmallRemove = new CategoryEntryListItemSmall(passwordEntry.getCategory(), this);
        removeFromFlowPane.getChildren().add(categoryEntryListItemSmallRemove);
        categoryEntryListItemSmallRemove.setTempPasswordEntry(passwordEntry);
    }

    @FXML
    public void addButtonPressed () {
        addChooserAnchorPane.toFront();
    }

    @FXML
    public void addCategoryEntryButtonPressed () {
        closeButtonPressed();
        categoryName.setText("");
        createCategoryAnchorPane.toFront();
    }

    @FXML
    public void addPasswordEntryButtonPressed () {
        closeButtonPressed();
        createEntryAnchorPane.toFront();
        updateCreateView();
    }


    public void categoryOption (PasswordEntry passwordEntry) {
        openCategoryChooser();
        updateCategoryChooserList(passwordEntry);
    }

    public void categoryOptionExtended (PasswordEntry passwordEntry) {
        openCategoryChooserExtended();
        System.out.println(entriesHandler.getCategories());
        updateCategoryChooserListExtended(passwordEntry);
        System.out.println(entriesHandler.getCategories());
    }

    public void openCategoryChooserExtended () {
        openCategoryChooser();
        setAddToCategoryTextExtended();
        extended.toFront();
    }


    public void openCategoryChooser() {
        mainAnchorPane.toFront();
        categoryChooser.toFront();
        setAddToCategoryText();
        original.toFront();
    }

    public void setAddToCategoryText() {
        if (entriesHandler.getCategories().size() > 0) {
            addToCategory.setText("Add To");
        }
        else
            addToCategory.setText("");
    }

    public void setAddToCategoryTextExtended() {
        if (entriesHandler.getCategories().size() > 1) {
            addToCategoryExtended.setText("Add To");
        }
        else
            addToCategoryExtended.setText("");
    }

    public void openCategoryEdit(CategoryEntry category) {
        categoryEditing = category;
        editCategoryAnchorPane.toFront();
        categoryEditName.setText(category.getName());
    }

    public void editCategoryDelete()  {
        entriesHandler.deleteCategoryEntry(categoryEditing);
        updateEntryList();
        closeButtonPressed();
    }

    public void editCategorySave()  {
        categoryEditing.setName(categoryEditName.getText());
        updateEntryList();
        closeButtonPressed();
    }

    // Updates the create view based on the entry type
    public void updateCreateView () {
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
    public void closeButtonPressed () {
        mainAnchorPane.toFront();
    }

    @FXML
    public void handleSaveButtonPressed () {
        closeButtonPressed();
        updateEntryList();
    }

    @FXML
    public void saveCategoryButtonPressed () throws IndexOutOfBoundsException {
        entriesHandler.addCategoryEntry(new CategoryEntry(categoryName.getText()));
        handleSaveButtonPressed();
    }

    public void addPasswordEntry (PasswordEntry entry) {   // addEntry
        this.entriesHandler.addPasswordEntry(entry);
    }

    // Creates the correct detail view per given entry
    public void populateDetailView (DisplayableEntry entry) {
        passwordEntryEditing = (PasswordEntry) entry;
        detailViewFlowPane.getChildren().clear();
        DetailViewItem detailViewItem;

        if (entry instanceof AccountEntry) {
            detailViewItem = new DetailViewItem((AccountEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        } else if (entry instanceof CardEntry) {
            detailViewItem = new DetailViewItem((CardEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        } else if (entry instanceof WifiEntry) {
            detailViewItem = new DetailViewItem((WifiEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        } else {
            detailViewItem = new DetailViewItem((SecureNoteEntry) entry, this);
            detailViewFlowPane.getChildren().add(detailViewItem);
        }
        editButton.setVisible(true);
        removeButton.setVisible(true);
        detailClose.setVisible(true);
        currentDetailItem = detailViewItem;
    }

    @FXML
    public void editButtonPressed () {
        toggleEditingMode();
        cancelSavePane.toFront();
    }

    @FXML
    public void removeButtonPressed() {
        // TODO handle removal of entry with database
        DisplayableEntry currentEntry = currentDetailItem.getEntry();
        System.out.println("the selected entry about to be deleted "+ currentEntry);
        clearDetailView();
        EncryptionBuffer.deleteEntry(currentEntry);
        updateEntryList();
        // TODO Kolla över detta
        entriesHandler.deletePasswordEntry(passwordEntryEditing);
    }

    @FXML
    private void clearDetailView () {
        detailViewFlowPane.getChildren().clear();
        editButton.setVisible(false);
        removeButton.setVisible(false);
        detailClose.setVisible(false);
    }

    @FXML
    public void cancelButtonPressed() {
        toggleEditingMode();
        updateEntryList();
        editPane.toFront();
    }

    @FXML
    public void saveButtonPressed () {
        // TODO handle save action
        currentDetailItem.updateDetailEntry();
        toggleEditingMode();
        updateEntryList();

        editPane.toFront();
    }

    private void toggleEditingMode () {
        editLabel.setVisible(!editLabel.isVisible());
        currentDetailItem.toggleEditable();
    }

    @FXML
    public void logoutButtonPressed () {
        // TODO handle user when logged out
        entriesHandler.saveAllEntries();
        helper.navigateTo(mainAnchorPane, "login_view.fxml");
    }
}

