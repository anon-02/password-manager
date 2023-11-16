package com.example.passwordmanager.Model;

import java.util.List;
/** Description of class **/

public class User<T extends Entry> {
    private String username;
    private Password masterPassword;
    private List<T> entries;


    public User(String username, Password masterPassword, List<T> entries) {
        this.username = username;
        this.masterPassword = masterPassword;
        this.entries = entries;
    }

    public void addEntry(T entry) {
        entries.add(entry);
    }

    public void removeEntry(T entry) {
        // raise exception if entry not in list, or do something else to let client code know
        entries.remove(entry);
    }

}
