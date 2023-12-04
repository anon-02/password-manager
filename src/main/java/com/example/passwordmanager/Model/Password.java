package com.example.passwordmanager.Model;
import java.util.Date;
/** Description of class **/

public class Password {
    private PasswordMaker passwordMaker;
    private String password;
    private String type;
    private int minLength = 0;  // default, means that there is no length requirement
    private int maxLength = 0;  // default, means that there is no length requirement
    private Date dateLastModified;

    // constructor
    public Password(){

    }

    public Password (PasswordMaker passwordMaker) {
        this.passwordMaker = passwordMaker;
    }

    // constructor with initial length requirement
    public Password (PasswordMaker passwordMaker, int minLength, int maxLength) {
        this.passwordMaker = passwordMaker;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void generatePassword(int length) {
        this.password = this.passwordMaker.generatePassword(length);
    }

    public void setMinLength(int length) {
        this.minLength = length;
    }

    public void setMaxLength(int length) {
        this.maxLength = length;
    }

    public void setPasswordMaker(PasswordMaker passwordMaker) {
        this.passwordMaker = passwordMaker;
    }

    public Date getDateLastModified() {
        return this.dateLastModified;
    }
}