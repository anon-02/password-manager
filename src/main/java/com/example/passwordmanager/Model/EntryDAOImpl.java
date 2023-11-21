package com.example.passwordmanager.Model;

import java.sql.SQLException;
import java.util.List;

public class EntryDAOImpl implements EntryDAO<Entry>{
    @Override
    public Entry get(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Entry> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Entry entry) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Entry entry) throws SQLException {
        return 0;
    }

    @Override
    public int update(Entry entry) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Entry entry) throws SQLException {
        return 0;
    }
}
