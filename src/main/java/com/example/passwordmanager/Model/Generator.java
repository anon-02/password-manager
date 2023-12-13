package com.example.passwordmanager.Model;

public interface Generator {
    void generate(String selectedToggleButton, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);
}