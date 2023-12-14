package com.example.passwordmanager.Model;

/**
 * Interface for views that feature a PasswordGeneratorItem component
 */
public interface Generator {
    void generate(String selectedToggleButton, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);
}