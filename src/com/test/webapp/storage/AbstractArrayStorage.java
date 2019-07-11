package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected final static int MAX_SIZE = 10_000;
    protected int carriage = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    @Override
    protected void addToStorage(Resume resume, Integer index) {
        if (carriage >= MAX_SIZE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        addResume(resume, index);
        carriage++;
    }

    @Override
    protected void updateResumeInStorage(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void deleteResumeInStorage(Integer index) {
        deleteResume(index);
        storage[carriage - 1] = null;
        carriage--;
    }

    @Override
    protected Resume getResumeFromStorage(Integer index) {
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
    protected List<Resume> copyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, carriage));
    }

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}
