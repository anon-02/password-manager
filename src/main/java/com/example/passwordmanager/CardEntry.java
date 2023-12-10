package com.example.passwordmanager;

import com.example.passwordmanager.DisplayableEntry;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class CardEntry implements DisplayableEntry {

    private String name, cardHolder, cardNumber, expireMonth, expireYear, cvcCode, note;
    private Image image;
    int entryId;
    int type = 2;


    public CardEntry(int entryId, String name, String cardHolder, String cardNumber, String expireMonth, String expireYear, String cvcCode, String note) {
        this.entryId = entryId;
        this.name = name;
        this.cardHolder = cardHolder;
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

    public String getCardHolder() {return this.cardHolder;}

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

    public int getType() { return type;}

    @Override
    public int getEntryId() {
        return entryId;
    }

    public Image getImage() throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/credit-card.png")).openStream());
    }

    public void setName(String newString) {
        this.name = newString;
    }

    public void setCardHolder(String newString) {
        this.cardHolder = newString;
    }

    public void setCardNumber(String newString) {
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
