package com.example.passwordmanager;

import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntriesListHandler {
    private List<PasswordEntry> allPasswordEntries;         // list of all current existing passwordEntries
    private List<PasswordEntry> passwordEntries;            // list of category-less passwordEntries  (could make into priority queue, prioritized by frequency)
    private List<CategoryEntry> categoryEntries;            // list of categories
    private List<PasswordEntry> addedEntries;


    public EntriesListHandler(List<DisplayableEntry> allEntries) {
        allPasswordEntries = new LinkedList(allEntries);
        passwordEntries = new LinkedList<>();
        categoryEntries = new LinkedList<>();
        addedEntries = new LinkedList<>();
        initialize();
    }

    private void initialize() {
        Set<CategoryEntry> categories = new HashSet<>();
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
        return categoryEntries;
    }

    public List<PasswordEntry> getAllPasswordEntries() {
        return allPasswordEntries;
    }

    public List<DisplayableEntry> displayEntries() {
        List showList = new LinkedList<>();
        for (CategoryEntry categoryEntry : categoryEntries) {
            showList.add(categoryEntry);
            if (categoryEntry.isOpen())
            {
                for (PasswordEntry passwordEntry: categoryEntry.getPasswordEntries()) {
                    showList.add(passwordEntry);
                }
            }
        }
        for (PasswordEntry passwordEntry : passwordEntries) {
            showList.add(passwordEntry);
        }
        return showList;
    }

    public void saveAllEntries() throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        List<DisplayableEntry> newEntries = new LinkedList<>(addedEntries);
        EncryptionBuffer.inserAllEntries(newEntries);
    }

    public static void main(String[] args) {
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
    }
}

