package com.test.webapp.storage;

import com.test.webapp.model.Resume;

public class MapStorageByResume extends AbstractMapStorage {

    @Override
    protected void deleteResumeInStorage(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected Resume getResumeFromStorage(Object key) {
        return (Resume) key;
    }

    @Override
    protected Object getKey(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean checkKey(Object key) {
        return key != null;
    }
}
