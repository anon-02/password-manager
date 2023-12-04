package com.example.passwordmanager;

import com.example.passwordmanager.Model.PasswordEntry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class EntryListItemIndented extends AnchorPane {

    @FXML
    private AnchorPane entryAnchorPane;
    @FXML private ImageView iconImageView;
    @FXML private Label nameLabel, underNameLabel, lastModifiedLabel, modifiedLabel;

    @FXML private Button addToCategoryButton;

    @FXML private ImageView categoryButton;

    MainViewController parentController;
    private PasswordEntry entry;


    // This constructor is used for testing only
    public EntryListItemIndented(PasswordEntry entry, MainViewController controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/entry_listitem_indented.fxml"));
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


    public void categoryHoverIn(Event event) throws IOException {
        this.categoryButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/categoryLabel2.jpg")).openStream()));
    }

    public void categoryHoverOut(Event event) throws IOException {
        this.categoryButton.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/category-icon-5.jpg")).openStream()));
    }

    public void categoryButtonClicked(Event event) throws IOException {
        this.parentController.categoryOptionExtended(entry);
    }

    @FXML
    private void onClick() {
        parentController.populateDetailView(entry);
    }
}
