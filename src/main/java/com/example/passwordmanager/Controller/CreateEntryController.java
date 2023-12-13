package com.example.passwordmanager.Controller;

import com.example.passwordmanager.ViewManager.CreateEntry.CreateAccount;
import com.example.passwordmanager.ViewManager.CreateEntry.CreateCard;
import com.example.passwordmanager.ViewManager.CreateEntry.CreateSecureNote;
import com.example.passwordmanager.ViewManager.CreateEntry.CreateWifi;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.scene.layout.FlowPane;

/**
 * Controller for the create-entry-view
 */
public class CreateEntryController {

    private final MainViewManager parentManager;

    public CreateEntryController(MainViewManager manager) {
        parentManager = manager;
    }

    // Injects the correct fxml-file based on the selected entry type
    public void updateCreateView (FlowPane pane, String entryType) {
        pane.getChildren().clear();
        // Add case for every new type of entry
        switch (entryType) {
            case "Account":
                pane.getChildren().add(new CreateAccount(parentManager));
                return;
            case "Card":
                pane.getChildren().add(new CreateCard(parentManager));
                return;
            case "Wifi":
                pane.getChildren().add(new CreateWifi(parentManager));
                return;
            case "Secure note":
                pane.getChildren().add(new CreateSecureNote(parentManager));
                return;
            default:
                System.out.println("Entry type not found");
        }
    }
}
