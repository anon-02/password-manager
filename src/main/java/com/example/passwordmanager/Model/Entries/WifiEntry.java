package com.example.passwordmanager.Model.Entries;

import com.example.passwordmanager.fxmlHelper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class WifiEntry extends PasswordEntry {
    private String name, wifiName, wifiPassword, wifiURL, wifiAdminPassword, note;
    int entryId;
    int type = 3;
    private fxmlHelper helper = fxmlHelper.getInstance();

    public WifiEntry(int entryId, String name, String wifiName, String wifiPassword, String wifiURL, String wifiAdminPassword, String note) {
        this.entryId = entryId;
        this.name = name;
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
        this.wifiURL = wifiURL;
        this.wifiAdminPassword = wifiAdminPassword;
        this.note = note;

        System.out.println("Entry added: " + this.name + " ID: "+ this.entryId);

        //this.modified = getTimeCreated(); TODO implement timeModified
    }

    public String getName() {return this.name;}
    public String getWifiName() {return this.wifiName;}
    public String getWifiPassword() {return this.wifiPassword;}
    public String getWifiURL() {return this.wifiURL;}
    public String getWifiAdminPassword() {return this.wifiAdminPassword;}
    public String getNote() {return this.note;}
    public int getType() {return type;}

    @Override
    public int getEntryId() {
        return entryId;
    }

    @Override
    public String getUnderName() {
        return getWifiName();
    }

    public void setName(String s) {this.name = s;}
    public void setWifiName(String s) {this.wifiName = s;}
    public void setWifiPassword(String s) {this.wifiPassword = s;}
    public void setWifiURL(String s) {this.wifiURL = s;}
    public void setWifiAdminPassword(String s) {this.wifiAdminPassword = s;}
    public void setNote(String s) {this.note = s;}

    @Override
    public Image getImage() throws IOException {
        return helper.getImage("wifi.png");
    }

    public String toString() {
        return "WifiEntry{" +
                "ID='" + entryId + '\'' +
                "encryptedName='" + name + '\'' +
                ", encryptedWifiName='" + wifiName + '\'' +
                ", encryptedWifiPass='" + wifiPassword + '\'' +
                ", encryptedURL='" + wifiURL + '\'' +
                ", encryptedAdminPass='" + wifiAdminPassword + '\'' +
                ", encryptedNote='" + note + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WifiEntry wifiEntry = (WifiEntry) o;
        return entryId == wifiEntry.entryId && type == wifiEntry.type && Objects.equals(name, wifiEntry.name) && Objects.equals(wifiName, wifiEntry.wifiName) && Objects.equals(wifiPassword, wifiEntry.wifiPassword) && Objects.equals(wifiURL, wifiEntry.wifiURL) && Objects.equals(wifiAdminPassword, wifiEntry.wifiAdminPassword) && Objects.equals(note, wifiEntry.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, wifiName, wifiPassword, wifiURL, wifiAdminPassword, note, entryId, type, helper);
    }

    @Override
    public String getSearchTerm() {
        String searchTerm = "";
        String category;
        searchTerm += getName() + "Wifi";
        try {
            category = getCategory().getName();
        } catch (NullPointerException e) {
            category = "";
        }
        return (searchTerm + category).toLowerCase().replace(" ", "");
    }

}
