package com.example.passwordmanager.Model;

import java.security.SecureRandom;

public class LetterNumberPassword extends PasswordMaker {   // for now means password with no numbers, but with special chars (change everything soon)
    @Override
    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
