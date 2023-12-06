package com.example.passwordmanager.Password;

import java.security.SecureRandom;

public class PasswordGenerator {    // could use decorator pattern for design pattern, or other pattern to extract common code

    public static String generatePassword(int length, boolean includeUppercase, boolean includeNumber, boolean includeSpecial) {
        String characters = getCharacters(includeUppercase, includeNumber, includeSpecial);
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    private static String getCharacters(boolean includeUppercase, boolean includeNumber, boolean includeSpecial) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        if (includeUppercase) {
            characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (includeNumber) {
            characters += "0123456789";
        }
        if (includeSpecial) {
            characters += "!@#$%^&*()-_=+";
        }
        return characters;
    }
}
