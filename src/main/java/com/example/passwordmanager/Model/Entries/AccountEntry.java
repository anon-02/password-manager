package com.example.passwordmanager.Model.Entries;

import com.example.passwordmanager.fxmlHelper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class AccountEntry extends PasswordEntry implements PasswordType {

    private String name, username, password, note;
    int type = 1;
    int entryId;
    private Image image;
    private fxmlHelper helper = fxmlHelper.getInstance();


    public AccountEntry(int entryId, String name, String username, String password, String note) {
        this.entryId = entryId;
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

    public int getType() { return type;}
    public int getEntryId() {return entryId;}
    public void setEntryId(int entryId) {this.entryId = entryId;};

    public Image getImage() throws IOException {
        return helper.getImage("user.png");
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
                "ID='" + entryId + '\'' +
                "encryptedName='" + name + '\'' +
                ", encryptedUsername='" + username + '\'' +
                ", encryptedPassword='" + password + '\'' +
                ", encryptedNote='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntry that = (AccountEntry) o;
        return type == that.type && entryId == that.entryId && Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(note, that.note) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, password, note, type, entryId, image, helper);
    }

    @Override
    public String getSearchTerm() {
        String searchTerm = "";
        String category;
        searchTerm += getName() + "Account";
        try {
            category = getCategory().getName();
        } catch (NullPointerException e) {
            category = "";
        }
        return (searchTerm + category).toLowerCase().replace(" ", "");
    }
}
