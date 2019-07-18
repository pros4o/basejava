package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.io.*;
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
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateResumeInStorage(file, resume);
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected void updateResumeInStorage(File file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected List<Resume> copyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(getResumeFromStorage(file));
        }
        return list;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File[] listFile = directory.listFiles();
        if (listFile != null) {
            for (File file : listFile) {
                file.delete();
            }
        } else throw new IllegalArgumentException(directory.getAbsolutePath() + " is not exist");
    }

    @Override
    public int size() {
        int size = 0;
        String[] list = directory.list();
        if (list != null) {
            size = list.length;
        } else throw new IllegalArgumentException(directory.getAbsolutePath() + " is not create");
        return size;
    }
}
