package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void addResumeFromStorage(Resume resume, Object index);

    protected abstract void updateResumeFromStorage(Object index, Resume resume);

    protected abstract void deleteResumeFromStorage(Object index);

    protected abstract Resume getResumeFromStorage(Object index);

    protected abstract Object getIndex(String uuid);

    public abstract void clear();

    public abstract int size();

    public abstract Resume[] getAll();

    public void update(Resume resume) {
        Object index = checkNotExistStorage(resume.getUuid());
        updateResumeFromStorage(index, resume);
    }

    public void save(Resume resume) {
        Object index = checkExistStorage(resume);
        addResumeFromStorage(resume, index);
    }

    public Resume get(String uuid) {
        Object index = checkNotExistStorage(uuid);
        return getResumeFromStorage(index);
    }

    public void delete(String uuid) {
        Object index = checkNotExistStorage(uuid);
        deleteResumeFromStorage(index);
    }

    private Object checkExistStorage(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (checkIndex(index)) throw new ExistStorageException(resume.getUuid());
        return index;
    }

    private Object checkNotExistStorage(String uuid) {
        Object index = getIndex(uuid);
        if (!checkIndex(index)) throw new NotExistStorageException(uuid);
        return index;
    }

    protected abstract boolean checkIndex(Object index);
}
