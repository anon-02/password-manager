package com.example.passwordmanager;

import com.example.passwordmanager.Model.PasswordEntry;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class WifiEntry extends PasswordEntry {
    private String name, wifiName, wifiPassword, wifiURL, wifiAdminPassword, note;


    public WifiEntry(String name, String wifiName, String wifiPassword, String wifiURL, String wifiAdminPassword, String note) {
        super(name, wifiName, wifiPassword, note);
        this.name = name;
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
        this.wifiURL = wifiURL;
        this.wifiAdminPassword = wifiAdminPassword;
        this.note = note;
        //this.modified = getTimeCreated(); TODO implement timeModified
    }

    public String getName() {return this.name;}
    public String getWifiName() {return this.wifiName;}
    public String getWifiPassword() {return this.wifiPassword;}
    public String getWifiURL() {return this.wifiURL;}
    public String getWifiAdminPassword() {return this.wifiAdminPassword;}
    public String getNote() {return this.note;}

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
        return new Image(Objects.requireNonNull(getClass().getResource("Images/wifi.png")).openStream());
    }
}