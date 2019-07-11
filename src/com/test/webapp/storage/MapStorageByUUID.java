package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public class MapStorageByUUID extends AbstractMapStorage<String> {

    @Override
    protected void deleteResumeInStorage(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume getResumeFromStorage(String key) {
        return storage.get(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }
}
