package com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation;

import com.example.passwordmanager.Model.Entries.CardEntry;
import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.DatabaseHandler;
import com.example.passwordmanager.Model.dbStuff.EntryDAO;
import com.example.passwordmanager.Model.dbStuff.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CardEntryDAOImpl implements EntryDAO<CardEntry> {

    @Override
    public List<CardEntry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int insert(CardEntry cardEntry) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();
        int type = cardEntry.getType();

        Connection connection = DatabaseHandler.DBconnect();

        String cardEntrySql = "INSERT INTO CardEntry (Name, CardHolder, CardNumber, expireMonth, expireYear, CVC, Note, Type, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(cardEntrySql);

        preparedStatement.setString(1, cardEntry.getName());
        preparedStatement.setString(2, cardEntry.getCardHolder());
        preparedStatement.setString(3, cardEntry.getCardNumber());
        preparedStatement.setString(4, cardEntry.getExpireMonth());
        preparedStatement.setString(5, cardEntry.getExpireYear());
        preparedStatement.setString(6, cardEntry.getCvcCode());
        preparedStatement.setString(7, cardEntry.getNote());
        preparedStatement.setInt(8, type);
        preparedStatement.setInt(9, user_id);
        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }

    @Override
    public int update(CardEntry entry) throws SQLException {
        Connection connection = DatabaseHandler.DBconnect();
        String sql = "UPDATE CardEntry SET Name = ?, CardHolder = ?, CardNumber = ?, expireMonth = ?, expireYear = ?, CVC = ?, Note = ? WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, entry.getName());
        preparedStatement.setString(2, entry.getCardHolder());
        preparedStatement.setString(3, entry.getCardNumber());
        preparedStatement.setString(4, entry.getExpireMonth());
        preparedStatement.setString(5, entry.getExpireYear());
        preparedStatement.setString(6, entry.getCvcCode());
        preparedStatement.setString(7, entry.getNote());
        preparedStatement.setInt(8, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);
        System.out.println("Successful entry update " + result);

        return result;
    }

    @Override
    public int delete(CardEntry entry) throws SQLException {
        String sql = "DELETE FROM CardEntry WHERE id = ?";
        Connection connection = DatabaseHandler.DBconnect();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }
}
