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
    private int minPasswordLength;
    private int maxPasswordLength;
    private Password password;
    private String note;
    private Image image;

    public Entry(String category, String name, String identifier, String passwordType, int minPasswordLength, int maxPasswordLength) {
        this.category = category;
        this.name = name;
        this.identifier = identifier;
        this.passwordType = passwordType;
        this.minPasswordLength = minPasswordLength;
        this.maxPasswordLength = maxPasswordLength;
        this.password = new Password(new AllSignsPassword(), minPasswordLength, maxPasswordLength);
    }

    // if you want to create entry object before user has typed in input, and use setters to insert data
    public Entry() {};

    public Date getPasswordLastModifiedDate() {
        return this.password.getDateLastModified();
    }

    public void changePasswordType(String passwordType) {
        // work with Enum and raise exceptions if passwordType is not one of the enum strings
        if (passwordType.equals("NUMBERS")) {
            this.password.setPasswordMaker(PasswordFactory.makeNumbersPassword());
        }
        else if (passwordType.equals("LETTERS")) {
            this.password.setPasswordMaker(PasswordFactory.makeLettersPassword());
        }
        //... add for other types of password
    }

    public void setMinPasswordLength(int length) {
        this.minPasswordLength = length;
        this.password.setMinLength(length);
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public int getMaxPasswordLength() {
        return maxPasswordLength;
    }

    public String getNote() {
        return note;
    }

    public void setMaxPasswordLength(int length) {
        this.maxPasswordLength = length;
        this.password.setMaxLength(length);
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

