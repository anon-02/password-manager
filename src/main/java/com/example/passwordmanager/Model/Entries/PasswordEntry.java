package com.example.passwordmanager.Model.Entries;

/** public interface that allows for passwordEntries to be categorized **/
public abstract class PasswordEntry implements DisplayableEntry {    // add common code from concrete password Entries
    private CategoryEntry category;

    public PasswordEntry() {}

    // adds this passwordEntry to the specified category
    public void addToCategory(CategoryEntry category) {
        this.category = category;
    }

    // removes this passwordEntry from the specified category
    public void removeFromItsCategory() {
        this.category = null;
    }

    // returns whether this passwordEntry belongs to a category
    public boolean isInCategory() {
        return category != null;
    }

    // returns the category this passwordEntry currently belongs to
    public CategoryEntry getCategory() {
        return this.category;
    }

}
