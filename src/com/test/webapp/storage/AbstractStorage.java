package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> COMPARATOR = ((o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));

    protected abstract void addToStorage(Resume resume, Object index);

    protected abstract void updateResumeInStorage(Object index, Resume resume);

    protected abstract void deleteResumeInStorage(Object index);

    protected abstract Resume getResumeFromStorage(Object index);

    protected abstract Object getKey(String uuid);

    public void update(Resume resume) {
        Object key = getSearchKeyIfExist(resume.getUuid());
        updateResumeInStorage(key, resume);
    }

    public void save(Resume resume) {
        Object key = getSearchKeyIfNotExist(resume.getUuid());
        addToStorage(resume, key);
    }

    public Resume get(String uuid) {
        Object key = getSearchKeyIfExist(uuid);
        return getResumeFromStorage(key);
    }

    public void delete(String uuid) {
        Object key = getSearchKeyIfExist(uuid);
        deleteResumeInStorage(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = mirrorAll();
        Collections.sort(resumeList);
        return resumeList;
    }

    protected abstract List<Resume> mirrorAll();

    private Object getSearchKeyIfNotExist(String uuid) {
        Object key = getKey(uuid);
        if (checkKey(key)) throw new ExistStorageException(uuid);
        return key;
    }

    private Object getSearchKeyIfExist(String uuid) {
        Object key = getKey(uuid);
        if (!checkKey(key)) throw new NotExistStorageException(uuid);
        return key;
    }

    protected abstract boolean checkKey(Object index);
}
