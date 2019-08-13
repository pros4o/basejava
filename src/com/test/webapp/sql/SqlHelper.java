package com.test.webapp.sql;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.StorageException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) throws SQLException {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlExect <T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public <T> T executeSql(String query, SqlExect<T> sqlExect) {
     try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
         return sqlExect.execute(ps);
     } catch (SQLException e) {
         if (e.getSQLState().startsWith("23505")) {
             throw new ExistStorageException(e);
         }
         throw new StorageException(e);
     }
    }
}


    //Вынести общий код (getConnection(), prepareStatement, catch SQLException) в класс SqlHelper