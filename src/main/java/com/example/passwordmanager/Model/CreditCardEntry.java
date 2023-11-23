package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/** Description of class **/

public class CreditCardEntry extends Entry {

    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String CVC ;


    public CreditCardEntry(String name, String identifier, String cardHolderName, String expirationDate, String CVC) throws IOException {
        super("CREDITCARD", name, identifier, "NUMBERS", 4, 4);
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.CVC = CVC;
        setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/credit-card.png")).openStream()));
        changePasswordType("NUMBERS");
    }

    public CreditCardEntry() {};
}
