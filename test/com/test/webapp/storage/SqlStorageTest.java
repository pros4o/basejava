package com.test.webapp.storage;

import com.test.webapp.Config;

import java.sql.SQLException;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() throws SQLException {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbUser()));
    }
}
