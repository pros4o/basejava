package com.test.webapp.storage;

import com.test.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < carriage; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        storage[index] = storage[carriage - 1];
        storage[carriage - 1] = null;
        carriage--;
    }

    @Override
    protected void addResumeToStorage(Resume resume) {
        storage[carriage] = resume;
    }
}
