package com.example.passwordmanager.Controller;

import com.example.passwordmanager.Model.DetailItemFactory;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.DetailItemImpl;
import com.example.passwordmanager.Model.Entries.*;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.AccountDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.CardDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.SecureNoteDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.WifiDetailItem;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * Controller for the detail view
 */
public class DetailViewController {

    private final MainViewManager parentManager;
    private DetailItemImpl currentDetailItem = null;

    public DetailViewController(MainViewManager manager) {
        this.parentManager = manager;
    }

    // Injects the correct DetailItem fxml into the detail view
    public void handleEntryClicked(FlowPane pane, DisplayableEntry entry) {
        pane.getChildren().clear();
        AnchorPane detailItem = DetailItemFactory.makeDetailItem(entry, this);
        pane.getChildren().add(detailItem);
        currentDetailItem = (DetailItemImpl) detailItem;
    }

    public DisplayableEntry getCurrentDetailItem() {
        return currentDetailItem.getEntry();
    }

    // Toggles the detailItem's fields editable property
    public void toggleEditableCurrentEntry() {
        currentDetailItem.toggleEditable();
    }

    // Updates the currently selected entry with the fields in the detail view
    public void updateCurrentEntry() {
        currentDetailItem.updateEntry();
    }
}
