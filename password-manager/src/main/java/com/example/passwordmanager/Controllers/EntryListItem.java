package com.example.passwordmanager.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class EntryListItem extends AnchorPane {

    //private Model = getInstance();

    @FXML private AnchorPane entryAnchorPane;
    @FXML private ImageView iconImageView;
    @FXML private Label nameLabel, underNameLabel, lastModifiedLabel, modifiedLabel;

    // Used for testing
    public EntryListItem() {
        // Location not set error
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/entry_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.nameLabel.setText("This will be a name in the future");
        this.underNameLabel.setText("examplemail@mail.com");
        this.lastModifiedLabel.setText("This was last modified:");
        this.modifiedLabel.setText("Long time ago");


    }
}
