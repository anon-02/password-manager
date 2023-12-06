package com.example.passwordmanager;

public interface Generator {
    void generate(String selectedToggleButton, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);
}