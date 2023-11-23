package com.example.passwordmanager.Model;

import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EntryDAOImpl implements EntryDAO<Entry>{
    @Override
    public Entry get(int id) throws SQLException {
        return null;
    }

    //TODO for the mainiewcontroller
    @Override
    public List<Entry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Entry entry) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Entry entry) throws SQLException {
        Connection connection = DatabaseHandler.entityDBconnect();

        String genericEntrySql = "INSERT INTO entry_list (entry_category, entry_name, entry_password, entry_identifier, entry_password_type,  entry_note, user_id, entry_min_password, entry_max_password, entry_URL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        String cardEntrySql = "INSERT INTO entry_list (entry_card_number, entry_card_holder_name, entry_card_expdate, entry_card_cvc,)"

        PreparedStatement genericStatement=connection.prepareStatement(genericEntrySql);
        PreparedStatement cardEntryStatement=connection.prepareStatement(cardEntrySql);

        preparedStatement.setString(1, entry.getCategory());
        preparedStatement.setString(2, entry.getName());
        preparedStatement.setString(3, entry.getPassword());
        preparedStatement.setString(3, entry.getIdentifier());
        preparedStatement.setString(4, entry.getPasswordType());
        preparedStatement.setInt(5, entry.getCardNumber());
        preparedStatement.setInt(6, entry.getCardHolder());
        preparedStatement.setInt(7, entry.getExpdDate());
        preparedStatement.setInt(8, entry.getCardCVC());
        preparedStatement.setString(9, entry.getNote());
        preparedStatement.setInt(10, entry.getUserId());
        preparedStatement.setInt(11, entry.getMinPasswordLength());
        preparedStatement.setInt(12, entry.getMaxPasswordLength());
        preparedStatement.setString(13, entry.getURL());
        // TODO lastmodified
        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;

        // getters for all vars
    }

    @Override
    public int update(Entry entry) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Entry entry) throws SQLException {
        return 0;
    }
}
