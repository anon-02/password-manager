package com.example.passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/main_view.fxml"));
        URL location = getClass().getResource("Views/main_view.fxml");
        System.out.println(location);
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Password Vault");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}