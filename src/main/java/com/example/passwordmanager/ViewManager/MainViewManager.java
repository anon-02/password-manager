package com.example.passwordmanager.ViewManager;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Controller.CreateEntryController;
import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Controller.MainViewController;
import com.example.passwordmanager.Model.Entries.*;
import com.example.passwordmanager.Model.EntriesListHandler;
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

import java.net.URL;

import java.util.ArrayList;
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
    @FXML private FlowPane injectEntryType;
    private final String[] entryTypes = {"Account", "Card", "Wifi", "Secure note"};

    /* Detail view */
    @FXML private AnchorPane cancelSavePane, editPane, deleteConfirmationPane;
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
    private CategoryEntry categoryEditing;

    public EntriesListHandler entriesHandler;
    private MainViewController mainViewController;
    private CreateEntryController createEntryController;
    private DetailViewController detailViewController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mainViewController = new MainViewController(this);
        this.createEntryController = new CreateEntryController(this);
        this.detailViewController = new DetailViewController(this);
        this.entriesHandler = EntriesListHandler.getInstance();

        clearDetailView();
        updateEntryList();

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                searchAction();
            }
        });

        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);

        entryType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                createEntryController.updateCreateView(injectEntryType, entryType.getValue());
            }
        });
    }

    // Iterates through all entries, creates a list of entries that matches the search term.
    // If the search term is empty, show all entries.
    private void searchAction() {
        mainViewController.handleSearchAction(allEntrysFlowPane, searchTextField.getText());
    }

    public void updateEntryList() {
        mainViewController.handleUpdateEntryList(allEntrysFlowPane);
    }

    public void updateEntryList (ArrayList<DisplayableEntry> array) {
        mainViewController.handleUpdateEntryList(allEntrysFlowPane, array);
    }

    public void updateCategoryChooserList (PasswordEntry passwordEntry) {
        mainViewController.handleUpdateCategoryChooserList(chooseCategoryFlowPane, passwordEntry);
    }

    public void updateCategoryChooserListExtended (PasswordEntry passwordEntry) {
        mainViewController.updateCategoryChooserListExtended(removeFromFlowPane, addToFlowPane, passwordEntry);
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
        createEntryController.updateCreateView(injectEntryType, entryType.getValue());
    }

    public void categoryOption (PasswordEntry passwordEntry) {
        openCategoryChooser();
        updateCategoryChooserList(passwordEntry);
    }

    public void categoryOptionExtended (PasswordEntry passwordEntry) {
        openCategoryChooserExtended();
        updateCategoryChooserListExtended(passwordEntry);
        if (helper.isDebug()) {
            System.out.println(entriesHandler.getCategories());
            System.out.println(entriesHandler.getCategories());
        }
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
        if (!entriesHandler.getCategories().isEmpty()) {
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
        System.out.println("Create category pressed");
        entriesHandler.addCategoryEntry(new CategoryEntry(categoryName.getText()));
        handleSaveButtonPressed();
    }

    // Creates the correct detail view per given entry
    public void entryClicked(DisplayableEntry entry) {
        detailViewController.handleEntryClicked(detailViewFlowPane, entry);

        editButton.setVisible(true);
        removeButton.setVisible(true);
        detailClose.setVisible(true);
    }

    @FXML
    public void editButtonPressed () {
        toggleEditingMode();
        cancelSavePane.toFront();
    }

    @FXML
    public void deleteConfirmation() {
        deleteConfirmationPane.toFront();
    }

    @FXML
    public void removeButtonPressed() {
        // TODO handle removal of entry with database
        DisplayableEntry currentEntry = detailViewController.getCurrentDetailItem();
        System.out.println("the selected entry about to be deleted "+ currentEntry);
        clearDetailView();
        EncryptionBuffer.deleteEntry(detailViewController.getCurrentDetailItem());
        closeButtonPressed();
        updateEntryList();
        // TODO Kolla över detta
        entriesHandler.deletePasswordEntry((PasswordEntry) detailViewController.getCurrentDetailItem());
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
        detailViewController.updateCurrentEntry();
        toggleEditingMode();
        updateEntryList();
        editPane.toFront();
    }

    private void toggleEditingMode () {
        editLabel.setVisible(!editLabel.isVisible());
        detailViewController.toggleEditableCurrentEntry();
    }

    @FXML
    public void logoutButtonPressed () {
        mainViewController.logoutButtonPressed(mainAnchorPane);
    }
}

