package com.test.webapp.storage;

public class MapStorageByUUID extends AbstractMapStorage {

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }
}
