package com.example.passwordmanager;
import com.example.passwordmanager.CategoryEntry;
import com.example.passwordmanager.DisplayableEntry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class CategoryEntryListItem extends AnchorPane {

    @FXML private AnchorPane categoryEntryAnchorPane;
    @FXML private Label categoryName;
    @FXML private Label nrOfPasswords;

    @FXML private ImageView downwardsArrow;
    @FXML private ImageView edit;

    MainViewController parentController;

    private CategoryEntry categoryEntry;


    public CategoryEntryListItem(CategoryEntry categoryEntry, MainViewController controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/category_entry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        this.categoryEntry = categoryEntry;
        this.categoryName.setText(categoryEntry.getName().toUpperCase());
        setNrOfPasswords();
    }

    public void setNrOfPasswords() {
        int nrOfPasswords = categoryEntry.getNrOfPasswords();
        if (nrOfPasswords > 0) {
            if (nrOfPasswords == 1)
                this.nrOfPasswords.setText("1 password");
            else
                this.nrOfPasswords.setText(nrOfPasswords + " passwords");
        }
        else {
            this.nrOfPasswords.setText("");
        }
    }

    @FXML
    public void manipulateCategory() throws IOException, InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (this.categoryEntry.isOpen()) {
            this.categoryEntry.setAsClose();
        }
        else {
            this.categoryEntry.setAsOpen();
        }
        this.parentController.updateEntryList();
    }

    @FXML
    public void editCategory(Event event) throws IOException {
        parentController.openCategoryEdit(categoryEntry);
    }

    public void hoverIn(Event event) throws IOException {
        edit.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/3dotsblack.png")).openStream()));
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-arrow-new.png")).openStream()));
        else
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-arrowUP.png")).openStream()));
    }

    public void hoverOut(Event event) throws IOException {
        edit.setImage(null);
        this.downwardsArrow.setImage(null);
    }

    public void arrowHoverIn(Event event) throws IOException {
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-filled.png")).openStream()));
        else
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-filledUP.png")).openStream()));
    }
}