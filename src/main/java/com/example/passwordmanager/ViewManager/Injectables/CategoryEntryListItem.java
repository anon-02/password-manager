package com.example.passwordmanager.ViewManager.Injectables;
import com.example.passwordmanager.Model.Entries.CategoryEntry;
import com.example.passwordmanager.ViewManager.MainViewManager;
import com.example.passwordmanager.fxmlHelper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CategoryEntryListItem extends AnchorPane {

    @FXML private AnchorPane categoryEntryAnchorPane;
    @FXML private Label categoryName;
    @FXML private Label nrOfPasswords;

    @FXML private ImageView downwardsArrow;
    @FXML private ImageView edit;

    MainViewManager parentController;

    private CategoryEntry categoryEntry;
    private final fxmlHelper helper = fxmlHelper.getInstance();


    public CategoryEntryListItem(CategoryEntry categoryEntry, MainViewManager controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("category_entry.fxml"));
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
    public void manipulateCategory() {
        if (this.categoryEntry.isOpen()) {
            this.categoryEntry.setAsClose();
        }
        else {
            this.categoryEntry.setAsOpen();
        }
        this.parentController.updateEntryList();
    }

    @FXML
    public void editCategory(Event event) {
        parentController.openCategoryEdit(categoryEntry);
    }

    public void hoverIn(Event event) throws IOException {
        edit.setImage(helper.getImage("3dotsblack.png"));
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(helper.getImage("drop-down-arrow-new.png"));
        else
            this.downwardsArrow.setImage(helper.getImage("drop-down-arrowUP.png"));
    }

    public void hoverOut(Event event) throws IOException {
        edit.setImage(null);
        this.downwardsArrow.setImage(null);
    }

    public void arrowHoverIn(Event event) throws IOException {
        if (!this.categoryEntry.isOpen())
            this.downwardsArrow.setImage(helper.getImage("drop-down-filled.png"));
        else
            this.downwardsArrow.setImage(helper.getImage("drop-down-filledUP.png"));
    }
}