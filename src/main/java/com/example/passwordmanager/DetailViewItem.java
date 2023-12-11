package com.example.passwordmanager;

import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.Password.PassphraseGenerator;
import com.example.passwordmanager.Password.PasswordGenerator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class DetailViewItem extends AnchorPane implements Generator{
    private MainViewController parentController;
    private fxmlHelper helper = fxmlHelper.getInstance();

    @FXML private AnchorPane baseAnchorPane;

    /** Used for account entries **/
    @FXML private AnchorPane noteAnchorPane;
    @FXML private TextField entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible;
    @FXML private TextArea accountNote;
    @FXML private ImageView eyeImageView, cogImageView;
    @FXML private FlowPane generatePasswordFlowPane;
    private boolean passwordGeneratorShowing = false;

    TextField[] accountFields;

    /** Used for card entries **/
    @FXML private AnchorPane nonEditableExpireDatePane, editableExpireDatePane;
    @FXML private TextField entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible;
    @FXML private ChoiceBox<String> cardMonthChoiceBox, cardYearChoiceBox;
    @FXML private TextArea cardNote;
    @FXML private ImageView cardEye;

    TextField[] cardFields;

    /** Used for wi-fi entries **/
    @FXML private TextField entryWifiName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiConfigURL, wifiAdminPassword;
    @FXML private TextArea wifiNote;
    @FXML private ImageView wifiEyeImageView;

    TextField[] wifiFields;

    /** Used for secure note entries **/
    @FXML private TextField entryNoteName, noteSubject;
    @FXML private TextArea noteContent;

    TextField[] secureNoteFields;

    private DisplayableEntry currentEntry;
    private PasswordFieldManager manager;

    public DetailViewItem(AccountEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/detail_view_account.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;
        manager = helper.addPasswordVisibleToggle(eyeImageView, accountPasswordInvisible, accountPasswordVisible);

        this.entryAccountName.setText(entry.getName());
        this.accountUsername.setText(entry.getUsername());
        manager.setPassword(entry.getPassword());
        this.accountNote.setText(entry.getNote());


        accountFields = new TextField[]{entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible};


        currentEntry = entry;

        EventHandler<MouseEvent> cogClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cogClicked();
            }

            private void cogClicked() {
                if (passwordGeneratorShowing) {
                    generatePasswordFlowPane.getChildren().clear();
                    noteAnchorPane.setLayoutY(255);
                } else {
                    noteAnchorPane.setLayoutY(476);
                    generatePasswordFlowPane.getChildren().clear();
                    generatePasswordFlowPane.getChildren().add(generateItem("detail"));
                }
                passwordGeneratorShowing = !passwordGeneratorShowing;
            }
        };
        cogImageView.setOnMouseClicked(cogClick);
    }

    public DetailViewItem(CardEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/detail_view_card.fxml"));
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

        this.cardFields = new TextField[]{entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible};


        currentEntry = entry;
    }

    public DetailViewItem(WifiEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/detail_view_wifi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = controller;
        manager = helper.addPasswordVisibleToggle(wifiEyeImageView, wifiPasswordInvisible, wifiPasswordVisible);

        this.entryWifiName.setText(entry.getName());
        this.wifiName.setText(entry.getWifiName());
        manager.setPassword(entry.getWifiPassword());
        this.wifiConfigURL.setText(entry.getWifiURL());
        this.wifiAdminPassword.setText(entry.getWifiAdminPassword());
        this.wifiNote.setText(entry.getNote());

        this.wifiFields = new TextField[]{entryWifiName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiConfigURL, wifiAdminPassword};

        currentEntry = entry;
    }

    public DetailViewItem(SecureNoteEntry entry, MainViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/detail_view_secure-note.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;

        this.entryNoteName.setText(entry.getName());
        this.noteSubject.setText(entry.getNoteSubject());
        this.noteContent.setText(entry.getNoteContent());

        this.secureNoteFields = new TextField[]{entryNoteName, noteSubject};

        currentEntry = entry;
    }

    public void toggleEditable() {
        if (currentEntry instanceof AccountEntry) {
            for (TextField t: this.accountFields) {
                t.setEditable(!t.isEditable());
            }
            accountNote.setEditable(!accountNote.isEditable());

        } else if (currentEntry instanceof CardEntry) {
            for (TextField t: this.cardFields) {
                t.setEditable(!t.isEditable());
            }
            cardYear.setEditable(!cardYear.isEditable());
            cardMonth.setEditable(!cardMonth.isEditable());
            cardNote.setEditable(!cardNote.isEditable());

        } else if (currentEntry instanceof WifiEntry) {
            for (TextField t: this.wifiFields) {
                t.setEditable(!t.isEditable());
            }
            wifiNote.setEditable(!wifiNote.isEditable());

        } else if (currentEntry instanceof SecureNoteEntry) {
            for (TextField t: this.secureNoteFields) {
                t.setEditable(!t.isEditable());
            }
            noteContent.setEditable(!noteContent.isEditable());
        }
    }

    // Updates the current entry viewed in the detail
    public void updateDetailEntry() throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (currentEntry instanceof AccountEntry) {

            // make a new entry instance and send the edited data to the db
            AccountEntry updateEntry = new AccountEntry(currentEntry.getEntryId(), entryAccountName.getText(), accountUsername.getText(), manager.getPassword(), accountNote.getText());
            System.out.println("updated entry: ");
            System.out.println(updateEntry);
            EncryptionBuffer.updateAccountEntry(updateEntry);

        } else if (currentEntry instanceof CardEntry) {

            CardEntry updateEntry = new CardEntry(currentEntry.getEntryId(), entryCardName.getText(), cardHolder.getText(), cardNumber.getText(), cardMonth.getText(), cardYear.getText(), manager.getPassword(), cardNote.getText());

            System.out.println("updated entry: ");
            System.out.println(updateEntry);
            EncryptionBuffer.updateCardEntry(updateEntry);

        } else if (currentEntry instanceof WifiEntry) {

            WifiEntry updateEntry = new WifiEntry(currentEntry.getEntryId(), entryWifiName.getText(), wifiName.getText(), manager.getPassword(), wifiConfigURL.getText(), wifiAdminPassword.getText(), wifiNote.getText());

            System.out.println("updated entry (detailview): ");
            System.out.println(updateEntry);
            EncryptionBuffer.updateWifiEntry(updateEntry);

        } else if (currentEntry instanceof SecureNoteEntry) {
            // TODO update current SecureNoteEntry

            SecureNoteEntry updateEntry = new SecureNoteEntry(currentEntry.getEntryId(), entryNoteName.getText(), noteSubject.getText(), noteContent.getText());

            System.out.println("updated entry (detailview): ");
            System.out.println(updateEntry);
            EncryptionBuffer.updateNoteEntry(updateEntry);

        }
    }

    @FXML
    public void deleteCurrentEntry() {
        //
    }

    private PasswordGeneratorItem generateItem(String s) {
        return new PasswordGeneratorItem(this, s);
    }

    @Override
    public void generate(String type, int length, boolean includeUpper, boolean includeNumbers, boolean includeSpecial) {
        String generatedPassword = null;
        if (type.equals("Password")) {
            generatedPassword = PasswordGenerator.generatePassword(length, includeUpper, includeNumbers, includeSpecial);

        } else if (type.equals("Passphrase")) {
            generatedPassword = PassphraseGenerator.generatePassphrase(length, includeUpper, includeNumbers, includeSpecial);
        }
        manager.setPassword(generatedPassword);
    }

    public DisplayableEntry getEntry () {
        System.out.println("current entry about to be selected in detailview "+ currentEntry );
        return currentEntry;}
}
