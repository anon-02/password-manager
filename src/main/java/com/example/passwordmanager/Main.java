package com.example.passwordmanager;

import com.example.passwordmanager.Model.*;

import com.example.passwordmanager.Model.dbStuff.UserDAO;
import com.example.passwordmanager.Model.dbStuff.UserDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/login_view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/main_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("PasswordManager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        UserDAO userDAO = new UserDAOImpl();

        //   User user2 = new User(2, 2, "johnny", "real password right here");


        // int result2 = userDAO.insert(user2);


        launch();
    }
}