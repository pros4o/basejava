package com.test.webapp.storage;

import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.*;
import com.test.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            fillSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.tracnsactionExecute(
                conn -> {
                    Resume r;
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT * FROM resume r " +
                            "LEFT JOIN contact c " +
                            "ON r.uuid = c.resume_uuid " +
                            "WHERE r.uuid = ?"
                    )) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        r = new Resume(uuid, rs.getString("full_name"));
                        do {
                            addContact(r, rs);
                        } while (rs.next());
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addSection(r, rs);
                        }
                    }

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
        return sqlHelper.tracnsactionExecute(conn -> {
                    Map<String, Resume> result = new LinkedHashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid").trim();
                            String full_name = rs.getString("full_name");
                            result.computeIfAbsent(uuid, id -> new Resume(id, full_name));
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume resume = result.get(rs.getString("resume_uuid").trim());
                            addContact(resume, rs);
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT * FROM section"
                    )) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume r = result.get(rs.getString("resume_uuid").trim());
                            addSection(r, rs);
                        }
                    }
                    return new ArrayList<>(result.values());
                }
        );
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

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        if (rs.getString("value") != null) {
            resume.putIntoContactInfo(
                    ContactType.valueOf(rs.getString("type")),
                    rs.getString("value")
            );
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.putIntoSections(sectionType, new SimpleTextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> items = Arrays.asList(value.split("\n"));
                    resume.putIntoSections(sectionType, new MarkedSection(items));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    break;
            }
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

    private void fillSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                SectionType sectionType = e.getKey();
                ps.setString(2, sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(3, e.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setString(3,
                                String.join("\n",((MarkedSection) e.getValue()).getItems()));
                    case EXPERIENCE:
                    case EDUCATION:
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}