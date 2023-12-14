package com.example.passwordmanager.Model;

import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Model.Entries.*;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.AccountDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.CardDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.SecureNoteDetailItem;
import com.example.passwordmanager.ViewManager.Injectables.DetailViewItem.WifiDetailItem;
import javafx.scene.layout.AnchorPane;

/**
 * Factory for creating detailViewItems
 */
public class DetailItemFactory {

    // Returns the correct detail item based on the type of entry
    public static AnchorPane makeDetailItem(DisplayableEntry entry, DetailViewController controller) {
        AnchorPane detailItem;
        if (entry instanceof AccountEntry) {
            detailItem = new AccountDetailItem((AccountEntry) entry, controller);
        } else if (entry instanceof CardEntry) {
            detailItem = new CardDetailItem((CardEntry) entry, controller);
        } else if (entry instanceof WifiEntry) {
            detailItem = new WifiDetailItem((WifiEntry) entry, controller);
        } else {
            detailItem = new SecureNoteDetailItem((SecureNoteEntry) entry, controller);
        }
        return detailItem;
    }
}
