package com.example.passwordmanager.Model;

import java.security.SecureRandom;

/** Description of class **/

public class NumbersPassword extends PasswordMaker { //not really a pure numbers password for now, that it's a numbers password means that it includes numbers and no special char

    @Override
    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
