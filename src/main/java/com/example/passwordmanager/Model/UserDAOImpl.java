package com.example.passwordmanager.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    // crud retrive
    @Override
    public User get(int id) throws SQLException {
        Connection connection = DatabaseHandler.connect();
        User user = null;

        String sql  = "SELECT id, user_ID, username, master_password FROM users WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            int oid = resultSet.getInt("id");
            int userId = resultSet.getInt("user_id");
            String username = resultSet.getString("username");
            String master_password = resultSet.getString("master_password");

            user = new User(oid, userId, username, master_password);
        }
        return user;
    }

    // crud retrieve all
    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    // crud created / update
    @Override
    public int save(User user) throws SQLException {
        return 0;
    }


    // crud create
    @Override
    public int insert(User user) throws SQLException {
        Connection connection = DatabaseHandler.connect();

        String sql = "INSERT INTO users (user_id, username, master_password) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, user.getUser_id());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getMasterPassword());

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }



    // crud update
    @Override
    public int update(User user) throws SQLException {
        Connection connection = DatabaseHandler.connect();

        String sql = "UPDATE users set user_ID = ?, username = ?, master_password = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, user.getUser_id());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getMasterPassword());
        preparedStatement.setInt(4, user.getID());

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }

    // crud delete
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
}
