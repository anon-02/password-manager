package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.GenericDAO;

import java.sql.*;

public interface UserDAO extends GenericDAO<User> {


    // crud retrive
    // TODO maybe remove
    User getUserByUsername(String username) throws SQLException;
}