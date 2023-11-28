package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.Model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class SessionManager {

    //private static final SessionManager instance = new SessionManager();
    private static SecretKey activeSecretKey;
    private static IvParameterSpec activeIV;

    private static ObjectProperty<User> currentUserProperty = new SimpleObjectProperty<>();

    private static User currentUser;

    public static void setCurrentUser(User user) {currentUserProperty.set(user);}

    public  static User getCurrentUser() {return currentUserProperty.get();}

    public static ObjectProperty<User> getCurrentUserProperty() {
        return currentUserProperty;
    }

    public static void invalidateSession() {
        currentUser = null;
    }

    public static void setActiveSecretKey(SecretKey key) { activeSecretKey = key; }
    public static SecretKey getActiveSecretKey() {return activeSecretKey; }

    public static void setActiveIV(IvParameterSpec iv) { activeIV = iv; }
    public static IvParameterSpec getActiveIV() {return activeIV;}


    // create and store key

}
