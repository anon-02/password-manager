package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.*;
import com.example.passwordmanager.Entries.AccountEntry;
import com.example.passwordmanager.Entries.CardEntry;
import com.example.passwordmanager.Entries.SecureNoteEntry;
import com.example.passwordmanager.Entries.WifiEntry;
import com.example.passwordmanager.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntryDAOImpl implements EntryDAO<DisplayableEntry> {


    @Override
    public DisplayableEntry get(int id) throws SQLException {
        return null;
    }


    //TODO for the mainiewcontroller
    @Override
    public List<DisplayableEntry> getAll() throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

        Connection connection = DatabaseHandler.DBconnect();
        String sql = "SELECT * FROM AccountEntry WHERE User_ID = ?";

        List<DisplayableEntry> entryList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);

        ResultSet resultSet = preparedStatement.executeQuery();
        getAccountEntries(entryList, user_id, connection);
        getNoteEntries(entryList, user_id, connection);
        // TODO getCardentry
        getWifiEntries(entryList, user_id, connection);

        return entryList;
    }

    @Override
    public int save(DisplayableEntry entry) throws SQLException {
        return 0;
    }

    @Override
    public int insert(DisplayableEntry entry) throws SQLException {
        return 0;
    }

    @Override
    public int update(DisplayableEntry entry) throws SQLException {
        return 0;
    }

    @Override
    public int delete(DisplayableEntry entry) throws SQLException {
        return 0;
    }

    public List<DisplayableEntry> getAccountEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM AccountEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            AccountEntry newAccEntry = new AccountEntry(resultSet.getString("Name"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("Note"));
            entryList.add(newAccEntry);
        }
        return entryList;
    }

    public List<DisplayableEntry> getNoteEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM NoteEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            SecureNoteEntry newNoteEntry = new SecureNoteEntry(resultSet.getString("Name"), resultSet.getString("Subject"), resultSet.getString("Note"));
            entryList.add(newNoteEntry);
        }
        return entryList;
    }

    public List<DisplayableEntry> getWifiEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM WifiEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            WifiEntry newWifiEntry = new WifiEntry(resultSet.getString("Name"), resultSet.getString("WifiName"), resultSet.getString("WifiPassword"), resultSet.getString("ConfigURL"), resultSet.getString("AdminPassword"), resultSet.getString("Note"));
            entryList.add(newWifiEntry);
        }
        return entryList;
    }




    public static int insertAccountEntry(AccountEntry accountEntry, int type) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();
        // the rest should be encrypted before inserting
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
        
        System.out.println("Successful accountInsert");
        return result;
    }

    public static int insertCardEntry(CardEntry cardEntry, int type) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

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

    public static int insertWifiEntry(WifiEntry wifiEntry, int type) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

        Connection connection = DatabaseHandler.DBconnect();

        String wifiEntrySql = "INSERT INTO WifiEntry (Name, WifiName, WifiPassword, ConfigURL, AdminPassword, Note, Type, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(wifiEntrySql);

        preparedStatement.setString(1, wifiEntry.getName());
        preparedStatement.setString(2, wifiEntry.getWifiName());
        preparedStatement.setString(3, wifiEntry.getWifiPassword());
        preparedStatement.setString(4, wifiEntry.getWifiURL());
        preparedStatement.setString(5, wifiEntry.getWifiAdminPassword());
        preparedStatement.setString(6, wifiEntry.getNote());
        preparedStatement.setInt(7, type);
        preparedStatement.setInt(8, user_id);

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }

    public static int insertSecureNoteEntry(SecureNoteEntry noteEntry, int type) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

        Connection connection = DatabaseHandler.DBconnect();

        String noteEntrySql = "INSERT INTO NoteEntry (Name, Subject, Note, User_ID, Type) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(noteEntrySql);

        preparedStatement.setString(1, noteEntry.getName());
        preparedStatement.setString(2, noteEntry.getNoteSubject());
        preparedStatement.setString(3, noteEntry.getNoteContent());
        preparedStatement.setInt(4, user_id);
        preparedStatement.setInt(5, type);

        int result = preparedStatement.executeUpdate();

        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;

    }
}
