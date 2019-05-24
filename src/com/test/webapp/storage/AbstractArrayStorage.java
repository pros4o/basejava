package com.test.webapp.storage;

import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int MAX_SIZE = 10_000;

    protected Resume[] storage = new Resume[MAX_SIZE];

    public void clear() {
        Arrays.fill(storage, 0, carriage, null);
        carriage = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, carriage);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public void save(Resume resume) {
        if ((carriage) >= MAX_SIZE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        saveInStorage(resume);
    }

    @Override
    protected void changeStorage() {
        storage[carriage - 1] = null;
        carriage--;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);
}
