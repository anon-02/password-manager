package com.example.passwordmanager.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    // crud retrive
    // TODO maybe remove
    @Override
    public User get(int id) throws SQLException {
        Connection connection = DatabaseHandler.connect();
        User user = null;

        String sql  = "SELECT id, username, master_password FROM users WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            int oid = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String master_password = resultSet.getString("master_password");

            user = new User(oid, username, master_password);
        }
        return user;
    }

    // crud retrieve all
    // TODO maybe remove
    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    // crud created / update
    // TODO remove
    @Override
    public int save(User user) throws SQLException {
        return 0;
    }


    // crud create
    @Override
    public int insert(User user) throws SQLException {
        Connection connection = DatabaseHandler.connect();
                       // fake errors weird
        String sql = "INSERT INTO users (username, master_password) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getMasterPassword());

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }



    // crud update
    // TODO implement forgotten password
    @Override
    public int update(User user) throws SQLException {
        Connection connection = DatabaseHandler.connect();

        String sql = "UPDATE users set username = ?, master_password = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getMasterPassword());

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }

    // crud delete
    // TODO maybe useful
    @Override
    public int delete(User user) throws SQLException {
        Connection connection = DatabaseHandler.connect();

        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, user.getID());

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }

    public boolean doesUserExist(String username)throws SQLException {
        Connection connection = DatabaseHandler.connect();
        String sql = "SELECT 1 FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next(); // if exists = true, otherwise false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPassCorrect(String hashedMasterPassword) throws SQLException {
        Connection connection = DatabaseHandler.connect();
        String sql = "SELECT 1 FROM users WHERE master_password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hashedMasterPassword);
        System.out.println("This is the hashed pass being asked for: " + hashedMasterPassword);


        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
