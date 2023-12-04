package com.example.passwordmanager;
import com.example.passwordmanager.Model.CategoryEntry;
import com.example.passwordmanager.Model.DisplayableEntry;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class CategoryEntryListItem extends AnchorPane {

    @FXML private AnchorPane categoryEntryAnchorPane;
    @FXML private Label categoryName;
    @FXML private Label nrOfPasswords;

    @FXML private ImageView downwardsArrow;

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
            this.nrOfPasswords.setText(nrOfPasswords + " passwords");
        }
        else {
            this.nrOfPasswords.setText("");
        }
    }

    @FXML
    public void manipulateCategory() throws IOException {
        if (this.categoryEntry.isOpen()) {
            this.categoryEntry.setAsClose();
        }
        else {
            this.categoryEntry.setAsOpen();
        }
        this.parentController.updateEntryList();
    }

    public void hoverIn(Event event) throws IOException {
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-arrow-new.png")).openStream()));
        else
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-arrowUP.png")).openStream()));
    }

    public void hoverOut(Event event) throws IOException {
        this.downwardsArrow.setImage(null);
    }

    public void arrowHoverIn(Event event) throws IOException {
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-filled.png")).openStream()));
        else
            this.downwardsArrow.setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/drop-down-filledUP.png")).openStream()));
    }
}