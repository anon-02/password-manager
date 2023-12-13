package com.example.passwordmanager.Controller;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Model.Entries.CategoryEntry;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.Entries.PasswordEntry;
import com.example.passwordmanager.Model.EntriesListHandler;
import com.example.passwordmanager.Model.EntryListItemFactory;
import com.example.passwordmanager.Model.Searcher;
import com.example.passwordmanager.ViewManager.Injectables.CategoryEntryListItemSmall;
import com.example.passwordmanager.ViewManager.MainViewManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.LinkedList;
import java.util.List;

/**
 * Controller for the applications main view
 * Handles all user input
 */
public class MainViewController {

    private final EntriesListHandler entriesHandler;
    private final fxmlHelper helper;
    private final MainViewManager viewManager;

    public MainViewController(MainViewManager manager) {
        this.viewManager = manager;
        helper = fxmlHelper.getInstance();
        this.entriesHandler = EntriesListHandler.getInstance();
    }

    // Updates the entry list with all entries currently stored for the user
    public void handleUpdateEntryList(FlowPane pane) {
        pane.getChildren().clear();
        entriesHandler.saveAllEntries();
        for (DisplayableEntry entry : entriesHandler.displayEntries()) {
            pane.getChildren().add(EntryListItemFactory.makeEntryListItem(entry, viewManager));
        }
    }

    // Updates the entry list with the given list of entries
    public void handleUpdateEntryList(FlowPane pane, List<DisplayableEntry> array) {
        pane.getChildren().clear();
        for (DisplayableEntry entry : array) {
            pane.getChildren().add(EntryListItemFactory.makeEntryListItem(entry, viewManager));
        }
    }

    // Finds the entries that the matches with the search term
    public void handleSearchAction(FlowPane pane, String s) {
        String searchTerm = s.replace(" ", "").toLowerCase();
        if (searchTerm.isEmpty()) {
            handleUpdateEntryList(pane);
        } else {
            List<DisplayableEntry> currentPasswordEntries = new LinkedList<>(entriesHandler.getAllPasswordEntries());
            displayResult(pane, Searcher.search(currentPasswordEntries, searchTerm));
        }
    }

    private void displayResult(FlowPane pane, List<DisplayableEntry> matchingEntries) {
        if (!(matchingEntries.isEmpty()))
            handleUpdateEntryList(pane, matchingEntries);
        else
            handleUpdateEntryList(pane);
    }

    public void handleUpdateCategoryChooserList(FlowPane pane, PasswordEntry entry) {
        pane.getChildren().clear();
        List<CategoryEntry> currentCategories = entriesHandler.getCategories();

        for (CategoryEntry categoryEntry : currentCategories) {
            CategoryEntryListItemSmall categoryEntryListItemSmall = new CategoryEntryListItemSmall(categoryEntry, viewManager);
            pane.getChildren().add(categoryEntryListItemSmall);
            categoryEntryListItemSmall.setTempPasswordEntry(entry);
        }
    }

    public void updateCategoryChooserListExtended(FlowPane removeFrom, FlowPane addTo, PasswordEntry entry) {
        removeFrom.getChildren().clear();
        addTo.getChildren().clear();
        List<CategoryEntry> currentCategories = new LinkedList<>(entriesHandler.getCategories());
        currentCategories.remove(entry.getCategory());

        for (CategoryEntry categoryEntry : currentCategories) {
            CategoryEntryListItemSmall categoryEntryListItemSmall = new CategoryEntryListItemSmall(categoryEntry, viewManager);
            addTo.getChildren().add(categoryEntryListItemSmall);
            categoryEntryListItemSmall.setTempPasswordEntry(entry);
        }
        CategoryEntryListItemSmall categoryEntryListItemSmallRemove = new CategoryEntryListItemSmall(entry.getCategory(), viewManager);
        removeFrom.getChildren().add(categoryEntryListItemSmallRemove);
        categoryEntryListItemSmallRemove.setTempPasswordEntry(entry);
    }

    public void logoutButtonPressed(AnchorPane pane) {
        entriesHandler.saveAllEntries();
        helper.navigateTo(pane, "login_view.fxml");
    }
}
