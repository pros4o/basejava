package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<? super Resume> comparator = (Comparator<Resume>) (o1, o2) -> {
        int checkByName = o1.getFullName().compareTo(o2.getFullName());
        if (checkByName == 0) return o1.getUuid().compareTo(o2.getUuid());
        return checkByName;
    };

    protected abstract void addToStorage(Resume resume, Object index);

    protected abstract void updateResumeInStorage(Object index, Resume resume);

    protected abstract void deleteResumeInStorage(Object index);

    protected abstract Resume getResumeFromStorage(Object index);

    protected abstract Object getKey(String uuid);

    public void update(Resume resume) {
        Object key = getNotExistStorage(resume.getUuid());
        updateResumeInStorage(key, resume);
    }

    public void save(Resume resume) {
        Object key = getExistStorage(resume.getUuid());
        addToStorage(resume, key);
    }

    public Resume get(String uuid) {
        Object key = getNotExistStorage(uuid);
        return getResumeFromStorage(key);
    }

    public void delete(String uuid) {
        Object key = getNotExistStorage(uuid);
        deleteResumeInStorage(key);
    }

    private Object getExistStorage(String uuid) {
        Object key = getKey(uuid);
        if (checkKey(key)) throw new ExistStorageException(uuid);
        return key;
    }

    private Object getNotExistStorage(String uuid) {
        Object key = getKey(uuid);
        if (!checkKey(key)) throw new NotExistStorageException(uuid);
        return key;
    }

    protected abstract boolean checkKey(Object index);
}
