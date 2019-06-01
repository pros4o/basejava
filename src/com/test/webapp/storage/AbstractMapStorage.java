package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected abstract void addResumeFromStorage(Resume resume, Object index);

    @Override
    protected void updateResumeFromStorage(Object index, Resume resume) {
        storage.replace((String) index, resume);
    }

    @Override
    protected void deleteResumeFromStorage(Object index) {
        storage.remove(index);
    }

    @Override
    protected Resume getResumeFromStorage(Object index) {
        return storage.get(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public abstract List<Resume> getAllSorted();

    @Override
    protected abstract Object getIndex(String uuid);

    @Override
    protected boolean checkIndex(Object index) {
        return storage.containsKey(index);
    }
}
