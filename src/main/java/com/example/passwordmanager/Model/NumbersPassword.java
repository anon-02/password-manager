package com.example.passwordmanager.Model;
/** Description of class **/

public class NumbersPassword extends PasswordMaker {

    @Override
    public String setPassword(int minLength, int maxLength, String password) {
        // if password is not number, raise exception
        // if password.length < minLength or > maxLength, raise exception
        // else return password
        return password;
    }
}
