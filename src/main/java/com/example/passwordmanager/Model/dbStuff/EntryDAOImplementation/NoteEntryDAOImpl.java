package com.example.passwordmanager.Model.dbStuff.EntryDAOImplementation;

import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.DatabaseHandler;
import com.example.passwordmanager.Model.dbStuff.EntryDAO;
import com.example.passwordmanager.Model.dbStuff.SessionManager;
import com.example.passwordmanager.Model.Entries.SecureNoteEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NoteEntryDAOImpl implements EntryDAO<SecureNoteEntry> {
    @Override
    public List<SecureNoteEntry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int insert(SecureNoteEntry noteEntry) throws SQLException {
        User currentuser =  SessionManager.getCurrentUser();
        int user_id = currentuser.getID();
        int type = noteEntry.getType();

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

    @Override
    public int update(SecureNoteEntry entry) throws SQLException {
        Connection connection = DatabaseHandler.DBconnect();
        String sql = "UPDATE NoteEntry SET Name = ?, Subject = ?, Note = ? WHERE ID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, entry.getName());
        preparedStatement.setString(2, entry.getNoteSubject());
        preparedStatement.setString(3, entry.getNoteContent());
        preparedStatement.setInt(4, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        System.out.println("Successful entry update " + result);
        return result;
    }

    @Override
    public int delete(SecureNoteEntry entry) throws SQLException {
        String sql = "DELETE FROM NoteEntry WHERE id = ?";

        Connection connection = DatabaseHandler.DBconnect();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, entry.getEntryId());

        int result = preparedStatement.executeUpdate();
        DatabaseHandler.closePreparedStatement(preparedStatement);
        DatabaseHandler.closeConnection(connection);

        return result;
    }
}
