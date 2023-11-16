package com.example.passwordmanager.Model;

public class PasswordFactory {

    public static NumbersPassword makeNumbersPassword() {
        return new NumbersPassword();
    }

    public static LettersPassword makeLettersPassword() {
        return new LettersPassword();
    }

}
