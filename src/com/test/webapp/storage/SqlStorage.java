package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;
import com.test.webapp.sql.ConnectionFactory;
import com.test.webapp.util.SqlHelper;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;



    public SqlStorage(SqlHelper sqlHelper) {

        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        try ( Connection conn = connectionFactory.getConnection();
              PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")){
            checkForExist(resume.getUuid());
            ps.setString(2, resume.getUuid());
            ps.setString(1, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try( Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")
                ) {
            checkForExist(uuid);
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try(Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT FROM resume ORDER BY full_name")
            ){
            ps.execute();
        }catch (SQLException e) {
            throw new StorageException(e);
        }
        return null;
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(uuid) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new IllegalArgumentException();
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private void checkForExist(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(uuid) FROM resume WHERE uuid=?")
        ){
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next() || resultSet.getInt(1) == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}