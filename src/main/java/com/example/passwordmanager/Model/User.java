package com.example.passwordmanager.Model;

import com.example.passwordmanager.Model.Entries.DisplayableEntry;

import java.sql.Connection;

/** Description of class **/

public class User<T extends DisplayableEntry> {
    private int id;
    private String username;
    private String masterPassword;
    private String salt;
    private byte[] iv;


    public User(int id, String username, String masterPassword, String salt, byte[] iv) {
        this.id = id;
        this.username = username;
        this.masterPassword = masterPassword;
        this.salt = salt;
        this.iv = iv;
    }
/*
    public void addEntry(T entry) {
        entries.add(entry);
    }

    public void removeEntry(T entry) {
        // raise exception if entry not in list, or do something else to let client code know
        entries.remove(entry);
    }

    public String getUsername() {
        return username;
    }
*/

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
                ", salt=" + salt + "'\'" +
                '}';
    }

}
