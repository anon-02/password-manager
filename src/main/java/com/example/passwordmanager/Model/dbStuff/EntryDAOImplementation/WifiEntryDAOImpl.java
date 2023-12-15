package com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation;

import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.DatabaseHandler;
import com.example.passwordmanager.Model.dbStuff.EntryDAO;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.Model.Entries.WifiEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
 * dao implementation for specifically wifiEntry types containing crud functions
 */

public class WifiEntryDAOImpl implements EntryDAO<WifiEntry> {

    @Override
    public List<WifiEntry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int insert(WifiEntry wifiEntry) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();
        int type = wifiEntry.getType();

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

    @Override
    public int update(WifiEntry entry) throws SQLException {
        Connection connection = DatabaseHandler.DBconnect();
        String sql = "UPDATE WifiEntry SET Name = ?, WifiName = ?, WifiPassword = ?, ConfigURL = ?, AdminPassword = ?, Note = ? WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, entry.getName());
        preparedStatement.setString(2, entry.getWifiName());
        preparedStatement.setString(3, entry.getWifiPassword());
        preparedStatement.setString(4, entry.getWifiURL());
        preparedStatement.setString(5, entry.getWifiAdminPassword());
        preparedStatement.setString(6, entry.getNote());
        preparedStatement.setInt(7, entry.getEntryId());


        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);
        System.out.println("Successful entry update " + result);

        return result;
    }

    @Override
    public int delete(WifiEntry entry) throws SQLException {
        String sql = "DELETE FROM WifiEntry WHERE id = ?";
        Connection connection = DatabaseHandler.DBconnect();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }
}
