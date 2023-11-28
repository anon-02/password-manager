package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.DisplayableEntry;
import com.example.passwordmanager.Model.Entry;

import java.sql.SQLException;
import java.util.List;

public interface EntryDAO<E> extends GenericDAO<DisplayableEntry> {

    //TODO for the mainiewcontroller
    List<DisplayableEntry> getAll() throws SQLException;

    int save(Entry entry) throws SQLException;

    int insert(Entry entry) throws SQLException;

    int update(Entry entry) throws SQLException;

    int delete(Entry entry) throws SQLException;
}
