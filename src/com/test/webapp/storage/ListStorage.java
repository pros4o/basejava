package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void addResumeFromStorage(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected void updateResumeFromStorage(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void deleteResumeFromStorage(Object index) {
        int integerIndex = (Integer) index;
        storage.remove(integerIndex);
    }

    @Override
    protected Resume getResumeFromStorage(Object index) {
        return storage.get((Integer) index);
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
    public List<Resume> getAllSorted() {
        storage.sort(comparator);
        return storage;
    }

    @Override
    protected Object getIndex(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean checkIndex(Object index) {
        return (Integer) index >= 0;
    }
}
