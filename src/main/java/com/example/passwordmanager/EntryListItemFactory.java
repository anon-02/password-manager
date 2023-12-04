package com.example.passwordmanager;

import com.example.passwordmanager.Model.CategoryEntry;
import com.example.passwordmanager.Model.DisplayableEntry;
import com.example.passwordmanager.Model.PasswordEntry;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EntryListItemFactory {

    public static AnchorPane makeEntryListItem(DisplayableEntry entry, MainViewController controller) throws IOException {
        if (entry instanceof PasswordEntry) {
            if (((PasswordEntry<?>) entry).isInCategory())
                return new EntryListItemIndented((PasswordEntry) entry, controller);
            else
                return new EntryListItem((PasswordEntry) entry, controller);
        }
        else if (entry instanceof CategoryEntry) {
            return new CategoryEntryListItem((CategoryEntry) entry, controller);
        }
        return null;
    }
}