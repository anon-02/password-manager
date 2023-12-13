package com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation;

import com.example.passwordmanager.*;
import com.example.passwordmanager.AccountEntry;
import com.example.passwordmanager.CardEntry;
import com.example.passwordmanager.Model.dbStuff.DatabaseHandler;
import com.example.passwordmanager.Model.dbStuff.EntryDAO;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.SecureNoteEntry;
import com.example.passwordmanager.WifiEntry;
import com.example.passwordmanager.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntryDAOImpl implements EntryDAO<DisplayableEntry> {

    @Override
    public List<DisplayableEntry> getAll() throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();

        Connection connection = DatabaseHandler.DBconnect();

        List<DisplayableEntry> entryList = new ArrayList<>();

        getAccountEntries(entryList, user_id, connection);
        getNoteEntries(entryList, user_id, connection);
        getCardEntries(entryList, user_id, connection);
        getWifiEntries(entryList, user_id, connection);

        return entryList;
    }

    public List<DisplayableEntry> getAccountEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM AccountEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            AccountEntry newAccEntry = new AccountEntry(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Username"), resultSet.getString("Password"), resultSet.getString("Note"));
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
            SecureNoteEntry newNoteEntry = new SecureNoteEntry(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("Subject"), resultSet.getString("Note"));
            entryList.add(newNoteEntry);
        }
        return entryList;
    }

    public List<DisplayableEntry> getCardEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM CardEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            CardEntry newCardEntry = new CardEntry(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("CardHolder"), resultSet.getString("CardNumber"), resultSet.getString("expireMonth"), resultSet.getString("expireYear"), resultSet.getString("CVC"), resultSet.getString("Note"));
            entryList.add(newCardEntry);
        }
        return entryList;
    }

    public List<DisplayableEntry> getWifiEntries(List<DisplayableEntry> entryList, int user_id, Connection connection) throws SQLException {
        String sql = "SELECT * FROM WifiEntry WHERE User_ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            WifiEntry newWifiEntry = new WifiEntry(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("WifiName"), resultSet.getString("WifiPassword"), resultSet.getString("ConfigURL"), resultSet.getString("AdminPassword"), resultSet.getString("Note"));
            System.out.println("the broken new wifis id  : "+ resultSet.getInt("ID"));
            System.out.println(" ");
            entryList.add(newWifiEntry);
        }
        return entryList;
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


}
