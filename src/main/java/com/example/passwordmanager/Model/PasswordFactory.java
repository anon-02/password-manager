package com.example.passwordmanager.Model;

public class PasswordFactory {

    public static NumbersPassword makeNumbersPassword() {
        return new NumbersPassword();
    }

    public static LettersPassword makeLettersPassword() {
        return new LettersPassword();
    }

    public static AllSignsPassword makeAllSignsPassword() {
        return new AllSignsPassword();
    }

    public static LetterNumberPassword makeLetterNumbersPassword() {
        return new LetterNumberPassword();
    }

}
