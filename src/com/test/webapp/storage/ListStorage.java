package com.test.webapp.storage;

import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.model.Resume;

import java.util.ArrayList;


public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
        carriage = 0;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage.add(carriage, resume);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void changeStorage() {
        carriage--;
    }

    @Override
    public Resume[] getAll() {
        return storage.subList(0, carriage).toArray(new Resume[carriage]);
    }

    protected int getIndex(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return storage.indexOf(r);
            }
        }
        return -1;
    }
}
