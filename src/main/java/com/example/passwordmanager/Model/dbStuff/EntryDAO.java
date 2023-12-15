package com.example.passwordmanager.Model.dbStuff;

import java.sql.SQLException;
import java.util.List;

/*
* Generic extentions of the DAO for all entry types, includes crud functions dealing with all entry data.
*/

public interface EntryDAO<E> extends GenericDAO<E> {


    List< E> getAll() throws SQLException;

    int insert(E entry) throws SQLException;

    int update(E entry) throws SQLException;

    int delete(E entry) throws SQLException;
}
