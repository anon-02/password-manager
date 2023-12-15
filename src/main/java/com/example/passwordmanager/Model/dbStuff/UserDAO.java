package com.example.passwordmanager.Model.dbStuff;

import com.example.passwordmanager.Model.User;
import com.example.passwordmanager.Model.dbStuff.GenericDAO;

import java.sql.*;
/*
*  DAO extending the generic data access object to deal with the user data
*/
public interface UserDAO extends GenericDAO<User> {


    User getUserByUsername(String username) throws SQLException;
}