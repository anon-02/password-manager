package com.example.passwordmanager.Model.dbStuff;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;

}
