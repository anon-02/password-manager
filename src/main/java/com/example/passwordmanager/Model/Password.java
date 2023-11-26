package com.example.passwordmanager.Model;
import java.util.Date;
/** Description of class **/

public class Password {
    private PasswordMaker passwordMaker;
    private String password;
    private String type;
    private int length;
    private int absoluteLengthRequirement;
    private boolean hasAbsoluteLengthRequirement = false;
    private Date dateLastModified;

    // constructor
    public Password (PasswordMaker passwordMaker) {
        this.passwordMaker = passwordMaker;
    }

    // constructor with initial length requirement
    public Password (PasswordMaker passwordMaker, int minLength, int maxLength) {
        this.passwordMaker = passwordMaker;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPasswordMaker(PasswordMaker passwordMaker) {
        this.passwordMaker = passwordMaker;
    }

    public Date getDateLastModified() {
        return this.dateLastModified;
    }
}
