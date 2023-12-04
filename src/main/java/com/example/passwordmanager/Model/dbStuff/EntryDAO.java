package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.Model.DisplayableEntry;

import java.sql.SQLException;
import java.util.List;

public interface EntryDAO<E> extends GenericDAO<DisplayableEntry> {

    //TODO for the mainiewcontroller
    List<DisplayableEntry> getAll() throws SQLException;

    int save(DisplayableEntry entry) throws SQLException;

    int insert(DisplayableEntry entry) throws SQLException;

    int update(DisplayableEntry entry) throws SQLException;

    int delete(DisplayableEntry entry) throws SQLException;
}
