package com.example.passwordmanager.Model.dbStuff;

import java.sql.*;

/*
* Class dealing with setting up a connection to the .db file.
*/
public class DatabaseHandler {

    private static final String JDBC_URL = "jdbc:sqlite:C:./Database/users.db";
    private static Connection  connection;

    private DatabaseHandler() {
    }

    // Connect to user database
    public static Connection DBconnect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL);
        }
        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }


    public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }


}
