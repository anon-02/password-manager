package com.example.passwordmanager.Model;

import java.sql.*;

public class DatabaseHandler {
    private static final String USER_JDBC_URL = "jdbc:sqlite:C:/Users/Tim/Chalmers/test/password-manager/Database/users.db";
    private static final String ENTITY_JDBC_URL = "jdbc:sqlite:C:/Users/Tim/Chalmers/test/password-manager/Database/entity.db";
    private static Connection  connection;

    private DatabaseHandler() {

    }

    // Connect to user database
    public static Connection userDBconnect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(USER_JDBC_URL);
        }
        return connection;
    }

    // connect to entity database
    public static Connection entityDBconnect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(ENTITY_JDBC_URL);
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
