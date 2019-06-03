package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public class MapStorageByUUID extends AbstractMapStorage {

    @Override
    protected void deleteResumeInStorage(Object key) {
        storage.remove(key);
    }

    @Override
    protected Resume getResumeFromStorage(Object key) {
        return storage.get(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkKey(Object key) {
        return storage.containsKey(key);
    }
}
