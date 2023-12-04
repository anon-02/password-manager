package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CategoryEntry <T extends DisplayableEntry> implements DisplayableEntry {
    private String categoryName;

    private User<T> user;
    private List<PasswordEntry<T>> passwordEntries;

    private boolean isOpen;

    public CategoryEntry(String name) {
        this.categoryName = name;
        this.passwordEntries = new LinkedList<>();
        this.isOpen = false;
    }

    public void addPasswordEntry(PasswordEntry<T> passwordEntry) {
        this.passwordEntries.add(passwordEntry);
        passwordEntry.addToCategory(this);
    }

    public void removePasswordEntry(PasswordEntry<T> passwordEntry) {
        this.passwordEntries.remove(passwordEntry);
        passwordEntry.removeFromItsCategory();
    }

    public void addUser(User user) {
        this.user = user;   // remove later, or if this method proves useful, use defensive copying
    }

    @Override
    public void delete() {

        // if passwordEntries is not null...
        for (PasswordEntry<T> passwordEntry : passwordEntries) {        // removes this category's all current passwordEntries from the category, then removes this category from  the user's list of categories
            this.user.removePasswordEntryFromCategory(this, passwordEntry);
        }
        // else
        this.user.deleteCategoryEntry(this);
    }

    public List<PasswordEntry<T>> getPasswordEntries() {
        return this.passwordEntries;
    }

    public int getNrOfPasswords() {
        return passwordEntries.size();
    }

    public void setAsOpen() {
        this.isOpen = true;
    }

    public void setAsClose() {
        this.isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public String getUnderName() {
        return null;
    }

    @Override
    public Image getImage() throws IOException {
        return null;
    }
}