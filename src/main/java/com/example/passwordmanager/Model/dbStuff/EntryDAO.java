package com.example.passwordmanager.Model.dbStuff;

import java.sql.SQLException;
import java.util.List;

public interface EntryDAO<E> extends GenericDAO<E> {


    List< E> getAll() throws SQLException;

    int insert(E entry) throws SQLException;

    int update(E entry) throws SQLException;

    int delete(E entry) throws SQLException;
}
