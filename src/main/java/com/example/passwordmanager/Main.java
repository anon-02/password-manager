package com.example.passwordmanager;

import com.example.passwordmanager.Model.dbStuff.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/login_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("PasswordManager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        // user data endpoint
        SessionManager sessionManager = new SessionManager();
        UserDAO userDAO = new UserDAOImpl();
        EntryDAO entryDAO = new EntryDAOImpl();

     //   User user2 = new User(2, 2, "johnny", "real password right here");


       // int result2 = userDAO.insert(user2);


       launch();
    }
}
