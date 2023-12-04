package com.example.passwordmanager;

import com.example.passwordmanager.Model.CategoryEntry;
import com.example.passwordmanager.Model.PasswordEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryEntryListItemSmall extends AnchorPane {
    @FXML
    private AnchorPane categoryEntrySmallAnchorPane;
    @FXML
    private Label categoryName;
    @FXML
    private Label nrOfPasswords;

    MainViewController parentController;

    private CategoryEntry categoryEntry;

    private PasswordEntry tempPasswordEntry;

    public CategoryEntryListItemSmall(CategoryEntry categoryEntry, MainViewController controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/categoryListItemLighter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        this.parentController = controller;
        this.categoryEntry = categoryEntry;

        this.categoryName.setText(categoryEntry.getName().toUpperCase());

        setNrOfPasswords();
    }

    public void setNrOfPasswords() {
        int nrOfPasswords = categoryEntry.getNrOfPasswords();
        if (nrOfPasswords > 0) {
            this.nrOfPasswords.setText(nrOfPasswords + " passwords");
        } else {
            this.nrOfPasswords.setText("");
        }
    }

    public void setTempPasswordEntry(PasswordEntry passwordEntry) {
        this.tempPasswordEntry = passwordEntry;
    }

    @FXML
    private void onClick() throws IOException {
        if (tempPasswordEntry.isInCategory()) {
            if (tempPasswordEntry.getCategory() == categoryEntry) {
                parentController.user.removePasswordEntryFromCategory(categoryEntry, tempPasswordEntry);
                parentController.updateEntryList();
                parentController.closeButtonPressed();
                return;
            }
        }
        parentController.user.movePasswordEntryToCategory(categoryEntry, tempPasswordEntry);
        parentController.updateEntryList();
        parentController.closeButtonPressed();
    }
}
