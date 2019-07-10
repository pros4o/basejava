package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void addToStorage(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected void updateResumeInStorage(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResumeInStorage(Integer index) {
        storage.remove((int) index);
    }

    @Override
    protected Resume getResumeFromStorage(Integer index) {
        return storage.get(index);
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
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected List<Resume> copyAll() {
        return new ArrayList<Resume>(storage);
    }

    @Override
    protected boolean checkKey(Integer index) {
        return index >= 0;
    }
}
