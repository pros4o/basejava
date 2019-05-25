package com.test.webapp.storage;

import com.test.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected void addResumeFromStorage(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void updateResumeFromStorage(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        storage.remove(index);
    }

    @Override
    protected Resume getResumeFromStorage(int index) {
        return storage.get(index); //all
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
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected int getIndex(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if(r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
