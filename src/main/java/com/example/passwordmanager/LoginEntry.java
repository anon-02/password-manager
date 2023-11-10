package com.example.passwordmanager;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class LoginEntry implements Entry{

    private String name, username, password, note;
    private Image image;


    public LoginEntry(String name, String username, String password, String note) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.note = note;
        //this.modified = timeCreated; TODO implement timeModified
    }

    public String getName() {
        return this.name;
    }

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
        return new Image(Objects.requireNonNull(getClass().getResource("Images/door-key.png")).openStream());
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
}
