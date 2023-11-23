package com.example.passwordmanager.Password;

import java.security.SecureRandom;

public class SimplePasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        PasswordGenerator passwordGenerator = new SimplePasswordGenerator();

        String newPassword = passwordGenerator.generatePassword(12);
        System.out.println("Genererat lösenord: " + newPassword);

    }
}
