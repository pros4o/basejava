package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<? super Resume> comparator = Resume::compareTo;

    protected abstract void addResumeFromStorage(Resume resume, Object index);

    protected abstract void updateResumeFromStorage(Object index, Resume resume);

    protected abstract void deleteResumeFromStorage(Object index);

    protected abstract Resume getResumeFromStorage(Object index);

    protected abstract Object getIndex(String uuid);

    public abstract void clear();

    public abstract int size();

    public abstract List<Resume> getAllSorted();

    public void update(Resume resume) {
        Object index = checkNotExistStorage(resume.getUuid());
        updateResumeFromStorage(index, resume);
    }

    public void save(Resume resume) {
        Object index = checkExistStorage(resume.getUuid());
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

    protected Object checkExistStorage(String uuid) {
        Object index = getIndex(uuid);
        if (checkIndex(index)) throw new ExistStorageException(uuid);
        return index;
    }

    protected Object checkNotExistStorage(String uuid) {
        Object index = getIndex(uuid);
        if (!checkIndex(index)) throw new NotExistStorageException(uuid);
        return index;
    }

    protected abstract boolean checkIndex(Object index);
}
