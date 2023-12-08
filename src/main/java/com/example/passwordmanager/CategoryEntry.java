package com.example.passwordmanager;

import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CategoryEntry implements DisplayableEntry {
    private String name;
    private boolean isOpen;
    private List<PasswordEntry> passwordEntries;        // could make into a priorityQueue prioritized by frequency

    private EntriesListHandler handler;

    public CategoryEntry(String name) {
        this.name = name;
        passwordEntries = new LinkedList<>();
    }

    public void addPasswordEntry(PasswordEntry passwordEntry) {
        this.passwordEntries.add(passwordEntry);
        passwordEntry.addToCategory(this);
    }

    public void removePasswordEntry(PasswordEntry passwordEntry) {
        this.passwordEntries.remove(passwordEntry);
        passwordEntry.removeFromItsCategory();
    }

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

    public boolean isOpen() {
        return isOpen;
    }

    public void setAsOpen() {
        this.isOpen = true;
    }

    public void setAsClose() {
        this.isOpen = false;
    }

    public List<PasswordEntry> getPasswordEntries() {
        return this.passwordEntries;
    }

    public int getNrOfPasswords() {
        return passwordEntries.size();
    }


    public void delete() {
        // if passwordEntries is not null...
        for (PasswordEntry passwordEntry : passwordEntries) {        // removes this category's all current passwordEntries from the category, then removes this category from  the user's list of categories
      //    this.handler.removePasswordEntryFromCategory(this, passwordEntry);
        }
        // else
      //this.handler.deleteCategoryEntry(this);
    }

}
