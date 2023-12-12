package com.example.passwordmanager;

import javafx.scene.image.Image;

import java.io.IOException;

public abstract class PasswordEntry implements DisplayableEntry {    // add common code from concrete password Entries
    private CategoryEntry category;

    public PasswordEntry() {}

    public void addToCategory(CategoryEntry category) {
        this.category = category;
    }

    public void removeFromItsCategory() {
        this.category = null;
    }

    public boolean isInCategory() {
        return category != null;
    }

    public CategoryEntry getCategory() {
        return this.category;
    }

}
