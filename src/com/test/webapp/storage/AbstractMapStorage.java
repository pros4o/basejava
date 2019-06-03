package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void addToStorage(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateResumeInStorage(Object key, Resume resume) {
        storage.replace(resume.getUuid(), resume);
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
    protected abstract Object getKey(String uuid);

    @Override
    protected List<Resume> mirrorAll() {
        return new ArrayList<Resume>(storage.values());
    }
}
