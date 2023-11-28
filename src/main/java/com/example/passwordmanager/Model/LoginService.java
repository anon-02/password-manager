package com.example.passwordmanager.Model;

import com.example.passwordmanager.Model.dbStuff.EncryptionLogic;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.Model.dbStuff.UserDAOImpl;
import com.google.common.hash.Hashing;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Base64;

public class LoginService {
    private UserDAOImpl userDAO = new UserDAOImpl();
    SessionManager sessionManager = new SessionManager();

    public LoginService() {
        this.userDAO = new UserDAOImpl();
    }

    // TODO Login()   void for now, send user_id later
    public void LogIn(String username, String masterPassword) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String hashedPass = HashPass(masterPassword);

        boolean userExist = userDAO.doesUserExist(username);


        // create key with the pass input and .getsalt

        if (userExist) {
            System.out.println("User exists");
            boolean correctPass = userDAO.isPassCorrect(hashedPass);
            if (correctPass) {
                User currentUser = userDAO.getUserByUsername(username);

                SecretKey key = EncryptionLogic.getKeyFromPassword(masterPassword, currentUser.getSalt());
                byte[] MainIV = currentUser.getIV();
                IvParameterSpec iv = bytesToIv(MainIV);

                SessionManager.setActiveSecretKey(key);
                SessionManager.setActiveIV(iv);

                SessionManager.setCurrentUser(currentUser);
                System.out.println("current user in session-manager: "+SessionManager.getCurrentUser());
                System.out.println("Correct login, user '" + username + "' logging in");
            } else {
                System.out.println("Incorrect Password");
            }
        } else {
            System.out.println("This user is not registered here!");
        }


        // TODO connect to view and restrict access

    }

    // create new user connect to CreateUserViewController
    // TODO proper exception handling, check for already used mail addresses
    public int CreateNewUser(String username, String masterPassword) throws SQLException {


        String hashedPass = HashPass(masterPassword);
        IvParameterSpec newIV = EncryptionLogic.generateIv();
        byte[] ivBytes = ivToBytes(newIV);

        User newUser = new User(0, username, hashedPass, generateSalt(), ivBytes);

        int result = userDAO.insert(newUser);
        System.out.println(result +" new user added successfully");

        return result;
    };

    public String HashPass(String masterPassword) {
        String sha256hex = Hashing.sha256().hashString(masterPassword, StandardCharsets.UTF_8).toString();
        System.out.println("hashedpass: "+sha256hex );
        return sha256hex;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static byte[] ivToBytes(IvParameterSpec ivSpec) {
        return ivSpec.getIV();
    }

    public static IvParameterSpec bytesToIv(byte[] ivBytes) {
        return new IvParameterSpec(ivBytes);
    }

}
