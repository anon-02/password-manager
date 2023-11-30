package com.example.passwordmanager;

import com.example.passwordmanager.DisplayableEntry;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class AccountEntry implements DisplayableEntry {

    private String name, username, password, note;
    private Image image;


    public AccountEntry(String name, String username, String password, String note) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.note = note;
        //this.modified = timeCreated; TODO implement timeModified

        System.out.println("Entry added: " + this.name + this.username + this.password);
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {return this.username;}

    public String getUnderName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNote() {
        return this.note;
    }

    public Image getImage() throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/user.png")).openStream());
    }

    public void setName(String newString) {
        this.name = newString;
    }

    public void setUsername(String newString) {
        this.username = newString;
    }

    public void setPassword(String newString) {
        this.password = newString;
    }

    public void setNote(String newString) {
        this.note = newString;
    }

    @Override
    public String toString() {
        return "AccountEntry{" +
                "encryptedName='" + name + '\'' +
                ", encryptedUsername='" + username + '\'' +
                ", encryptedPassword='" + password + '\'' +
                ", encryptedNote='" + note + '\'' +
                '}';
    }

}
