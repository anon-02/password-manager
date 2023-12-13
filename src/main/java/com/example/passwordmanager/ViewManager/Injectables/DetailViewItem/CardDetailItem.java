package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Model.Entries.CardEntry;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CardDetailItem extends AnchorPane implements DetailItemImpl     {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private AnchorPane nonEditableExpireDatePane, editableExpireDatePane;
    @FXML private TextField entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible;
    @FXML private ChoiceBox<String> cardMonthChoiceBox, cardYearChoiceBox;
    @FXML private TextArea cardNote;
    @FXML private ImageView cardEye;

    TextInputControl[] cardFields;
    private final DisplayableEntry currentEntry;
    private final PasswordFieldManager manager;
    private final fxmlHelper helper = fxmlHelper.getInstance();
    DetailViewController parentController;


    public CardDetailItem(CardEntry entry, DetailViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("detail_view_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;
        manager = helper.addPasswordVisibleToggle(cardEye, cardCVCInvisible, cardCVCVisible);

        String[] expireMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] expireYears = {"24", "25", "26", "27", "28", "29", "30"};
        this.cardMonthChoiceBox.getItems().addAll(expireMonths);
        this.cardYearChoiceBox.getItems().addAll(expireYears);

        this.entryCardName.setText(entry.getName());
        this.cardHolder.setText(entry.getCardHolder());
        this.cardNumber.setText(entry.getCardNumber());
        this.cardMonth.setText(entry.getExpireMonth());
        this.cardYear.setText(entry.getExpireYear());
        manager.setPassword(entry.getCvcCode());
        this.cardNote.setText(entry.getNote());

        this.cardFields = new TextInputControl[]{entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible, cardNote};

        this.currentEntry = entry;
    }
    @Override
    public void toggleEditable() {
        for (TextInputControl field: this.cardFields) {
            field.setEditable(!field.isEditable());
        }
        cardYear.setEditable(!cardYear.isEditable());
        cardMonth.setEditable(!cardMonth.isEditable());
    }

    @Override
    public void updateEntry() {
        CardEntry updateEntry = new CardEntry(currentEntry.getEntryId(), entryCardName.getText(), cardHolder.getText(), cardNumber.getText(), cardMonth.getText(), cardYear.getText(), manager.getPassword(), cardNote.getText());
        EncryptionBuffer.updateCardEntry(updateEntry);
        if (helper.isDebug()) {
            System.out.println("updated entry: ");
            System.out.println(updateEntry);
        }
    }

    @Override
    public DisplayableEntry getEntry() {
        return currentEntry;
    }

}
