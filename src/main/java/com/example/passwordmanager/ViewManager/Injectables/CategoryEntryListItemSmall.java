package com.example.passwordmanager.ViewManager.Injectables;

import com.example.passwordmanager.Model.Entries.CategoryEntry;
import com.example.passwordmanager.Model.Entries.PasswordEntry;
import com.example.passwordmanager.ViewManager.MainViewManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class CategoryEntryListItemSmall extends AnchorPane {
    @FXML
    private AnchorPane categoryEntrySmallAnchorPane;
    @FXML
    private Label categoryName;
    @FXML
    private Label nrOfPasswords;

    MainViewManager parentController;
    private final fxmlHelper helper = fxmlHelper.getInstance();
    private CategoryEntry categoryEntry;
    private PasswordEntry tempPasswordEntry;

    public CategoryEntryListItemSmall(CategoryEntry categoryEntry, MainViewManager controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("categoryListItemLighter.fxml"));
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
            if (nrOfPasswords == 1)
                this.nrOfPasswords.setText("1 password");
            else
                this.nrOfPasswords.setText(nrOfPasswords + " passwords");
        }
        else {
            this.nrOfPasswords.setText("");
        }
    }

    public void setTempPasswordEntry(PasswordEntry passwordEntry) {
        this.tempPasswordEntry = passwordEntry;
    }

    @FXML
    private void onClick() throws IOException, InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (tempPasswordEntry.isInCategory()) {
            if (tempPasswordEntry.getCategory() == categoryEntry) {
                parentController.entriesHandler.removePasswordEntryFromCategory(categoryEntry, tempPasswordEntry);
                parentController.updateEntryList();
                parentController.closeButtonPressed();
                return;
            }
        }
        parentController.entriesHandler.movePasswordEntryToCategory(categoryEntry, tempPasswordEntry);
        parentController.updateEntryList();
        parentController.closeButtonPressed();
    }
}


