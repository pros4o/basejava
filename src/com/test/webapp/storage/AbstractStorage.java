package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected int carriage = 0;

    public abstract void clear();

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateResume(index, resume);
    }

    protected abstract void updateResume(int index, Resume resume);

    public void save(Resume resume) {
        saveInStorage(resume);
    }

    protected void saveInStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        addResume(resume, index);
        carriage++;
    }

    protected abstract void addResume(Resume resume, int index);

    public abstract Resume get(String uuid);

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            changeStorage();
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void changeStorage();

    protected abstract void deleteResume(int index);

    public abstract Resume[] getAll();

    public int size() {
        return carriage;
    }

    protected abstract int getIndex(String uuid);


}
