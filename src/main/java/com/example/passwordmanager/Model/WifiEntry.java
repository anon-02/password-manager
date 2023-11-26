package com.example.passwordmanager.Model;

public class WifiEntry extends Entry {
    public WifiEntry(String name, String identifier, int passwordLength) {
        super("WIFI", name, identifier, "ALLSIGNS", passwordLength);  //could set default values for passwordLength
    }
}
