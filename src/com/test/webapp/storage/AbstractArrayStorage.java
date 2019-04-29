package com.test.webapp.storage;

import com.test.webapp.exception.ExistStorageException;
import com.test.webapp.exception.NotExistStorageException;
import com.test.webapp.exception.StorageException;
import com.test.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int MAX_SIGE = 10_000;

    protected Resume[] storage = new Resume[MAX_SIGE];
    protected int carriage = 0;

    public int size() {
        return carriage;
    }

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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[index] = resume;
    }

    public void save(Resume resume) {
        if ((carriage) >= MAX_SIGE) {
            throw new StorageException("Attention. Overflow", resume.getUuid());
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        addResume(resume, index);
        carriage++;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[carriage - 1] = null;
            carriage--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void addResume(Resume resume, int index);
}
