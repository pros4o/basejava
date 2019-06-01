package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public class MapStorageByFullName extends AbstractMapStorage {

    @Override
    public void update(Resume resume) {
        Object index = checkNotExistStorage(resume.getFullName());
        updateResumeFromStorage(index, resume);
    }

    @Override
    public void save(Resume resume) {
        Object index = checkExistStorage(resume.getFullName());
        addResumeFromStorage(resume, index);
    }

    @Override
    protected Object getIndex(String key) {
        return key;
    }
}
