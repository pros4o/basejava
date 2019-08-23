package com.test.webapp.storage;

import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.ContactType;
import com.test.webapp.model.Resume;
import com.test.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeSql("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.tracnsactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                checkForExist(resume.getUuid(), ps);
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            fillContact(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.tracnsactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            fillContact(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSql("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        fillContactWithCheckContact(r, rs);
                    } while (rs.next());

                    return r;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSql("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            checkForExist(uuid, ps);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeSql("" +
                        "SELECT uuid, full_name, type, value FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "ORDER BY full_name, uuid ",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> result = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        String full_name = rs.getString("full_name");
                        Resume resume = result.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, full_name);
                            result.put(uuid, resume);
                        }
                        fillContactWithCheckContact(resume, rs);
                    }
                    return new ArrayList<>(result.values());
                });
    }

    @Override
    public int size() {
        return sqlHelper.executeSql("SELECT COUNT(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void checkForExist(String uuid, PreparedStatement ps) throws SQLException {
        if (ps.executeUpdate() != 1) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void fillContactWithCheckContact(Resume resume, ResultSet rs) throws SQLException {
        if (rs.getString("value") != null) {
            resume.putIntoContactInfo(
                    ContactType.valueOf(rs.getString("type")),
                    rs.getString("value")
            );
        }
    }

    private void fillContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContactInfo().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}