package com.example.passwordmanager.Model.Entries;

import com.example.passwordmanager.Model.EntriesListHandler;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/** Represents a category that can contain and maintain passwordEntries **/

public class CategoryEntry implements DisplayableEntry {
    private String name;
    private boolean isOpen;
    private List<PasswordEntry> passwordEntries;        // could make into a priorityQueue prioritized by frequency

    private EntriesListHandler handler;

    public CategoryEntry(String name) {
        this.name = name;
        passwordEntries = new LinkedList<>();
    }

    // adds a passwordEntry to this category
    public void addPasswordEntry(PasswordEntry passwordEntry) {
        this.passwordEntries.add(passwordEntry);
        passwordEntry.addToCategory(this);
    }

    // removes a passwordEntry from this category
    public void removePasswordEntry(PasswordEntry passwordEntry) {
        this.passwordEntries.remove(passwordEntry);
        passwordEntry.removeFromItsCategory();
    }

    // sets the name of this category
    public void setName(String name) {
        this.name = name;
    }

    // returns this category's name
    public String getName() {
        return this.name;
    }

    @Override
    public String getUnderName() {  // problem, has to implement interface method it does not use, bad OOP, find alternative
        return null;
    }

    @Override
    public Image getImage() throws IOException {    // problem, has to implement interface method it does not use, bad OOP, find alternative
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getEntryId() {
        return 0;
    }

    @Override
    public String getSearchTerm() {
        return null;
    }

    // returns whether this category is open or not, meaning a user has clicked on it to reveal its internal passwordEntries
    public boolean isOpen() {
        return isOpen;
    }

    // sets this category as open
    public void setAsOpen() {
        this.isOpen = true;
    }

    // sets this category as closed
    public void setAsClose() {
        this.isOpen = false;
    }

    // returns this category's internal passwordEntries
    public List<PasswordEntry> getPasswordEntries() {
        return this.passwordEntries;
    }

    // returns the number of passwordEntries this category currently contains
    public int getNrOfPasswords() {
        return passwordEntries.size();
    }

}
