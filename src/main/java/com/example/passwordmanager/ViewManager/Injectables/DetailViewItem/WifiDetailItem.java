package com.example.passwordmanager.ViewManager.Injectables.DetailViewItem;

import com.example.passwordmanager.Controller.DetailViewController;
import com.example.passwordmanager.Model.Entries.DisplayableEntry;
import com.example.passwordmanager.Model.Entries.WifiEntry;
import com.example.passwordmanager.Model.PasswordFieldManager;
import com.example.passwordmanager.Model.dbStuff.EncryptionBuffer;
import com.example.passwordmanager.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Injectable AnchorPane in the detailView
 */
public class WifiDetailItem extends AnchorPane implements DetailItemImpl {

    @FXML private AnchorPane baseAnchorPane;
    @FXML private TextField entryWifiName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiConfigURL, wifiAdminPassword;
    @FXML private TextArea wifiNote;
    @FXML private ImageView wifiEyeImageView;

    private DisplayableEntry currentEntry;
    private PasswordFieldManager manager;
    private final fxmlHelper helper = fxmlHelper.getInstance();
    DetailViewController parentController;
    TextInputControl[] wifiFields;

    public WifiDetailItem(WifiEntry entry, DetailViewController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("detail_view_wifi.fxml"));
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

        this.wifiFields = new TextInputControl[]{entryWifiName, wifiName, wifiPasswordInvisible, wifiPasswordVisible, wifiConfigURL, wifiAdminPassword, wifiNote};

        this.currentEntry = entry;
    }
    @Override
    public void toggleEditable() {
        for (TextInputControl field: this.wifiFields) {
            field.setEditable(!field.isEditable());
        }
    }
    @Override
    public void updateEntry() {
        WifiEntry updateEntry = new WifiEntry(currentEntry.getEntryId(), entryWifiName.getText(), wifiName.getText(), manager.getPassword(), wifiConfigURL.getText(), wifiAdminPassword.getText(), wifiNote.getText());
        System.out.println("new updated entry "+ updateEntry);
        EncryptionBuffer.updateWifiEntry(updateEntry);
        if (helper.isDebug()) {
            System.out.println("updated entry (detailview): ");
            System.out.println(updateEntry);
        }
    }

    @Override
    public DisplayableEntry getEntry() {
        return currentEntry;
    }
}
