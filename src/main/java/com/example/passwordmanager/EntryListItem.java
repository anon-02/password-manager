package com.example.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

// This will be a base class and every other type of item will be a subclass
public class EntryListItem extends AnchorPane {

    //private Model = getInstance();

    @FXML private AnchorPane entryAnchorPane;
    @FXML private ImageView iconImageView;
    @FXML private Label nameLabel, underNameLabel, lastModifiedLabel, modifiedLabel;

    MainViewController parentController;


    // This constructor is used for testing only
    public EntryListItem(Entry entry, MainViewController controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/entry_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;

        this.nameLabel.setText(entry.getName());
        this.underNameLabel.setText(entry.getUnderName());
        this.lastModifiedLabel.setText("Last modified:");
        this.modifiedLabel.setText("Today 13:50");
        this.iconImageView.setImage(entry.getImage());
    }
}

