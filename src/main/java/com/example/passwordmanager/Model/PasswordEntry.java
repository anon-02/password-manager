package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Date;

/** Description of class **/

public abstract class PasswordEntry<T extends DisplayableEntry> implements DisplayableEntry {
    private User<T> user;

    private CategoryEntry<T> category;
    private boolean isInCategory = false;

    private String name;
    private String identifier;

    private Password password = new Password();
    private String note;
    private Image image;

    public PasswordEntry(String name, String identifier, String password, String note) {
        this.name = name;
        this.identifier = identifier;
        this.password.setPassword(password);
        this.note = note;
    }

    public void addToCategory(CategoryEntry<T> category) {
        this.category = category;
        //this.user.movePasswordEntryToCategory(category, this);
    }

    public void removeFromItsCategory() {
        //this.user.removePasswordEntryFromCategory(this.category, this);
        this.category = null;
    }

    public boolean isInCategory() {
        return category != null;
    }

    public void setCategoryTrue() {
        this.isInCategory = true;
    }

    public void setCategoryFalse() {
        this.isInCategory = false;
    }

    public CategoryEntry<T> getCategory() {
        return this.category;
    }

    public void addUser(User user) {    // remove later, or if this method proves useful, use defensive copying
        this.user = user;
    }

    @Override
    public void delete() {
        if (this.category != null) {
            this.category.removePasswordEntry(this);
        }
        else {
            this.user.deletePasswordEntry(this);
        }
    };


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


    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getNote() {
        return note;
    }


    public void setPassword(String password) {
        this.password.setPassword(password);
    }

    public void generatePassword(int length, boolean upper, boolean numbers, boolean special) {
        // have to include new passwordMaker and remove some old ones, to include upper case lower case mix
        if (numbers && special) {
            this.password.setPasswordMaker(PasswordFactory.makeAllSignsPassword());
        }
        else if (numbers) {
            this.password.setPasswordMaker(PasswordFactory.makeNumbersPassword());
        }
        else {
            this.password.setPasswordMaker(PasswordFactory.makeLetterNumbersPassword());
        }
        this.password.generatePassword(length);
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

    public Image getImage() throws IOException {
        return this.image;
    }
}