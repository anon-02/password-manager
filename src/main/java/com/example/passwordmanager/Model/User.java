package com.example.passwordmanager.Model;

import java.sql.Connection;
import java.util.List;
/** Description of class **/

public class User<T extends Entry> {
    private int id;
    private String username;
    private String masterPassword;


    private static Connection connection;
   // private UserDAO dbHandler = new UserDAO(connection);
    //  private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Felix/IdeaProjects/password-manager/Database/users.db";


    public User(int id, String username, String masterPassword) {
        this.id = id;
        this.username = username;
        this.masterPassword = masterPassword;
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



    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", masterPassword='" + masterPassword + '\'' +
                '}';
    }

}
