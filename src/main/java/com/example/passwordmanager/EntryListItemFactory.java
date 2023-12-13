package com.example.passwordmanager;

import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EntryListItemFactory {

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