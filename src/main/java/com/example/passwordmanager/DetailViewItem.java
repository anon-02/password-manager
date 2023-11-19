package com.example.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DetailViewItem extends AnchorPane{
    private MainViewController parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();


    @FXML private AnchorPane baseAnchorPane;

    /** Used for account entries **/
    @FXML private TextField entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible;
    @FXML private TextArea accountNote;
    @FXML private ImageView eyeImageView, cogImageView;

    /** Used for card entries **/
    @FXML private AnchorPane nonEditableExpireDatePane, editableExpireDatePane;
    @FXML private TextField entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible;
    @FXML private ChoiceBox<String> cardMonthChoiceBox, cardYearChoiceBox;
    @FXML private TextArea cardNote;
    @FXML private ImageView cardEye;

    /** Used for wi-fi entries **/


    /** Used for secure note entries **/

    private String currentEntry;

    public DetailViewItem(AccountEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/display_view_account.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;

        this.entryAccountName.setText(entry.getName());
        this.accountUsername.setText(entry.getUsername());
        this.accountPasswordInvisible.setText(entry.getPassword());
        this.accountPasswordVisible.setText(entry.getPassword());
        this.accountNote.setText(entry.getNote());

        helper.addPasswordVisibleToggle(eyeImageView, accountPasswordInvisible, accountPasswordVisible);

        currentEntry = "accountEntry"; // Make an enum
    }

    public DetailViewItem(CardEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/display_view_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;

        String[] expireMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] expireYears = {"24", "25", "26", "27", "28", "29", "30"};
        this.cardMonthChoiceBox.getItems().addAll(expireMonths);
        this.cardYearChoiceBox.getItems().addAll(expireYears);

        this.entryCardName.setText(entry.getName());
        this.cardHolder.setText(entry.getCardNumber());
        this.cardNumber.setText(entry.getCardNumber());
        this.cardMonth.setText(entry.getExpireMonth());
        this.cardYear.setText(entry.getExpireYear());
        this.cardCVCInvisible.setText(entry.getCvcCode());
        this.cardCVCVisible.setText(entry.getCvcCode());
        this.cardNote.setText(entry.getNote());

        helper.addPasswordVisibleToggle(cardEye, cardCVCInvisible, cardCVCVisible);

        currentEntry = "cardEntry";
    }

}
