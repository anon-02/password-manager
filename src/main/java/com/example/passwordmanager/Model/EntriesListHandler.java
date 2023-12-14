package com.example.passwordmanager.Model;

import com.example.passwordmanager.Model.Entries.CategoryEntry;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.Model.Entries.PasswordEntry;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntriesListHandler {

    private static EntriesListHandler instance = null;

    private List<PasswordEntry> allPasswordEntries;         // list of all current existing passwordEntries
    private List<PasswordEntry> passwordEntries;            // list of category-less passwordEntries  (could make into priority queue, prioritized by frequency)
    private List<CategoryEntry> categoryEntries;            // list of categories
    private List<PasswordEntry> addedEntries;

    protected EntriesListHandler() {
        System.out.println("entrieshandler");
        allPasswordEntries = new LinkedList(EncryptionBuffer.retrieveEntries());
        passwordEntries = new LinkedList<>();
        categoryEntries = new LinkedList<>();
        addedEntries = new LinkedList<>();
        initialize();
    }

    // Singleton, makes sure the application shares the same EntriesListHandler
    public static EntriesListHandler getInstance() {
        if (instance == null) {
            instance = new EntriesListHandler();
        }
        return instance;
    }

    private void initialize() {
        Set<CategoryEntry> categories = new HashSet<>();
        allPasswordEntries.clear();
        passwordEntries.clear();
        categoryEntries.clear();
        for (DisplayableEntry entry: EncryptionBuffer.retrieveEntries()) {
            allPasswordEntries.add((PasswordEntry) entry);
        }
        for (PasswordEntry passwordEntry : allPasswordEntries) {
            if (passwordEntry.isInCategory()) {
                CategoryEntry category = passwordEntry.getCategory();
                category.addPasswordEntry(passwordEntry);
                categories.add(category);
            }
            else {
                passwordEntries.add(passwordEntry);
            }
        }
        if (!categories.isEmpty()) {
            categoryEntries.addAll(categories);
        }
    }

    public void addPasswordEntry(PasswordEntry passwordEntry) {
        passwordEntries.add(passwordEntry);
        allPasswordEntries.add(passwordEntry);
        addedEntries.add(passwordEntry);
        saveAllEntries();
    }

    public void addCategoryEntry(CategoryEntry categoryEntry) {
        categoryEntries.add(categoryEntry);
    }

    // adds a passwordEntry to a given category
    public void movePasswordEntryToCategory(CategoryEntry category, PasswordEntry passwordEntry) {
        if (passwordEntry.isInCategory()) {
            passwordEntry.getCategory().removePasswordEntry(passwordEntry);     // exceptions
        }
        else {
            deletePasswordEntry(passwordEntry);
        }
        category.addPasswordEntry(passwordEntry);
    }

    // adds a passwordEntry to a given category
    public void removePasswordEntryFromCategory(CategoryEntry category, PasswordEntry passwordEntry) {
        category.removePasswordEntry(passwordEntry);   // exceptions
        passwordEntries.add(passwordEntry);
    }

    // deletes a passwordEntry
    public void deletePasswordEntry(PasswordEntry passwordEntry) {
        // raise exception if entry not in list, or do something else to let client code know
        if (passwordEntry.isInCategory()) {
            removePasswordEntryFromCategory(passwordEntry.getCategory(), passwordEntry);
        }
        passwordEntries.remove(passwordEntry);
    }

    // removes this category's all current passwordEntries and adds them to the list of individual passwordEntries, then deletes this category
    public void deleteCategoryEntry(CategoryEntry categoryEntry) {
        // exceptions..
        List<PasswordEntry> currentPasswordEntries = new LinkedList<>(categoryEntry.getPasswordEntries());
        System.out.println(currentPasswordEntries); //remove
        if (!currentPasswordEntries.isEmpty()) {
            for (PasswordEntry passwordEntry : currentPasswordEntries) {
                removePasswordEntryFromCategory(categoryEntry, passwordEntry);
            }
        }
        categoryEntries.remove(categoryEntry);
    }

    public List<CategoryEntry> getCategories() {
        return new LinkedList<>(categoryEntries);
    }

    public List<PasswordEntry> getAllPasswordEntries() {
        return new LinkedList<>(allPasswordEntries);
    }

    public List<DisplayableEntry> displayEntries() {
        initialize();
        List<DisplayableEntry> showList = new LinkedList<>();
        for (CategoryEntry categoryEntry : categoryEntries) {
            showList.add(categoryEntry);
            if (categoryEntry.isOpen()) {
                showList.addAll(categoryEntry.getPasswordEntries());
            }
        }
        showList.addAll(passwordEntries);
        return showList;
    }

    public void saveAllEntries() {
        List<DisplayableEntry> newEntries = new LinkedList<>(addedEntries);
        EncryptionBuffer.insertAllEntries(newEntries);
        addedEntries.clear();
    }

    /*public static void main(String[] args) {
        CategoryEntry c = new CategoryEntry("category");
        PasswordEntry p1 = new AccountEntry("1", "two", "h", "j");
        System.out.println(p1.isInCategory());
        p1.addToCategory(c);
        System.out.println(p1.isInCategory());
        PasswordEntry p2 = new AccountEntry("2", "two", "h", "j");
        p2.addToCategory(c);
        PasswordEntry p3 = new AccountEntry("3", "two", "h", "j");
        p3.addToCategory(c);

        List list = new LinkedList();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        EntriesListHandler handler = new EntriesListHandler(list);
        System.out.println(handler.displayEntries());
        System.out.println(handler.getCategories());
        handler.deleteCategoryEntry(c);
        System.out.println(handler.getCategories());
        System.out.println(handler.displayEntries());
    }*/
}

