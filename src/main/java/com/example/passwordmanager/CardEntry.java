package com.example.passwordmanager;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class CardEntry implements Entry{

    private String name, cardNumber, expireMonth, expireYear, cvcCode, note;
    private Image image;


    public CardEntry(String name, String cardNumber, String expireMonth, String expireYear, String cvcCode, String note) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.cvcCode = cvcCode;
        this.note = note;
        //this.modified = timeCreated; TODO implement timeModified
    }

    public String getName() {
        return this.name;
    }

    public String getUnderName() {
        return "•••• •••• •••• " + this.cardNumber.substring(this.cardNumber.length()-4);
    }

    public String getCardNumber() {return this.cardNumber;}

    public String getExpireMonth() {
        return this.expireMonth;
    }

    public String getExpireYear() {
        return this.expireYear;
    }

    public String getCvcCode() {
        return this.cvcCode;
    }

    public String getNote() {
        return this.note;
    }

    public Image getImage() throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/credit-card.png")).openStream());
    }

    public void setName(String newString) {
        this.name = newString;
    }

    public void setUsername(String newString) {
        this.cardNumber = newString;
    }

    public void setExpireMonth(String newString) {
        this.expireMonth = newString;
    }

    public void setExpireYear(String newString) {
        this.expireYear = newString;
    }

    public void setCvcCode(String newString) {
        this.cvcCode = newString;
    }

    public void setNote(String newString) {
        this.note = newString;
    }
}
