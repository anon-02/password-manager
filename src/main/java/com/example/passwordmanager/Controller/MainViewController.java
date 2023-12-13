package com.example.passwordmanager.Controller;

import com.example.passwordmanager.DisplayableEntry;
import com.example.passwordmanager.EntriesListHandler;
import com.example.passwordmanager.fxmlHelper;

import java.util.ArrayList;

/**
 * Controller for the applications main view
 * Handles all user input
 */
public class MainViewController {

    private final fxmlHelper helper;
    private EntriesListHandler entriesHandler;

    public MainViewController() {
        this.helper = fxmlHelper.getInstance();
    }

    /*public ArrayList<DisplayableEntry> getAllEntries() {

    }*/
}
