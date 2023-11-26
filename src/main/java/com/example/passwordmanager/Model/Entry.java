package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Date;

/** Description of class **/

public abstract class Entry {
    private String category;
    private String name;
    private String identifier;
    private String passwordType;
    private int passwordLength;
    private int timesInspected = 0;  // amount of times a user has clicked on this entry, helps us sort entries by how frequently a user inspects them
    private Password password;
    private String note;
    private Image image;

    public Entry(String category, String name, String identifier, String passwordType, int passwordLength) {
        this.category = category;
        this.name = name;
        this.identifier = identifier;
        this.passwordType = passwordType;
        this.password = new Password(new AllSignsPassword());
    }

    // if you want to create entry object before user has typed in input, and use setters to insert data
    public Entry() {};

    public Date getPasswordLastModifiedDate() {
        return this.password.getDateLastModified();
    }

    public void changePasswordType(PasswordMaker passwordMaker) {
        this.password.setPasswordMaker(passwordMaker);
     }


    public void setPasswordLength(int length) {
        this.passwordLength = length;
        this.password.setLength(length);
    }

    public int getTimesInspected() {
        return this.timesInspected;
    }

    public void increaseTimesInspected() {
        this.timesInspected ++;
    }

    public void setPassword(String password) {
        this.password.setPassword(password);
    }

    public String getPassword() {
        return this.password.getPassword();
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setImage (Image image) throws IOException {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }
}

