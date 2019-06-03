package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void addToStorage(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected void updateResumeInStorage(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void deleteResumeInStorage(Object index) {
        storage.remove((int) index);
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
    protected Integer getKey(String uuid) {
        for(int i = 0; i < storage.size(); i++) {
            if(storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected List<Resume> mirrorAll() {
        return new ArrayList<Resume>(storage);
    }

    @Override
    protected boolean checkKey(Object index) {
        return (Integer) index >= 0;
    }
}
