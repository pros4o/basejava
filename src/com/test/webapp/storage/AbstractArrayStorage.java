package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final static int MAX_SIZE = 10_000;
    protected int carriage = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    @Override
    protected void addToStorage(Resume resume, Object index) {
        if (carriage >= MAX_SIZE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        addResume(resume, (Integer) index);
        carriage++;
    }

    @Override
    protected void updateResumeInStorage(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void deleteResumeInStorage(Object index) {
        deleteResume((Integer) index);
        storage[carriage - 1] = null;
        carriage--;
    }

    @Override
    protected Resume getResumeFromStorage(Object index) {
        return storage[(Integer) index];
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
    protected List<Resume> copyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, carriage));
    }

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);

    @Override
    protected boolean checkKey(Object index) {
        return (Integer) index >= 0;
    }
}
