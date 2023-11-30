package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Entries.AccountEntry;
import com.example.passwordmanager.Entries.CardEntry;
import com.example.passwordmanager.Entries.SecureNoteEntry;
import com.example.passwordmanager.Entries.WifiEntry;
import com.example.passwordmanager.Model.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EncryptionBuffer {

    User currentUser = SessionManager.getCurrentUser();
    int userID = currentUser.getID();

    static IvParameterSpec iv = SessionManager.getActiveIV();
    static SecretKey key = SessionManager.getActiveSecretKey();
    static String algorithm = "AES/CBC/PKCS5Padding";

    public static List<DisplayableEntry> retrieveEntries() throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        EntryDAOImpl entryDAO = new EntryDAOImpl();

        // get the list
        List<DisplayableEntry> encryptedList = entryDAO.getAll();
        List<DisplayableEntry> decryptedList = new ArrayList<>();

        // decrypt it
        //
        for (DisplayableEntry entry : encryptedList) {
            if (entry instanceof AccountEntry) {
                decryptedList.add(decryptAccountEntry((AccountEntry) entry));
            } else if (entry instanceof WifiEntry) {
                decryptedList.add(decryptWifiEntry((WifiEntry) entry));
            } else {
                decryptedList.add(decryptNoteEntry((SecureNoteEntry) entry));
            }

            System.out.println(entry);
            System.out.println("This is the decrypted list");
            System.out.println(decryptedList);
        }
        // return a complete list
        return decryptedList;
    }

    public static AccountEntry decryptAccountEntry(AccountEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedUsername = EncryptionLogic.decrypt(algorithm, entry.getUsername(), key, iv);
        String decryptedPassword = EncryptionLogic.decrypt(algorithm, entry.getPassword(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNote(), key, iv);

        AccountEntry decodedEntry = new AccountEntry(decryptedName, decryptedUsername, decryptedPassword, decryptedNote);

        return decodedEntry;
    }

    public static WifiEntry decryptWifiEntry(WifiEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedWifiName = EncryptionLogic.decrypt(algorithm, entry.getWifiName(), key, iv);
        String decryptedWifiPassword = EncryptionLogic.decrypt(algorithm, entry.getWifiPassword(), key, iv);
        String decryptedConfigURL = EncryptionLogic.decrypt(algorithm, entry.getWifiURL(), key, iv);
        String decryptedAdminPassword = EncryptionLogic.decrypt(algorithm, entry.getWifiURL(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNote(), key, iv);

        WifiEntry decodedEntry = new WifiEntry(decryptedName, decryptedWifiName, decryptedWifiPassword, decryptedConfigURL, decryptedAdminPassword, decryptedNote);

        return decodedEntry;
    }

    public static SecureNoteEntry decryptNoteEntry(SecureNoteEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedSubject = EncryptionLogic.decrypt(algorithm, entry.getNoteSubject(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNoteContent(), key, iv);

        SecureNoteEntry decodedEntry = new SecureNoteEntry(decryptedName, decryptedSubject, decryptedNote);

        return decodedEntry;
    }

    public static void insertAccountEntry(AccountEntry entry) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // get entry
        int type = 1;  // deciding arbitrarily that accountEntries are 1

        // encrypt each field on their own
        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedUsername = EncryptionLogic.encrypt(algorithm, entry.getUsername(), key, iv);
        String encryptedPassword = EncryptionLogic.encrypt(algorithm, entry.getPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        // make a new entry of encoded
        AccountEntry encodedEntry = new AccountEntry(encryptedName, encryptedUsername, encryptedPassword, encryptedNote);

        // insert it
        EntryDAOImpl.insertAccountEntry(encodedEntry, type);
        //TODO used here for now
        retrieveEntries();
    }

    public static void insertCardEntry(CardEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = 2;

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedCardHolder = EncryptionLogic.encrypt(algorithm, entry.getCardHolder(), key, iv);
        String encryptedCardNumber = EncryptionLogic.encrypt(algorithm, entry.getCardNumber(), key, iv);
        String encryptedExpireMonth = EncryptionLogic.encrypt(algorithm, entry.getExpireMonth(), key, iv);
        String encryptedExpireYear = EncryptionLogic.encrypt(algorithm, entry.getExpireYear(), key, iv);
        String encryptedCVC = EncryptionLogic.encrypt(algorithm, entry.getCvcCode(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        CardEntry encodedEntry = new CardEntry(encryptedName, encryptedCardHolder, encryptedCardNumber, encryptedExpireMonth, encryptedExpireYear, encryptedCVC, encryptedNote);

        EntryDAOImpl.insertCardEntry(encodedEntry, type);
    }

    public static void insertWifiEntry(WifiEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = 3;

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedWifiName = EncryptionLogic.encrypt(algorithm, entry.getWifiName(), key, iv);
        String encryptedWifiPassword = EncryptionLogic.encrypt(algorithm, entry.getWifiPassword(), key, iv);
        String encryptedWifiURL = EncryptionLogic.encrypt(algorithm, entry.getWifiURL(), key, iv);
        String encryptedWifiAdminPass = EncryptionLogic.encrypt(algorithm, entry.getWifiAdminPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        WifiEntry encodedEntry = new WifiEntry(encryptedName, encryptedWifiName, encryptedWifiPassword, encryptedWifiURL, encryptedWifiAdminPass, encryptedNote);

        EntryDAOImpl.insertWifiEntry(encodedEntry, type);
    }

    public static void insertNoteEntry(SecureNoteEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = 4;

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedSubject = EncryptionLogic.encrypt(algorithm, entry.getNoteSubject(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNoteContent(), key, iv);

        SecureNoteEntry encodedEntry = new SecureNoteEntry(encryptedName, encryptedSubject, encryptedNote);

        EntryDAOImpl.insertSecureNoteEntry(encodedEntry, type);
    }

}
