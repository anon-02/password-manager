package com.example.passwordmanager.Password;

import java.security.SecureRandom;

public class SimplePasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!\"^&#%*";

    public static String generatePassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeNumbers, boolean includeSpecials) {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        String allowedCharacters = "";
        if (includeLowercase) {
            allowedCharacters += LOWERCASE;
        }
        if (includeUppercase) {
            allowedCharacters += UPPERCASE;
        }
        if (includeNumbers) {
            allowedCharacters += NUMBERS;
        }
        if (includeSpecials) {
            allowedCharacters += SPECIAL_CHARACTERS;
        }

        if (allowedCharacters.isEmpty()) {
            throw new IllegalArgumentException("At least one character type should be included.");
        }

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            password.append(allowedCharacters.charAt(randomIndex));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        int length = 12;
        boolean includeLowercase = true;
        boolean includeUppercase = true;
        boolean includeNumbers = true;
        boolean includeSpecials = true;

        String password = generatePassword(length, includeLowercase, includeUppercase, includeNumbers, includeSpecials);
        System.out.println("Generated Password: " + password);
    }
}
