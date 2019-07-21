package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectStreamPathStorage extends AbstractStorage<Path> {
    private Path directoryPath;
    private SaveStrategy saveStrategy;

    public ObjectStreamPathStorage(String storagePath, SaveStrategy saveStrategy) {
        this.directoryPath = Paths.get(storagePath);
        Objects.requireNonNull(directoryPath, "Directory must not be null");
        Objects.requireNonNull(saveStrategy, "SaveStrategy must not be null");
        this.saveStrategy = saveStrategy;
    }


    @Override
    protected void addToStorage(Resume resume, Path path) {
        updateResumeInStorage(path, resume);
    }

    @Override
    protected void updateResumeInStorage(Path path, Resume resume) {
        try {
            saveStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Resume can't write", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResumeInStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can not delete file " + path.toString(), path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getResumeFromStorage(Path path) {
        try {
            return saveStrategy.doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Can't get resume", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return directoryPath.resolve(uuid);
    }

    @Override
    protected List<Resume> copyAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directoryPath).forEach(path -> list.add(getResumeFromStorage(path)));
        } catch (IOException e) {
            throw new StorageException("No resumes in directory", null);
        }
        return list;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        try {
            Files.list(directoryPath).forEach(this::deleteResumeInStorage);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directoryPath).count();
        } catch (IOException e) {
            throw new StorageException("Path size error", null, e);
        }
    }
}
