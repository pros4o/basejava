package com.test.webapp.util;

import com.test.webapp.sql.ConnectionFactory;

import java.sql.DriverManager;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;


    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}


    //Вынести общий код (getConnection(), prepareStatement, catch SQLException) в класс SqlHelper