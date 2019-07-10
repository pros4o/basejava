package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void addToStorage(Resume resume, SK key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateResumeInStorage(SK key, Resume resume) {
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
    protected abstract SK getKey(String uuid);

    @Override
    protected List<Resume> copyAll() {
        return new ArrayList<Resume>(storage.values());
    }
}
