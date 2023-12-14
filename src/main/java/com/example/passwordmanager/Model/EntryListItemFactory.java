package com.example.passwordmanager.Model;

import com.example.passwordmanager.ViewManager.Injectables.EntryListItemIndented;
import com.example.passwordmanager.Model.Entries.CategoryEntry;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.Entries.PasswordEntry;
import com.example.passwordmanager.ViewManager.Injectables.CategoryEntryListItem;
import com.example.passwordmanager.ViewManager.Injectables.EntryListItem;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Factory for creating entryListItems
 */
public class EntryListItemFactory {

    // Returns the correct EntryListItem based on the given entry
    public static AnchorPane makeEntryListItem(DisplayableEntry entry, MainViewManager controller) {
        try {
            if (entry instanceof PasswordEntry) {
                if (((PasswordEntry) entry).isInCategory())
                    return new EntryListItemIndented((PasswordEntry) entry, controller);
                else
                    return new EntryListItem((PasswordEntry) entry, controller);
            }
            else if (entry instanceof CategoryEntry) {
                return new CategoryEntryListItem((CategoryEntry) entry, controller);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}