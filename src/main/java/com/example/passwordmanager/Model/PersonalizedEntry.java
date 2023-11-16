package com.example.passwordmanager.Model;
/** Description of class **/

public class PersonalizedEntry extends Entry {

    public PersonalizedEntry(String name, String identifier, String passwordType, int minPasswordLength, int maxPasswordLength) {
        super("PERSONALIZED", name, identifier, passwordType, minPasswordLength, maxPasswordLength);
        if (!(passwordType.equals("ALLSIGNS"))) {
            changePasswordType(passwordType);
        }
    }
}
