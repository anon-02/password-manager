package com.example.passwordmanager.Model;

public class WifiEntry extends Entry {
    public WifiEntry(String name, String identifier, int minPasswordLength, int maxPasswordLength) {
        super("WIFI", name, identifier, "ALLSIGNS", minPasswordLength, maxPasswordLength);  //could set default values for passwordLength
    }
}
