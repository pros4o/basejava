package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }


    @Override
    protected void addToStorage(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected void updateResumeInStorage(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResumeInStorage(File file) {
        if (!file.delete()) throw new IllegalArgumentException(file.getName() + " cannot be deleted");
    }

    @Override
    protected Resume getResumeFromStorage(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> copyAll() {
        List<Resume> listResume = new ArrayList<>();
        File[] listFile = directory.listFiles();
        for (File file : listFile) {
            try {
                listResume.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return listResume;
    }

    @Override
    protected boolean checkKey(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] listFile = directory.listFiles();
        for (File file : listFile) {
            file.delete();
        }
    }

    @Override
    public int size() {
        int count = 0;
        String[] list = directory.list();
        if (list != null) {
            for (String name : list) {
                count++;
            }
        } else throw new IllegalArgumentException(directory.getAbsolutePath() + " is not create");
        return count;
    }
}
