package com.example.passwordmanager.Model;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
/** Description of class **/

public class User<T extends DisplayableEntry> {
    private final int id;
    private String username;
    private String masterPassword;
    private String salt;
    private byte[] iv;

    private List<DisplayableEntry> allEntriesList; // could change to priority queue, prioritized by frequency. Also might not be needed
    private List<PasswordEntry<T>> passwordEntries;
    private List<CategoryEntry<T>> categoryEntries;

    private static Connection connection;
    // private UserDAO dbHandler = new UserDAO(connection);
    //  private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Felix/IdeaProjects/password-manager/Database/users.db";


    public User(int id, String username, String masterPassword) {
        this.id = id;
        this.username = username;
        this.masterPassword = masterPassword;

        //this.allEntriesList = new LinkedList<>();
        this.passwordEntries = new LinkedList<>();
        this.categoryEntries = new LinkedList<>();
    }

    public User(int id, String username, String masterPassword, String salt, byte[] iv) {
        this.id = id;
        this.username = username;
        this.masterPassword = masterPassword;
        this.salt = salt;
        this.iv = iv;
    }

    public void addPasswordEntry(PasswordEntry<T> passwordEntry) {
        this.passwordEntries.add(passwordEntry);
        //this.allEntriesList.add(passwordEntry);
    }

    public void addCategoryEntry(CategoryEntry<T> categoryEntry) {
        this.categoryEntries.add(categoryEntry);
        //this.allEntriesList.add(categoryEntry);
    }

    public void movePasswordEntryToCategory(CategoryEntry<T> category, PasswordEntry<T> passwordEntry) { // adds a passwordEntry to a given category
        if (passwordEntry.isInCategory()) {
            passwordEntry.getCategory().removePasswordEntry(passwordEntry);
        }
        else {
            deletePasswordEntry(passwordEntry);
        }
        category.addPasswordEntry(passwordEntry);   // exceptions
        //this.allEntriesList.remove(passwordEntry);
    }

    public void removePasswordEntryFromCategory(CategoryEntry<T> category, PasswordEntry<T> passwordEntry) { // adds a passwordEntry to a given category
        category.removePasswordEntry(passwordEntry);   // exceptions
        this.passwordEntries.add(passwordEntry);
        //this.allEntriesList.add(passwordEntry);     // will be able to remove this perhaps
    }

    public void deleteCategoryEntry(CategoryEntry<T> categoryEntry) {
        this.categoryEntries.remove(categoryEntry);
        //this.allEntriesList.remove(categoryEntry);
    }

    public void deletePasswordEntry(PasswordEntry<T> passwordEntry) {
        // raise exception if entry not in list, or do something else to let client code know
        this.passwordEntries.remove(passwordEntry);
        //this.allEntriesList.remove(passwordEntry);
    }

    public List<DisplayableEntry> getAllEntries() {
        List<DisplayableEntry> showList = new LinkedList();
        for (CategoryEntry<T> categoryEntry : categoryEntries) {
            showList.add(categoryEntry);
            if (categoryEntry.isOpen())
            {
                for (PasswordEntry passwordEntry: categoryEntry.getPasswordEntries()) {
                    showList.add(passwordEntry);
                }
            }
        }
        for (PasswordEntry<T> passwordEntry : passwordEntries) {
            showList.add(passwordEntry);
        }
        return showList;
    }
    // return the list with categories first, then the individual passwordEntries by frequency

    public List<CategoryEntry<T>> getCategories() {
        return categoryEntries;
    }



    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public String getSalt() { return salt; }
    public byte[] getIV() {return iv;}


    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", masterPassword='" + masterPassword + '\'' +
                '}';
    }

}