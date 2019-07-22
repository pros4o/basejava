package com.test.webapp.storage;

import com.test.webapp.exception.IOStrategy;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directoryPath;
    private SaveStrategy saveStrategy;

    public PathStorage(String storagePath, SaveStrategy saveStrategy) {
        Objects.requireNonNull(directoryPath, "Directory must not be null");
        Objects.requireNonNull(saveStrategy, "SaveStrategy must not be null");
        if (!directoryPath.toFile().isDirectory()) {
            throw new IllegalArgumentException(directoryPath.toAbsolutePath() + " is not directory");
        }
        if (!directoryPath.toFile().canRead() || !directoryPath.toFile().canWrite()) {
            throw new IllegalArgumentException(directoryPath.toAbsolutePath() + " is not readable/writable");
        }
        this.directoryPath = Paths.get(storagePath);
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
        } catch (Exception e) {
            throw new IOStrategy("Resume can't write to the file ", path.getFileName().toString(), e);
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
        } catch (Exception e) {
            throw new IOStrategy("Can't get resume", path.getFileName().toString(), e);
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
