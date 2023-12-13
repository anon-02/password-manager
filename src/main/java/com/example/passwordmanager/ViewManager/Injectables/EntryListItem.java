package com.example.passwordmanager.ViewManager.Injectables;

import com.example.passwordmanager.Model.Entries.PasswordEntry;
import com.example.passwordmanager.ViewManager.MainViewManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

// This will be a base class and every other type of item will be a subclass
public class EntryListItem extends AnchorPane {

    //private Model = getInstance();

    @FXML private AnchorPane entryAnchorPane;
    @FXML private ImageView iconImageView;
    @FXML private Label nameLabel, underNameLabel, lastModifiedLabel, modifiedLabel;

    @FXML private Button addToCategoryButton;
    @FXML private ImageView categoryButton;

    MainViewManager parentController;
    private final PasswordEntry entry;
    private final fxmlHelper helper = fxmlHelper.getInstance();


    // This constructor is used for testing only
    public EntryListItem(PasswordEntry entry, MainViewManager controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("entry_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        this.entry = entry;

        this.nameLabel.setText(entry.getName());
        this.underNameLabel.setText(entry.getUnderName());
        this.lastModifiedLabel.setText("Last modified:");
        this.modifiedLabel.setText("Today 13:50");
        this.iconImageView.setImage(entry.getImage());
    }

    @FXML
    public void categoryHoverIn(Event event) throws IOException {
        this.categoryButton.setImage(helper.getImage("categoryLabel2.jpg"));
    }

    @FXML
    public void categoryHoverOut(Event event) throws IOException {
        this.categoryButton.setImage(helper.getImage("category-icon-5.jpg"));
    }

    @FXML
    public void categoryButtonClicked(Event event) throws IOException {
        this.parentController.categoryOption(entry);
    }

    @FXML
    private void onClick() {
        parentController.entryClicked(entry);
    }

}

