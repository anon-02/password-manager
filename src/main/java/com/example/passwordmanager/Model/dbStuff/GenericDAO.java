package com.example.passwordmanager.Model.dbStuff;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic interface for the database access object, an abstract api isolating the layer dealing with the database
 * Including simple crud functions
 */
public interface GenericDAO<T> {

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;

}
