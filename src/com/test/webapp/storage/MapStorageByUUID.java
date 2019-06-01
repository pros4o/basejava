package com.test.webapp.storage;


import com.test.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class MapStorageByUUID extends AbstractMapStorage {

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> support = new ArrayList<>(storage.values());
        support.sort(comparator);
        return support;
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }
}
