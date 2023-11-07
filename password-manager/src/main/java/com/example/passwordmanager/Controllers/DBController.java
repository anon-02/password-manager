package com.example.passwordmanager.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;  
import java.io.IOException; 

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DBController {
    
    // TODO getters/setterse
    // TODO test on main.java

    // the username for the masterpassword
    @FXML private TextField userReg; 

    // masterpassword input 
    @FXML private TextField passReg; 

    @FXML 
    private String getInput() {
        String userInput = userReg.getText();
        System.out.println(userInput);
        return userInput;
    }

    @FXML 
    private String getMasterPass() {
        String masterPassword = passReg.getText();
        System.out.println(masterPassword);
        return masterPassword; 
    }

    // call this function on register to create a new user, creates new db file with user and password table
    @FXML 
    protected void createNewUserDatabase() {
        // wonky-ass way to get to the right file path but works for now 
        // refactor this at some point
        // currently makes a new db file for every user, might be better to have everyone use the same file though 
        String projectRoot = System.getProperty("user.dir");
        String folderPath = projectRoot + "/databases/";

        String newUser = getInput();
        String masterPass = getMasterPass();
        File userDBFile = new File( folderPath + newUser + ".db");

        try {
            if (userDBFile.createNewFile()) {
                SQLConnection(userDBFile, newUser, masterPass);
                System.out.println("User database file created successfully.");
            } else {
                System.out.println("User database file already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to create the user database file.");
        }
    }

    @FXML 
    public static void createNewUserTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String createNewUserTableSQL = "CREATE TABLE IF NOT EXISTS users ("
        + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + "username VARCHAR(255) NOT NULL UNIQUE,"
        + "masterPassword VARCHAR(255) NOT NULL"
        + ");";

        String createNewPasswordTableSQL = "CREATE TABLE IF NOT EXISTS passwords ("
        + "password_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + "user_id INTEGER,"
        + "username TEXT VARCHAR(255),"
        + "website_url VARCHAR(255),"
        + "password_type VARCHAR(255),"
        // date_added
        + "password TEXT NOT NULL,"
        + "last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
        + "FOREIGN KEY (user_id) REFERENCES users (user_id)"
        + ");";

        statement.executeUpdate(createNewUserTableSQL);
        statement.executeUpdate(createNewPasswordTableSQL);
        statement.close();
        System.out.println("Table created successfully.");
    }

    // adds the new user into the user db, doesnt really make sense when the whole table is made up of one user  
    @FXML
    public static int insertUserData(Connection connection, String username, String masterPass) throws SQLException {
        String insertUserSQL = "INSERT INTO users (username, masterPassword) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, masterPass);
            int rowsAffected = preparedStatement.executeUpdate();

             if (rowsAffected == 1) {
                // Retrieve the generated user ID
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); 
                }
            }
        }
        throw new SQLException("User data insertion failed or no user ID retrieved.");
    }

    // connects to the db files and updates/creates them
    @FXML 
    public static void SQLConnection(File userDBFile, String newUser, String masterPass) {
        String url = "jdbc:sqlite:" + userDBFile;
        Connection dbConnection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection(url);

            if (dbConnection != null) {
                System.out.println("Connected to the database.");
                
                createNewUserTable(dbConnection);
                insertUserData(dbConnection, newUser, masterPass);
                dbConnection.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }

    
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
        } catch (SQLException e) {
            System.err.println("Connection to the database failed: " + e.getMessage());
        }
    }

    // Add to password table 
    // get specific type of password
    // TODO get all passwords 
   
}
