package com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation;


import com.example.passwordmanager.Model.Entries.AccountEntry;
import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.DatabaseHandler;
import com.example.passwordmanager.Model.dbStuff.EntryDAO;
import com.example.passwordmanager.Model.dbStuff.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
* dao implementation for specifically accountEntry types containing crud functions
*/
public class AccountEntryDAOImpl implements EntryDAO<AccountEntry> {

    @Override
    public List<AccountEntry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int insert(AccountEntry accountEntry) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

        int type = accountEntry.getType();

        Connection connection = DatabaseHandler.DBconnect();

        String accountEntrySql = "INSERT INTO AccountEntry (Name, Username, Password, Note, Type, User_ID) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(accountEntrySql);

        preparedStatement.setString(1, accountEntry.getName());
        preparedStatement.setString(2, accountEntry.getUsername());
        preparedStatement.setString(3, accountEntry.getPassword());
        preparedStatement.setString(4, accountEntry.getNote());
        preparedStatement.setInt(5, type);
        preparedStatement.setInt(6, user_id);
        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        System.out.println("Successful accountEntryInsert");
        return result;
    }

    @Override
    public int update(AccountEntry entry) throws SQLException {
        Connection connection = DatabaseHandler.DBconnect();
        String sql = "UPDATE AccountEntry SET Name = ?, Username = ?, Password = ?, Note = ? WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, entry.getName());
        System.out.println( "the entry name about to be changed: '"+entry.getName()+ "'");
        preparedStatement.setString(2, entry.getUsername());
        System.out.println("the entry username about to be changed '" +entry.getUsername()+"'");
        preparedStatement.setString(3, entry.getPassword());
        preparedStatement.setString(4, entry.getNote());
        preparedStatement.setInt(5, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);
        System.out.println("Successful entry update " + result);

        return result;
    }

    @Override
    public int delete(AccountEntry entry) throws SQLException {
        String sql = "DELETE FROM AccountEntry WHERE id = ?";
        Connection connection = DatabaseHandler.DBconnect();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }
}
