package com.test.webapp.storage;

import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final static int MAX_SIZE = 10_000;
    protected int carriage = 0;
    protected Resume[] storage = new Resume[MAX_SIZE];

    @Override
    protected void addResumeFromStorage(Resume resume, Object index) {
        if ((carriage) >= MAX_SIZE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        addResume(resume, (Integer) index);
        carriage++;
    }

    @Override
    protected void updateResumeFromStorage(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void deleteResumeFromStorage(Object index) {
        deleteResume((Integer) index);
        storage[carriage - 1] = null;
        carriage--;
    }

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
    public List<Resume> getAllSorted() {
        Resume[] temp = Arrays.copyOfRange(storage, 0, carriage);
        Arrays.sort(temp, comparator);
        return new ArrayList<>(Arrays.asList(temp));
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void deleteResume(int key);

    protected abstract void addResume(Resume resume, int index);

    @Override
    protected boolean checkIndex(Object index) {
        if ((Integer) index < 0) return false;
        else return true;
    }
}
