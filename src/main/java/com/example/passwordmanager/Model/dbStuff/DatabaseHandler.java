package com.example.passwordmanager.Model.dbStuff;

import java.sql.*;

public class DatabaseHandler {
    private static final String JDBC_URL = "jdbc:sqlite:C:\\Users\\Tim\\Chalmers\\oopp\\password-manager\\Database\\users.db";
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

    public static void closeStatement(Statement statement) throws SQLException {
        statement.close();
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

}
