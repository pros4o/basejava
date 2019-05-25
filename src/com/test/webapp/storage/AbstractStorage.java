package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void addResumeFromStorage(Resume resume, int index);

    protected abstract void updateResumeFromStorage(int index, Resume resume);

    protected abstract void deleteResumeFromStorage(int index);

    protected abstract Resume getResumeFromStorage(int index);

    protected abstract int getIndex(String uuid);

    public abstract void clear();

    public abstract int size();

    public abstract Resume[] getAll();

    public void update(Resume resume) {
        int index = checkNotExistStorage(resume.getUuid());
        updateResumeFromStorage(index, resume);
    }

    public void save(Resume resume) {
        int index = checkExistStorage(resume);
        addResumeFromStorage(resume, index);
    }

    public Resume get(String uuid) {
        int index = checkNotExistStorage(uuid);
        return getResumeFromStorage(index);
    }

    public void delete(String uuid) {
        int index = checkNotExistStorage(uuid);
        deleteResumeFromStorage(index);
    }

    private int checkExistStorage(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        return index;
    }

    private int checkNotExistStorage(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) throw new NotExistStorageException(uuid);
        return index;
    }
}
