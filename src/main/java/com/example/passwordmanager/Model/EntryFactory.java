package com.example.passwordmanager.Model;

import java.io.IOException;

public class EntryFactory {

    public static CreditCardEntry makeCreditCardEntry(String name, String identifier, String cardName, String date, String cvv) throws IOException {
        return new CreditCardEntry(name, identifier, cardName, date, cvv);
    }

    public static WebsiteEntry makeWebsiteEntry(String name, String identifier, int passwordLength, String URL) throws IOException {
        return new WebsiteEntry(name, identifier, passwordLength, URL);
    }

    public static WifiEntry makeWifiEntry(String name, String identifier, int passwordLength) {
        return new WifiEntry(name, identifier, passwordLength);
    }

    public static PersonalizedEntry PersonalizedEntry(String name, String identifier, String passwordType, int passwordLength) {
        return new PersonalizedEntry(name, identifier, passwordType, passwordLength);
    }
}