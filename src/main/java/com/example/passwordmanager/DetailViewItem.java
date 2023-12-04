package com.example.passwordmanager;

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

import java.io.IOException;
import java.util.Objects;

public class DetailViewItem extends AnchorPane{
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

        this.entryAccountName.setText(entry.getName());
        this.accountUsername.setText(entry.getUsername());
        this.accountPasswordInvisible.setText(entry.getPassword());
        this.accountPasswordVisible.setText(entry.getPassword());
        this.accountNote.setText(entry.getNote());

        accountFields = new TextField[]{entryAccountName, accountUsername, accountPasswordInvisible, accountPasswordVisible};
        helper.addPasswordVisibleToggle(eyeImageView, accountPasswordInvisible, accountPasswordVisible);

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
                    generatePasswordFlowPane.getChildren().add(new PasswordGeneratorItem("detail"));
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

        String[] expireMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] expireYears = {"24", "25", "26", "27", "28", "29", "30"};
        this.cardMonthChoiceBox.getItems().addAll(expireMonths);
        this.cardYearChoiceBox.getItems().addAll(expireYears);

        this.entryCardName.setText(entry.getName());
        this.cardHolder.setText(entry.getCardHolder());
        this.cardNumber.setText(entry.getCardNumber());
        this.cardMonth.setText(entry.getExpireMonth());
        this.cardYear.setText(entry.getExpireYear());
        this.cardCVCInvisible.setText(entry.getCvcCode());
        this.cardCVCVisible.setText(entry.getCvcCode());
        this.cardNote.setText(entry.getNote());

        this.cardFields = new TextField[]{entryCardName, cardHolder, cardNumber, cardMonth, cardYear, cardCVCInvisible, cardCVCVisible};
        helper.addPasswordVisibleToggle(cardEye, cardCVCInvisible, cardCVCVisible);

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

        this.entryWifiName.setText(entry.getName());
        this.wifiName.setText(entry.getWifiName());
        this.wifiPasswordVisible.setText(entry.getWifiPassword());
        this.wifiPasswordInvisible.setText(entry.getWifiPassword());
        this.wifiConfigURL.setText(entry.getWifiURL());
        this.wifiAdminPassword.setText(entry.getWifiAdminPassword());
        this.wifiNote.setText(entry.getNote());

        this.wifiFields = new TextField[]{entryWifiName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiConfigURL, wifiAdminPassword};
        helper.addPasswordVisibleToggle(wifiEyeImageView, wifiPasswordInvisible, wifiPasswordVisible);

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
    public void updateDetailEntry() {
        if (currentEntry instanceof AccountEntry) {
            // TODO update current AccountEntry
        } else if (currentEntry instanceof CardEntry) {
            // TODO update current CardEntry
        } else if (currentEntry instanceof WifiEntry) {
            // TODO update current WifiEntry
        } else if (currentEntry instanceof SecureNoteEntry) {
            // TODO update current SecureNoteEntry
        }
    }

    @FXML
    public void deleteCurrentEntry() {
        //
    }

}
