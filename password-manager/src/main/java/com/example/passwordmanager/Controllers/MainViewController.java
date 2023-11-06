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

    @FXML AnchorPane backAnchorPane;
    @FXML TextField searchTextField;
    @FXML Button addEntryButton;
    @FXML FlowPane allEntrysFlowPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.setText("Test");
    }
}
