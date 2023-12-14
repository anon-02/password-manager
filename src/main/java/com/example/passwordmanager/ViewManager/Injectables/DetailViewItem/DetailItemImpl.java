package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import javafx.scene.layout.AnchorPane;

/**
 * Interface for every DetailItem
 * All should be able to be editable and be updated
 */
public interface DetailItemImpl {
    // Toggles the editable property of all the fields in the detail item
    void toggleEditable();

    // Updates the entry in the database
    void updateEntry();

    // Returns the entry used to fill the detail view
    DisplayableEntry getEntry();
}
