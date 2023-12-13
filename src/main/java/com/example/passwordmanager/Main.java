package com.example.passwordmanager;

import com.example.passwordmanager.Model.EntriesListHandler;
import com.example.passwordmanager.Model.dbStuff.*;
import com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation.EntryDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private final fxmlHelper helper = fxmlHelper.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(helper.getFxmlFile("login_view.fxml"));
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

        launch();

        // Makes sure changes are saved when exiting the application
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                EntriesListHandler.getInstance().saveAllEntries();
            }
        }));
    }
}
