package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.*;
import com.example.passwordmanager.AccountEntry;
import com.example.passwordmanager.CardEntry;
import com.example.passwordmanager.SecureNoteEntry;
import com.example.passwordmanager.WifiEntry;
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
        System.out.println(" ");
        System.out.println("-This is the decrypted list: ");
        for (DisplayableEntry entry : encryptedList) {
            if (entry instanceof AccountEntry) {
                decryptedList.add(decryptAccountEntry((AccountEntry) entry));
            } else if (entry instanceof CardEntry) {
                decryptedList.add(decryptCardEntry((CardEntry) entry));
            } else if (entry instanceof WifiEntry) {
                decryptedList.add(decryptWifiEntry((WifiEntry) entry));
            } else {
                decryptedList.add(decryptNoteEntry((SecureNoteEntry) entry));
            }

            System.out.println(entry);
        }
        // return a complete list
        System.out.println(" ");
        System.out.println("-- complete decrypted list --");
        return decryptedList;
    }

    public static AccountEntry decryptAccountEntry(AccountEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int ID = entry.getEntryId();
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedUsername = EncryptionLogic.decrypt(algorithm, entry.getUsername(), key, iv);
        String decryptedPassword = EncryptionLogic.decrypt(algorithm, entry.getPassword(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNote(), key, iv);


        AccountEntry decodedEntry = new AccountEntry(ID, decryptedName, decryptedUsername, decryptedPassword, decryptedNote);

        return decodedEntry;
    }

    public static CardEntry decryptCardEntry(CardEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int ID = entry.getEntryId();
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedCardHolder = EncryptionLogic.decrypt(algorithm, entry.getCardHolder(), key, iv);
        String decryptedCardNumber = EncryptionLogic.decrypt(algorithm, entry.getCardNumber(), key, iv);
        String decryptedExpireMonth = EncryptionLogic.decrypt(algorithm, entry.getExpireMonth(), key, iv);
        String decryptedExpireYear = EncryptionLogic.decrypt(algorithm, entry.getExpireYear(), key, iv);
        String decryptedCvcCode = EncryptionLogic.decrypt(algorithm, entry.getCvcCode(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNote(), key, iv);


        CardEntry decodedEntry = new CardEntry(ID, decryptedName, decryptedCardHolder, decryptedCardNumber, decryptedExpireMonth, decryptedExpireYear, decryptedCvcCode, decryptedNote);

        return decodedEntry;
    }

    public static WifiEntry decryptWifiEntry(WifiEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int ID = entry.getEntryId();
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedWifiName = EncryptionLogic.decrypt(algorithm, entry.getWifiName(), key, iv);
        String decryptedWifiPassword = EncryptionLogic.decrypt(algorithm, entry.getWifiPassword(), key, iv);
        String decryptedConfigURL = EncryptionLogic.decrypt(algorithm, entry.getWifiURL(), key, iv);
        String decryptedAdminPassword = EncryptionLogic.decrypt(algorithm, entry.getWifiURL(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNote(), key, iv);

        WifiEntry decodedEntry = new WifiEntry(ID, decryptedName, decryptedWifiName, decryptedWifiPassword, decryptedConfigURL, decryptedAdminPassword, decryptedNote);

        return decodedEntry;
    }

    public static SecureNoteEntry decryptNoteEntry(SecureNoteEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int ID = entry.getEntryId();
        String decryptedName = EncryptionLogic.decrypt(algorithm, entry.getName(), key, iv);
        String decryptedSubject = EncryptionLogic.decrypt(algorithm, entry.getNoteSubject(), key, iv);
        String decryptedNote = EncryptionLogic.decrypt(algorithm, entry.getNoteContent(), key, iv);

        SecureNoteEntry decodedEntry = new SecureNoteEntry(ID, decryptedName, decryptedSubject, decryptedNote);

        return decodedEntry;
    }

    public static void insertAccountEntry(AccountEntry entry) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // get entry
        int type = entry.getType();  // deciding arbitrarily that accountEntries are 1

        // encrypt each field on their own
        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedUsername = EncryptionLogic.encrypt(algorithm, entry.getUsername(), key, iv);
        String encryptedPassword = EncryptionLogic.encrypt(algorithm, entry.getPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        // make a new entry of encoded
        AccountEntry encodedEntry = new AccountEntry(0, encryptedName, encryptedUsername, encryptedPassword, encryptedNote);

        // insert it
        EntryDAOImpl.insertAccountEntry(encodedEntry, type);
        retrieveEntries();
    }

    public static void insertCardEntry(CardEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = entry.getType();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedCardHolder = EncryptionLogic.encrypt(algorithm, entry.getCardHolder(), key, iv);
        String encryptedCardNumber = EncryptionLogic.encrypt(algorithm, entry.getCardNumber(), key, iv);
        String encryptedExpireMonth = EncryptionLogic.encrypt(algorithm, entry.getExpireMonth(), key, iv);
        String encryptedExpireYear = EncryptionLogic.encrypt(algorithm, entry.getExpireYear(), key, iv);
        String encryptedCVC = EncryptionLogic.encrypt(algorithm, entry.getCvcCode(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        CardEntry encodedEntry = new CardEntry(0, encryptedName, encryptedCardHolder, encryptedCardNumber, encryptedExpireMonth, encryptedExpireYear, encryptedCVC, encryptedNote);

        EntryDAOImpl.insertCardEntry(encodedEntry, type);
    }

    public static void insertWifiEntry(WifiEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = entry.getType();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedWifiName = EncryptionLogic.encrypt(algorithm, entry.getWifiName(), key, iv);
        String encryptedWifiPassword = EncryptionLogic.encrypt(algorithm, entry.getWifiPassword(), key, iv);
        String encryptedWifiURL = EncryptionLogic.encrypt(algorithm, entry.getWifiURL(), key, iv);
        String encryptedWifiAdminPass = EncryptionLogic.encrypt(algorithm, entry.getWifiAdminPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        WifiEntry encodedEntry = new WifiEntry(0, encryptedName, encryptedWifiName, encryptedWifiPassword, encryptedWifiURL, encryptedWifiAdminPass, encryptedNote);

        EntryDAOImpl.insertWifiEntry(encodedEntry, type);
    }

    public static void insertNoteEntry(SecureNoteEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        int type = entry.getType();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedSubject = EncryptionLogic.encrypt(algorithm, entry.getNoteSubject(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNoteContent(), key, iv);

        SecureNoteEntry encodedEntry = new SecureNoteEntry(0, encryptedName, encryptedSubject, encryptedNote);

        EntryDAOImpl.insertSecureNoteEntry(encodedEntry, type);
    }

    public static void deleteEntry(DisplayableEntry entry) throws SQLException {
        int entryType = entry.getType();
        System.out.println("entry type about to be deleted: " + entryType);

        switch (entryType) {
            case 1:
                String AccountDeletetionSql = "DELETE FROM AccountEntry WHERE id = ?";
                EntryDAOImpl.delete(entry, AccountDeletetionSql);
                System.out.println("type 1 accountentry deleted");
                break;
            case 2:
                String CardDeleteSql = "DELETE FROM CardEntry WHERE id = ?";
                EntryDAOImpl.delete(entry, CardDeleteSql);
                System.out.println("Type 2 cardentry deleted");
                break;
            case 3:
                String WifiDeletionSql = "DELETE FROM WifiEntry WHERE id = ?";
                EntryDAOImpl.delete(entry, WifiDeletionSql);
                System.out.println("Type 3 wifientry deleted");
                break;
            case 4:
                String NoteDeletionSql = "DELETE FROM NoteEntry WHERE id = ?";
                EntryDAOImpl.delete(entry, NoteDeletionSql);
                System.out.println("Type 4 noteEntry deleted");
                break;
        }
        System.out.println(" ");
        System.out.println("This entry is now deleted");
    }

    public static void updateAccountEntry(AccountEntry entry) throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        EntryDAOImpl entryDAO = new EntryDAOImpl();

        // encrypt it
        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedUsername = EncryptionLogic.encrypt(algorithm, entry.getUsername(), key, iv);
        String encryptedPassword = EncryptionLogic.encrypt(algorithm, entry.getPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        AccountEntry updatedAccountEntry = new AccountEntry(entry.getEntryId(), encryptedName, encryptedUsername, encryptedPassword, encryptedNote);
        // send in the info you need
        entryDAO.updateAccountEntry(updatedAccountEntry);
        System.out.println("---");
        System.out.println("this is the entry edited: "+entry);
        System.out.println("the encrypted version: " + updatedAccountEntry);
        System.out.println("---");
    }

    public static void updateCardEntry(CardEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        EntryDAOImpl entryDAO = new EntryDAOImpl();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedCardHolder = EncryptionLogic.encrypt(algorithm, entry.getCardHolder(), key, iv);
        String encryptedCardNumber = EncryptionLogic.encrypt(algorithm, entry.getCardNumber(), key, iv);
        String encryptedExpireMonth = EncryptionLogic.encrypt(algorithm, entry.getExpireMonth(), key, iv);
        String encryptedExpireYear = EncryptionLogic.encrypt(algorithm, entry.getExpireYear(), key, iv);
        String encryptedCVC = EncryptionLogic.encrypt(algorithm, entry.getCvcCode(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        CardEntry updatedCardEntry = new CardEntry(entry.getEntryId(), encryptedName, encryptedCardHolder, encryptedCardNumber, encryptedExpireMonth, encryptedExpireYear, encryptedCVC, encryptedNote);

        entryDAO.updateCardEntry(updatedCardEntry);
        System.out.println("---");
        System.out.println("this is the entry edited: "+entry);
        System.out.println("the encrypted version: " + updatedCardEntry);
        System.out.println("---");
    }

    public static void updateWifiEntry(WifiEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        EntryDAOImpl entryDAO = new EntryDAOImpl();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedWifiName = EncryptionLogic.encrypt(algorithm, entry.getWifiName(), key, iv);
        String encryptedWifiPassword = EncryptionLogic.encrypt(algorithm, entry.getWifiPassword(), key, iv);
        String encryptedWifiURL = EncryptionLogic.encrypt(algorithm, entry.getWifiURL(), key, iv);
        String encryptedWifiAdminPass = EncryptionLogic.encrypt(algorithm, entry.getWifiAdminPassword(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNote(), key, iv);

        WifiEntry updatedWifiEntry = new WifiEntry(entry.getEntryId(), encryptedName, encryptedWifiName, encryptedWifiPassword, encryptedWifiURL, encryptedWifiAdminPass, encryptedNote);

        entryDAO.updateWifiEntry(updatedWifiEntry);
        System.out.println("---");
        System.out.println("this is the entry edited: "+entry);
        System.out.println("the encrypted version: " + updatedWifiEntry);
        System.out.println("---");
    }

    public static void updateNoteEntry(SecureNoteEntry entry) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, SQLException {
        EntryDAOImpl entryDAO = new EntryDAOImpl();

        String encryptedName = EncryptionLogic.encrypt(algorithm, entry.getName(), key, iv);
        String encryptedSubject = EncryptionLogic.encrypt(algorithm, entry.getNoteSubject(), key, iv);
        String encryptedNote = EncryptionLogic.encrypt(algorithm, entry.getNoteContent(), key, iv);

        SecureNoteEntry updatedNoteEntry = new SecureNoteEntry(entry.getEntryId(), encryptedName, encryptedSubject, encryptedNote);

        entryDAO.updateNoteEntry(updatedNoteEntry);
        System.out.println("---");
        System.out.println("this is the entry edited: "+entry);
        System.out.println("the encrypted version: " + updatedNoteEntry);
        System.out.println("---");
    }

}


