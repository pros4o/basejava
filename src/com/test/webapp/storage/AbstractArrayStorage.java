package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final static int MAX_SIZE = 10_000;
    protected int carriage = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    @Override
    protected void addResumeFromStorage(Resume resume, int index) {
        if ((carriage) >= MAX_SIZE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        addResume(resume, index);
        carriage++;
    }

    @Override
    protected void updateResumeFromStorage(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        deleteResume(index);
        storage[carriage - 1] = null;
        carriage--;
    }

    protected Resume getResumeFromStorage(int index) {
        return storage[index];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, carriage, null);
        carriage = 0;
    }

    @Override
    public int size() {
        return carriage;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, carriage);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int key);

    protected abstract void addResume(Resume resume, int index);
}
