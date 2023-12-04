package com.example.passwordmanager;

import com.example.passwordmanager.Model.CategoryEntry;
import com.example.passwordmanager.Model.DisplayableEntry;
import com.example.passwordmanager.Model.PasswordEntry;
import com.example.passwordmanager.Model.User;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    /* Home View */
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TextField searchTextField;
    @FXML private Button addEntryButton;
    @FXML private FlowPane allEntrysFlowPane, detailViewFlowPane;

    /* Create Entry View */
    @FXML private AnchorPane addChooserAnchorPane;
    @FXML private AnchorPane createCategoryAnchorPane;
    @FXML private AnchorPane createEntryAnchorPane;
    @FXML private AnchorPane categoryChooser;
    @FXML private AnchorPane editCategoryAnchorPane;

    @FXML private ImageView createEntryExit;
    @FXML private ChoiceBox<String> entryType;
    private final String[] entryTypes = {"Account", "Card", "Wifi", "Secure note"};
    //@FXML private Button saveButton;
    @FXML private FlowPane injectEntryType;

    @FXML private FlowPane chooseCategoryFlowPane;
    @FXML private FlowPane removeFromFlowPane;
    @FXML private FlowPane addToFlowPane;

    @FXML private TextField categoryName;

    @FXML private AnchorPane createCategoryFront;

    @FXML private AnchorPane extended;
    @FXML private AnchorPane original;

    /* Detail view */
    @FXML private Button editButton, cancelButton, saveButton, deleteButton;


    private fxmlHelper helper = fxmlHelper.getInstance();
    ArrayList<DisplayableEntry> entries = new ArrayList<>();
    User user = new User(1, "username", "password");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entryType.getItems().addAll(entryTypes);
        entryType.setValue(entryTypes[0]);
        editButton.setVisible(false);
        deleteButton.setVisible(false);

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

    public void updateEntryList() throws IOException {
        allEntrysFlowPane.getChildren().clear();
        //for (DisplayableEntry displayableEntry : this.entries) { // Temporary just for proof of concept
        //    allEntrysFlowPane.getChildren().add(new EntryListItem(displayableEntry, this));
        //}
        List<DisplayableEntry> allEntries = user.getAllEntries();
        for (DisplayableEntry entry: allEntries) {
            allEntrysFlowPane.getChildren().add(EntryListItemFactory.makeEntryListItem(entry, this));
        }
    }


    public void updateCategoryChooserList(PasswordEntry passwordEntry) throws IOException {
        this.chooseCategoryFlowPane.getChildren().clear();
        List<CategoryEntry> currentCategories = user.getCategories();

        for (CategoryEntry categoryEntry : currentCategories) {
            CategoryEntryListItemSmall categoryEntryListItemSmall = new CategoryEntryListItemSmall(categoryEntry, this);
            this.chooseCategoryFlowPane.getChildren().add(categoryEntryListItemSmall);
            categoryEntryListItemSmall.setTempPasswordEntry(passwordEntry);
        }
    }

    public void updateCategoryChooserListExtended(PasswordEntry passwordEntry) throws IOException {
        this.removeFromFlowPane.getChildren().clear();
        this.addToFlowPane.getChildren().clear();
        List<CategoryEntry> currentCategories = new LinkedList<>(user.getCategories());
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
    public void addButtonPressed() {
        addChooserAnchorPane.toFront();
    }

    @FXML
    public void addCategoryEntryButtonPressed() {
        mainAnchorPane.toFront();
        categoryName.setText("");
        createCategoryAnchorPane.toFront();
    }

    @FXML
    public void addPasswordEntryButtonPressed() {
        mainAnchorPane.toFront();
        createEntryAnchorPane.toFront();
        updateCreateView();
    }

    public void categoryOption(PasswordEntry passwordEntry) throws IOException {
        openCategoryChooser();
        updateCategoryChooserList(passwordEntry);
    }

    public void categoryOptionExtended(PasswordEntry passwordEntry) throws IOException {
        openCategoryChooserExtended();
        System.out.println(user.getCategories());
        updateCategoryChooserListExtended(passwordEntry);
        System.out.println(user.getCategories());
    }

    public void openCategoryChooser() {
        mainAnchorPane.toFront();
        categoryChooser.toFront();
        original.toFront();
    }

    public void openCategoryChooserExtended() {
        openCategoryChooser();
        extended.toFront();
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


    @FXML
    public void saveCategoryButtonPressed() throws IOException {
        user.addCategoryEntry(new CategoryEntry(categoryName.getText()));
        handleSaveButtonPressed();
    }


    public void addPasswordEntry(PasswordEntry entry) {   // addEntry
        this.user.addPasswordEntry(entry);
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
        else if (entry instanceof WifiEntry) {
            detailViewFlowPane.getChildren().add(new DetailViewItem((WifiEntry) entry, this));
        }
        else if (entry instanceof SecureNoteEntry) {
            detailViewFlowPane.getChildren().add(new DetailViewItem((SecureNoteEntry) entry, this));
        }
        editButton.setVisible(true);
        deleteButton.setVisible(true);
    }
}