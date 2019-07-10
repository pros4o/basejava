package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public class MapStorageByResume extends AbstractMapStorage<Resume> {

    @Override
    protected void deleteResumeInStorage(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected Resume getResumeFromStorage(Resume key) {
        return key;
    }

    @Override
    protected Resume getKey(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean checkKey(Resume key) {
        return key != null;
    }
}
