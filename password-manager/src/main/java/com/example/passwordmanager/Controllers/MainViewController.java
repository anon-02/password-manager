package com.example.passwordmanager.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML private AnchorPane backAnchorPane;
    @FXML private TextField searchTextField;
    @FXML private Button addEntryButton;
    @FXML private FlowPane allEntrysFlowPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.setText("Test");
        //updateEntryList();
    }

    private void updateEntryList() {
        allEntrysFlowPane.getChildren().clear();
        allEntrysFlowPane.getChildren().add(new EntryListItem());
    }
}
