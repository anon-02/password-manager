package com.example.passwordmanager.Password;

public class PasswordGenerator {

    public static String generatePassword(int length, boolean includeUpper, boolean includeNumbers, boolean includeSpecial) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()_-+=<>?/";

        StringBuilder characters = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        if (includeUpper) {
            characters.append(uppercaseLetters);
        }
        if (includeNumbers) {
            characters.append(numbers);
        }
        if (includeSpecial) {
            characters.append(specialCharacters);
        }

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }
}




























