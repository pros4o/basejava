package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void addResumeFromStorage(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

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
        String key = (String) index;
        return storage.get(key);
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
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkIndex(Object index) {
        return storage.containsKey(index);
    }
}
