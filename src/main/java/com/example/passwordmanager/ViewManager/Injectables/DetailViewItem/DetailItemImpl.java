package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import javafx.scene.layout.AnchorPane;

public interface DetailItemImpl {
    void toggleEditable();
    void updateEntry();

    DisplayableEntry getEntry();
}
