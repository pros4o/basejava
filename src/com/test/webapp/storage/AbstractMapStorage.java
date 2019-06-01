package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void addResumeFromStorage(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void updateResumeFromStorage(Object key, Resume resume) {
        storage.replace((String) key, resume);
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
    public List<Resume> getAllSorted() {
        List<Resume> support = new ArrayList<>(storage.values());
        support.sort(comparator);
        return support;
    }

    @Override
    protected abstract Object getIndex(String uuid);

    @Override
    protected boolean checkIndex(Object index) {
        return storage.containsKey(index);
    }
}
