package com.test.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory <T> {
    Connection getConnection() throws SQLException;
}
