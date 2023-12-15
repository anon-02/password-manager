package com.example.passwordmanager.Password;

/**
 * Utility class for generating random password based on the specific criteria.
 */
public class PasswordGenerator {

    public static String generatePassword(int length, boolean includeUpper, boolean includeNumbers, boolean includeSpecial) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()_-+=<>?/";

        StringBuilder characters = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        // Include additional character sets based on criteria
        if (includeUpper) {
            characters.append(uppercaseLetters);
        }
        if (includeNumbers) {
            characters.append(numbers);
        }
        if (includeSpecial) {
            characters.append(specialCharacters);
        }
        // Generate password by randomly selecting characters from the combined set
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }
}




























